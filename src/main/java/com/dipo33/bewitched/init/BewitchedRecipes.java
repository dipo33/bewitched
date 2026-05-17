package com.dipo33.bewitched.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BewitchedRecipes {

    public static void init() {
        registerCraftingRecipes();
        registerOreDictEntries();
        registerFuelHandlers();
        registerFireBehavior();
    }

    private static void registerOreDictEntries() {
        OreDictionary.registerOre("logWood", new ItemStack(BewitchedBlocks.LOG.get(), 1, OreDictionary.WILDCARD_VALUE));
    }

    private static void registerFuelHandlers() {
        GameRegistry.registerFuelHandler(fuel -> {
            if (Block.getBlockFromItem(fuel.getItem()) == BewitchedBlocks.LOG.get()) {
                return 300;
            }
            return 0;
        });
    }

    private static void registerFireBehavior() {
        Blocks.fire.setFireInfo(BewitchedBlocks.LOG.get(), 5, 5);
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
