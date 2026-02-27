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

    // Seeds
    public static final ObjectHolder<Item> BELLADONNA_SEED = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.BELLADONNA_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> WOLFSBANE_SEED = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.WOLFSBANE_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> WATER_ARTICHOKE_SEED = new ObjectHolder<>(() ->
        new ItemWaterSeeds(BlockRegistry.WATER_ARTICHOKE_CROP.get(), Blocks.water).setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> MANDRAKE_SEED = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.MANDRAKE_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> SNOW_WISP_SEED = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.SNOW_WISP_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> GARLIC = new ObjectHolder<>(() ->
        new ItemSeeds(BlockRegistry.GARLIC_CROP.get(), Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials)
    );

    // Crops
    public static final ObjectHolder<Item> BELLADONNA_FLOWER = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> WOLFSBANE_FLOWER = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> WATER_ARTICHOKE_GLOBE = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> MANDRAKE_ROOT = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );
    public static final ObjectHolder<Item> ICY_NEEDLE = new ObjectHolder<>(() ->
        new Item().setCreativeTab(CreativeTabs.tabMaterials)
    );

    /**
     * Registers every item declared in this class with the game's item registry.
     */
    public static void registerItems() {
        registerItem(BELLADONNA_SEED.get(), "belladonna_seed");
        registerItem(WOLFSBANE_SEED.get(), "wolfsbane_seed");
        registerItem(WATER_ARTICHOKE_SEED.get(), "water_artichoke_seed");
        registerItem(MANDRAKE_SEED.get(), "mandrake_seed");
        registerItem(SNOW_WISP_SEED.get(), "snow_wisp_seed");
        registerItem(GARLIC.get(), "garlic_seed");

        registerItem(BELLADONNA_FLOWER.get(), "belladonna_flower");
        registerItem(WOLFSBANE_FLOWER.get(), "wolfsbane_flower");
        registerItem(WATER_ARTICHOKE_GLOBE.get(), "water_artichoke_globe");
        registerItem(MANDRAKE_ROOT.get(), "mandrake_root");
        registerItem(ICY_NEEDLE.get(), "icy_needle");
    }

    /**
     * Configure an item's unlocalized name and texture name then register it with the game registry.
     *
     * @param item
     *     the Item to configure and register
     * @param name
     *     the registry name used for the item's texture, unlocalized name, and registration
     */
    private static void registerItem(Item item, String name) {
        item.setUnlocalizedName(name);
        item.setTextureName(Bewitched.MODID + ":" + name);
        GameRegistry.registerItem(item, name);
    }
}
