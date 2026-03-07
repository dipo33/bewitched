package com.dipo33.bewitched.items.mutandis;

import com.dipo33.bewitched.block.BlockRegistry;
import com.dipo33.bewitched.config.Config;
import com.github.bsideup.jabel.Desugar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class MutandisMutationRegistry {

    @Desugar
    public record MutationMatch(MutandisMutation mutation, int index) {
    }

    private static final List<MutandisMutation> worldMutations = new ArrayList<>();
    private static final List<MutandisMutation> advancedWorldMutations = new ArrayList<>();
    private static final List<MutandisMutation> specialWorldMutations = new ArrayList<>();
    private static final List<MutandisMutation> flowerPotMutations = new ArrayList<>();

    public static final List<MutandisMutation> WORLD_MUTATIONS = Collections.unmodifiableList(worldMutations);
    public static final List<MutandisMutation> ADVANCED_WORLD_MUTATIONS = Collections.unmodifiableList(advancedWorldMutations);
    public static final List<MutandisMutation> SPECIAL_WORLD_MUTATIONS = Collections.unmodifiableList(specialWorldMutations);
    public static final List<MutandisMutation> FLOWER_POT_MUTATIONS = Collections.unmodifiableList(flowerPotMutations);

    public static void registerWorldMutation(MutandisMutation mutation) {
        Objects.requireNonNull(mutation);
        worldMutations.add(mutation);
    }

    public static void registerAdvancedWorldMutation(MutandisMutation mutation) {
        Objects.requireNonNull(mutation);
        advancedWorldMutations.add(mutation);
    }

    public static void registerSpecialWorldMutation(MutandisMutation mutation) {
        Objects.requireNonNull(mutation);
        specialWorldMutations.add(mutation);
    }

    public static void registerFlowerPotMutation(MutandisMutation mutation) {
        Objects.requireNonNull(mutation);
        flowerPotMutations.add(mutation);
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

    public static void registerMutations() {
        registerWorldMutations();
        registerAdvancedWorldMutations();
        registerSpecialWorldMutations();
        registerFlowerPotMutations();
    }

    private static void registerWorldMutations() {
        for (MutandisMutation mutation : Config.mutandisAdditionalMutations) {
            registerWorldMutation(mutation);
        }

        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.tallgrass, 1));
        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.brown_mushroom, 0));
        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.red_mushroom, 0));
        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.yellow_flower, 0));
        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.red_flower, 0));
        registerWorldMutation(MutandisMutation.primitiveMutation(Blocks.waterlily, 0));
        registerWorldMutation(MutandisMutation.primitiveMutation(BlockRegistry.EMBER_MOSS.get(), 0));

        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(BlockRegistry.SPANISH_MOSS.get(), MutandisMutation.wallStrategy()),
            Collections.singletonList(
                MutandisMutation.Source.anyMeta(BlockRegistry.SPANISH_MOSS.get())
            )
        ));

        // Vanilla Saplings
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(0)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 0),
                MutandisMutation.Source.exact(Blocks.sapling, 8)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(1)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 1),
                MutandisMutation.Source.exact(Blocks.sapling, 9)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(2)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 2),
                MutandisMutation.Source.exact(Blocks.sapling, 10)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(3)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 3),
                MutandisMutation.Source.exact(Blocks.sapling, 11)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(4)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 4),
                MutandisMutation.Source.exact(Blocks.sapling, 12)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(Blocks.sapling, MutandisMutation.basicStrategy(5)),
            Arrays.asList(
                MutandisMutation.Source.exact(Blocks.sapling, 5),
                MutandisMutation.Source.exact(Blocks.sapling, 13)
            )
        ));

        // Bewitched Saplings
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(BlockRegistry.SAPLING.get(), MutandisMutation.basicStrategy(0)),
            Arrays.asList(
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 0),
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 8)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(BlockRegistry.SAPLING.get(), MutandisMutation.basicStrategy(1)),
            Arrays.asList(
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 1),
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 9)
            )
        ));
        registerWorldMutation(new MutandisMutation(
            new MutandisMutation.Output(BlockRegistry.SAPLING.get(), MutandisMutation.basicStrategy(2)),
            Arrays.asList(
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 2),
                MutandisMutation.Source.exact(BlockRegistry.SAPLING.get(), 10)
            )
        ));
    }

    private static void registerAdvancedWorldMutations() {
        for (MutandisMutation mutation : worldMutations) {
            registerAdvancedWorldMutation(mutation);
        }
        for (MutandisMutation mutation : Config.mutandisExtremisAdditionalMutations) {
            registerAdvancedWorldMutation(mutation);
        }

        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.potatoes, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.carrots, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.nether_wart, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.wheat, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.reeds, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.cactus, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.pumpkin_stem, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(Blocks.melon_stem, 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(BlockRegistry.MANDRAKE_CROP.get(), 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(BlockRegistry.BELLADONNA_CROP.get(), 0));
        registerAdvancedWorldMutation(MutandisMutation.primitiveAnyMetaMutation(BlockRegistry.WATER_ARTICHOKE_CROP.get(), 0));
    }

    private static void registerSpecialWorldMutations() {
        for (MutandisMutation mutation : Config.mutandisExtremisAdditionalGrassMutations) {
            registerSpecialWorldMutation(mutation);
        }

        registerSpecialWorldMutation(MutandisMutation.primitiveMutation(Blocks.grass, 0));
        registerSpecialWorldMutation(MutandisMutation.primitiveMutation(Blocks.mycelium, 0));
    }

    private static void registerFlowerPotMutations() {
        for (MutandisMutation mutation : Config.mutandisAdditionalFlowerPotMutations) {
            registerFlowerPotMutation(mutation);
        }

        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.yellow_flower, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.red_flower, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.cactus, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.brown_mushroom, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.red_mushroom, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.deadbush, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.tallgrass, 2));

        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 0));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 1));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 2));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 3));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 4));
        registerFlowerPotMutation(MutandisMutation.primitiveMutation(Blocks.sapling, 5));
    }
}
