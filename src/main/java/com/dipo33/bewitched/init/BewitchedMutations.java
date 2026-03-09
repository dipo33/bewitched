package com.dipo33.bewitched.init;

import com.dipo33.bewitched.api.mutation.Mutation;
import com.dipo33.bewitched.api.mutation.MutationPool;
import com.dipo33.bewitched.api.mutation.MutationPoolType;
import com.dipo33.bewitched.api.mutation.MutationRegistry;
import com.dipo33.bewitched.block.BlockRegistry;
import com.dipo33.bewitched.config.Config;
import com.dipo33.bewitched.items.ItemRegistry;

import java.util.Arrays;
import java.util.Collections;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BewitchedMutations {

    public static void init() {
        registerMutandisMutations();
        registerMutandisExtremisMutations();
        registerMutandisExtremisGrassMutations();
        registerFlowerPotMutations();
    }

    private static void registerMutandisMutations() {
        MutationPool pool = MutationRegistry.createPool(
            MutationPoolType.MUTANDIS,
            "nei.bewitched.mutation.mutandis"
        );
        pool.addCatalyst(new ItemStack(ItemRegistry.MUTANDIS.get()));

        for (Mutation mutation : Config.mutandisAdditionalMutations) {
            pool.addMember(mutation);
        }

        pool.addMember(Mutation.primitiveMutation(Blocks.tallgrass, 1));
        pool.addMember(Mutation.primitiveMutation(Blocks.brown_mushroom, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.red_mushroom, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.yellow_flower, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.red_flower, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.waterlily, 0));
        pool.addMember(Mutation.primitiveMutation(BlockRegistry.EMBER_MOSS.get(), 0));

        pool.addMember(new Mutation(
            new Mutation.Output(BlockRegistry.SPANISH_MOSS.get(), Mutation.wallStrategy()),
            Collections.singletonList(
                Mutation.Source.anyMeta(BlockRegistry.SPANISH_MOSS.get())
            )
        ));

        // Vanilla Saplings
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(0)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 0),
                Mutation.Source.exact(Blocks.sapling, 8)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(1)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 1),
                Mutation.Source.exact(Blocks.sapling, 9)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(2)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 2),
                Mutation.Source.exact(Blocks.sapling, 10)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(3)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 3),
                Mutation.Source.exact(Blocks.sapling, 11)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(4)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 4),
                Mutation.Source.exact(Blocks.sapling, 12)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(Blocks.sapling, Mutation.basicStrategy(5)),
            Arrays.asList(
                Mutation.Source.exact(Blocks.sapling, 5),
                Mutation.Source.exact(Blocks.sapling, 13)
            )
        ));

        // Bewitched Saplings
        pool.addMember(new Mutation(
            new Mutation.Output(BlockRegistry.SAPLING.get(), Mutation.basicStrategy(0)),
            Arrays.asList(
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 0),
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 8)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(BlockRegistry.SAPLING.get(), Mutation.basicStrategy(1)),
            Arrays.asList(
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 1),
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 9)
            )
        ));
        pool.addMember(new Mutation(
            new Mutation.Output(BlockRegistry.SAPLING.get(), Mutation.basicStrategy(2)),
            Arrays.asList(
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 2),
                Mutation.Source.exact(BlockRegistry.SAPLING.get(), 10)
            )
        ));
    }

    private static void registerMutandisExtremisMutations() {
        MutationPool pool = MutationRegistry.createPool(
            MutationPoolType.MUTANDIS_EXTREMIS,
            "nei.bewitched.mutation.mutandis_extremis"
        );
        pool.addCatalyst(new ItemStack(ItemRegistry.MUTANDIS_EXTREMIS.get()));

        for (Mutation mutation : MutationRegistry.getMembers(MutationPoolType.MUTANDIS)) {
            pool.addMember(mutation);
        }
        for (Mutation mutation : Config.mutandisExtremisAdditionalMutations) {
            pool.addMember(mutation);
        }

        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.potatoes, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.carrots, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.nether_wart, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.wheat, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.reeds, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.cactus, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.pumpkin_stem, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(Blocks.melon_stem, 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(BlockRegistry.MANDRAKE_CROP.get(), 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(BlockRegistry.BELLADONNA_CROP.get(), 0));
        pool.addMember(Mutation.primitiveAnyMetaMutation(BlockRegistry.WATER_ARTICHOKE_CROP.get(), 0));
    }

    private static void registerMutandisExtremisGrassMutations() {
        MutationPool pool = MutationRegistry.createPool(
            MutationPoolType.MUTANDIS_EXTREMIS_GRASS,
            "nei.bewitched.mutation.mutandis_extremis_grass"
        );
        pool.addCatalyst(new ItemStack(ItemRegistry.MUTANDIS_EXTREMIS.get()));

        for (Mutation mutation : Config.mutandisExtremisAdditionalGrassMutations) {
            pool.addMember(mutation);
        }

        pool.addMember(Mutation.primitiveMutation(Blocks.grass, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.mycelium, 0));
    }

    private static void registerFlowerPotMutations() {
        MutationPool pool = MutationRegistry.createPool(
            MutationPoolType.FLOWER_POT,
            "nei.bewitched.mutation.flower_pot"
        );
        pool.addCatalyst(new ItemStack(ItemRegistry.MUTANDIS.get()));
        pool.addCatalyst(new ItemStack(ItemRegistry.MUTANDIS_EXTREMIS.get()));

        for (Mutation mutation : Config.mutandisAdditionalFlowerPotMutations) {
            pool.addMember(mutation);
        }

        pool.addMember(Mutation.primitiveMutation(Blocks.yellow_flower, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.red_flower, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.cactus, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.brown_mushroom, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.red_mushroom, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.deadbush, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.tallgrass, 2));

        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 0));
        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 1));
        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 2));
        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 3));
        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 4));
        pool.addMember(Mutation.primitiveMutation(Blocks.sapling, 5));
    }
}
