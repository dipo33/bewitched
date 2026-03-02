package com.dipo33.bewitched.items.mutandis;

import com.dipo33.bewitched.block.BlockRegistry;
import com.github.bsideup.jabel.Desugar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class MutandisMutationRegistry {

    @Desugar
    public record MutationMatch(MutandisMutation mutation, int index) {
    }

    public static MutationMatch getMutationFor(List<MutandisMutation> mutations, Block block, int meta) {
        for (int i = 0; i < mutations.size(); i++) {
            MutandisMutation mutation = mutations.get(i);
            if (mutation.matchesSource(block, meta)) {
                return new MutationMatch(mutation, i);
            }
        }

        return null;
    }

    /**
     * World-block mutations.
     */
    public static final List<MutandisMutation> WORLD_MUTATIONS = Arrays.asList(
        // ─────────────────────────────
        // Tallgrass (meta 1)
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.tallgrass, 1),

        // ─────────────────────────────
        // Tallgrass (meta 2 - fern)
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.tallgrass, 2),

        // ─────────────────────────────
        // Brown mushroom
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.brown_mushroom, 0),

        // ─────────────────────────────
        // Red mushroom
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.red_mushroom, 0),

        // ─────────────────────────────
        // Dandelion
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.yellow_flower, 0),

        // ─────────────────────────────
        // Poppy
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.red_flower, 0),

        // ─────────────────────────────
        // Waterlily
        // ─────────────────────────────
        MutandisMutation.primitiveMutation(Blocks.waterlily, 0),

        // ─────────────────────────────
        // Spanish Moss
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(BlockRegistry.SPANISH_MOSS.get(), MutandisMutation.wallStrategy()),
            Collections.singletonList(
                MutandisMutation.Source.anyMeta(BlockRegistry.SPANISH_MOSS.get())
            )
        ),

        // ─────────────────────────────
        // Oak sapling
        // Includes growth-flag variant (meta 8)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(0)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 0),
                MutandisMutation.Source.exact(Blocks.sapling, 8)
            )
        ),

        // ─────────────────────────────
        // Spruce sapling
        // Includes growth-flag variant (meta 9)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(1)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 1),
                MutandisMutation.Source.exact(Blocks.sapling, 9)
            )
        ),

        // ─────────────────────────────
        // Birch sapling
        // Includes growth-flag variant (meta 10)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(2)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 2),
                MutandisMutation.Source.exact(Blocks.sapling, 10)
            )
        ),

        // ─────────────────────────────
        // Jungle sapling
        // Includes growth-flag variant (meta 11)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(3)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 3),
                MutandisMutation.Source.exact(Blocks.sapling, 11)
            )
        ),

        // ─────────────────────────────
        // Acacia sapling
        // Includes growth-flag variant (meta 12)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(4)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 4),
                MutandisMutation.Source.exact(Blocks.sapling, 12)
            )
        ),

        // ─────────────────────────────
        // Dark oak sapling
        // Includes growth-flag variant (meta 13)
        // ─────────────────────────────
        new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(5)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 5),
                MutandisMutation.Source.exact(Blocks.sapling, 13)
            )
        )
    );

    /**
     * Flower pot mutations.
     */
    public static final List<MutandisMutation> FLOWER_POT_MUTATIONS = Arrays.asList(
        MutandisMutation.primitiveMutation(Blocks.yellow_flower, 0),
        MutandisMutation.primitiveMutation(Blocks.red_flower, 0),
        MutandisMutation.primitiveMutation(Blocks.cactus, 0),
        MutandisMutation.primitiveMutation(Blocks.brown_mushroom, 0),
        MutandisMutation.primitiveMutation(Blocks.red_mushroom, 0),
        MutandisMutation.primitiveMutation(Blocks.deadbush, 0),
        MutandisMutation.primitiveMutation(Blocks.tallgrass, 2),
        MutandisMutation.primitiveMutation(Blocks.sapling, 0),
        MutandisMutation.primitiveMutation(Blocks.sapling, 1),
        MutandisMutation.primitiveMutation(Blocks.sapling, 2),
        MutandisMutation.primitiveMutation(Blocks.sapling, 3),
        MutandisMutation.primitiveMutation(Blocks.sapling, 4),
        MutandisMutation.primitiveMutation(Blocks.sapling, 5)
    );
}
