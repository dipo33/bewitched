package com.dipo33.bewitched.items.mutandis;

import com.github.bsideup.jabel.Desugar;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Desugar
public record MutandisMutation(Output output, List<Source> sources) {
    public boolean matchesSource(Block block, int meta) {
        for (Source source : sources) {
            if (source.matches(block, meta)) {
                return true;
            }
        }

        return false;
    }

    public static MutandisMutation mutate(List<MutandisMutation> mutations, Block block, int meta, Random rand) {
        var match = MutandisMutationRegistry.getMutationFor(mutations, block, meta);
        if (match == null) {
            return null;
        }

        int resultMutationIdx = rand.nextInt(mutations.size() - 1);
        if (resultMutationIdx >= match.index()) {
            resultMutationIdx++;
        }

        return mutations.get(resultMutationIdx);
    }

    /**
     * A source matcher: matches a block and either an exact metadata or ANY metadata (wildcard).
     *
     * @param meta
     *     null = wildcard
     */
    @Desugar
    public record Source(Block block, Integer meta) {
        public boolean matches(Block b, int m) {
            return b == block && (meta == null || meta == m);
        }

        public static Source anyMeta(Block b) {
            return new Source(b, null);
        }

        public static Source exact(Block b, int meta) {
            return new Source(b, meta);
        }
    }

    @FunctionalInterface
    public interface PlacementStrategy {
        /**
         * Return the most appropriate meta to place this block with.
         */
        int placementMeta(World world, int x, int y, int z);
    }

    @Desugar
    public record Output(Block block, PlacementStrategy placement) {
    }

    public static MutandisMutation primitiveMutation(final Block block, int meta) {
        return new MutandisMutation(
            new Output(block, basicStrategy(meta)),
            Collections.singletonList(
                Source.exact(block, meta)
            )
        );
    }

    /**
     * Fixed-meta strategy: only spawns exactly one meta.
     */
    public static PlacementStrategy basicStrategy(int meta) {
        return (world, x, y, z) -> meta;
    }

    /**
     * Wall strategy: only spawns where a neighboring block is present.
     */
    public static PlacementStrategy wallStrategy() {
        return (world, x, y, z) -> {
            // South (+Z) -> meta 1
            if (world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH)) {
                return 1;
            }

            // West (-X) -> meta 2
            if (world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST)) {
                return 2;
            }

            // North (-Z) -> meta 4
            if (world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH)) {
                return 4;
            }

            // East (+X) -> meta 8
            if (world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST)) {
                return 8;
            }

            return 1;
        };
    }
}
