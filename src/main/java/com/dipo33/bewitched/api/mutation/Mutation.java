package com.dipo33.bewitched.api.mutation;

import com.github.bsideup.jabel.Desugar;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Desugar
public record Mutation(Output output, List<Source> sources) {
    public boolean matchesSource(Block block, int meta) {
        for (Source source : sources) {
            if (source.matches(block, meta)) {
                return true;
            }
        }

        return false;
    }

    public static Mutation mutate(MutationPoolType type, Block block, int meta, Random rand) {
        var pool = MutationRegistry.getPool(type);
        var match = pool.getMutationFor(block, meta);
        if (match == null) {
            return null;
        }

        var mutations = pool.getMembers();
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

    public interface PlacementStrategy {
        /**
         * Return the most appropriate meta to place this block with.
         */
        int placementMeta(World world, int x, int y, int z);

        int defaultPlacementMeta();
    }

    @Desugar
    public record Output(Block block, PlacementStrategy placement) {
        public ItemStack asStack() {
            Item item = Item.getItemFromBlock(this.block);
            return new ItemStack(item, 1, this.placement.defaultPlacementMeta());
        }
    }

    public static Mutation primitiveMutation(final Block block, int meta) {
        return new Mutation(
            new Output(block, basicStrategy(meta)),
            Collections.singletonList(
                Source.exact(block, meta)
            )
        );
    }

    public static Mutation primitiveAnyMetaMutation(final Block block, int meta) {
        return new Mutation(
            new Output(block, basicStrategy(meta)),
            Collections.singletonList(
                Source.anyMeta(block)
            )
        );
    }

    /**
     * Fixed-meta strategy: only spawns exactly one meta.
     */
    public static PlacementStrategy basicStrategy(int meta) {
        return new PlacementStrategy() {
            @Override
            public int placementMeta(World world, int x, int y, int z) {
                return meta;
            }

            @Override
            public int defaultPlacementMeta() {
                return meta;
            }
        };
    }

    /**
     * Wall strategy: only spawns where a neighboring block is present.
     */
    public static PlacementStrategy wallStrategy() {
        return new PlacementStrategy() {
            @Override
            public int placementMeta(final World world, final int x, final int y, final int z) {
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
            }

            @Override
            public int defaultPlacementMeta() {

                return 1;
            }
        };
    }
}
