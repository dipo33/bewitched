package com.dipo33.bewitched.init;

import com.dipo33.bewitched.block.BlockBewitchedPlanks;
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
        OreDictionary.registerOre("plankWood", new ItemStack(BewitchedBlocks.PLANKS.get(), 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("slabWood", new ItemStack(BewitchedBlocks.SLAB.get(), 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("stairWood", BewitchedBlocks.STAIRS_ROWAN.get());
        OreDictionary.registerOre("stairWood", BewitchedBlocks.STAIRS_ALDER.get());
        OreDictionary.registerOre("stairWood", BewitchedBlocks.STAIRS_HAWTHORN.get());

        OreDictionary.registerOre("treeLeaves", new ItemStack(BewitchedBlocks.LEAVES.get(), 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(BewitchedBlocks.SAPLING.get(), 1, OreDictionary.WILDCARD_VALUE));
    }

    private static void registerFuelHandlers() {
        GameRegistry.registerFuelHandler(fuel -> {
            Block block = Block.getBlockFromItem(fuel.getItem());
            if (block == BewitchedBlocks.LOG.get() || block == BewitchedBlocks.PLANKS.get()) {
                return 300;
            }
            return 0;
        });
    }

    private static void registerFireBehavior() {
        Blocks.fire.setFireInfo(BewitchedBlocks.LEAVES.get(), 30, 60);
        Blocks.fire.setFireInfo(BewitchedBlocks.LOG.get(), 5, 5);
        Blocks.fire.setFireInfo(BewitchedBlocks.PLANKS.get(), 5, 20);
        Blocks.fire.setFireInfo(BewitchedBlocks.SLAB.get(), 5, 20);
        Blocks.fire.setFireInfo(BewitchedBlocks.SLAB_DOUBLE.get(), 5, 20);
        Blocks.fire.setFireInfo(BewitchedBlocks.STAIRS_ROWAN.get(), 5, 20);
        Blocks.fire.setFireInfo(BewitchedBlocks.STAIRS_ALDER.get(), 5, 20);
        Blocks.fire.setFireInfo(BewitchedBlocks.STAIRS_HAWTHORN.get(), 1, 1);
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

        for (int i = 0; i < BlockBewitchedPlanks.VARIANTS.length; i++) {
            GameRegistry.addShapelessRecipe(
                new ItemStack(BewitchedBlocks.PLANKS.get(), 4, i),
                new ItemStack(BewitchedBlocks.LOG.get(), 1, i)
            );
            GameRegistry.addShapedRecipe(
                new ItemStack(BewitchedBlocks.SLAB.get(), 6, i),
                "###",
                '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, i)
            );
        }

        GameRegistry.addShapedRecipe(new ItemStack(BewitchedBlocks.STAIRS_ROWAN.get(), 4),
            "#  ", "## ", "###", '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, 0));
        GameRegistry.addShapedRecipe(new ItemStack(BewitchedBlocks.STAIRS_ALDER.get(), 4),
            "#  ", "## ", "###", '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, 1));
        GameRegistry.addShapedRecipe(new ItemStack(BewitchedBlocks.STAIRS_HAWTHORN.get(), 4),
            "#  ", "## ", "###", '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, 2));

        GameRegistry.addShapedRecipe(new ItemStack(BewitchedItems.ALDER_DOOR.get(), 1),
            "##", "##", "##", '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, 1));

        GameRegistry.addShapedRecipe(new ItemStack(BewitchedItems.HAWTHORN_DOOR.get(), 1),
            "##", "##", "##", '#', new ItemStack(BewitchedBlocks.PLANKS.get(), 1, 2));

        GameRegistry.addShapedRecipe(new ItemStack(BewitchedItems.CONCEALING_DOOR.get(), 1),
            " O ",
            "OAO",
            " O ",
            'O', new ItemStack(Blocks.planks, 1, 0),
            'A', new ItemStack(BewitchedItems.ALDER_DOOR.get()));

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
