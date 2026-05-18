package com.dipo33.bewitched.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BewitchedRecipes {

    public static void init() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {
        // TODO: Temporary recipe until cauldron recipes are implemented
        GameRegistry.addShapelessRecipe(
            new ItemStack(BewitchedItems.MUTANDIS.get(), 6),
            Items.egg,
            BewitchedItems.MANDRAKE_ROOT.get(),
            Items.potionitem
        );

        // TODO: Temporary recipe until cauldron recipes are implemented
        GameRegistry.addShapelessRecipe(
            new ItemStack(BewitchedItems.MUTANDIS_EXTREMIS.get(), 1),
            BewitchedItems.MUTANDIS.get(),
            Items.nether_wart
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(BewitchedItems.EARMUFFS.get(), 1),
            "lll",
            "l l",
            "w w",
            'l', new ItemStack(Items.leather),
            'w', new ItemStack(Blocks.wool)
        );

    }
}
