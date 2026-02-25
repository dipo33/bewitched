package com.dipo33.bewitched.items;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.block.BlockRegistry;
import com.dipo33.bewitched.data.ObjectHolder;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemRegistry {

    public static final ObjectHolder<Item> BELLADONNA_SEED = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.BELLADONNA_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );

    public static final ObjectHolder<Item> BELLADONNA_FLOWER = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );

    public static void registerItems() {
        registerItem(BELLADONNA_SEED.get(), "belladonna_seed");
        registerItem(BELLADONNA_FLOWER.get(), "belladonna_flower");
    }

    private static void registerItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setTextureName(Bewitched.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
    }
}
