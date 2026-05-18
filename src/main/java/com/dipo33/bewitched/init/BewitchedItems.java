package com.dipo33.bewitched.init;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.items.armor.ItemEarmuffs;
import com.dipo33.bewitched.items.ItemMutandis;
import com.dipo33.bewitched.items.ItemWaterSeeds;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class BewitchedItems {

    // Seeds
    public static final ObjectHolder<Item> BELLADONNA_SEEDS = new ObjectHolder<>(() ->
        new ItemSeeds(BewitchedBlocks.BELLADONNA_CROP.get(), Blocks.farmland).setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> WOLFSBANE_SEEDS = new ObjectHolder<>(() ->
        new ItemSeeds(BewitchedBlocks.WOLFSBANE_CROP.get(), Blocks.farmland).setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> WATER_ARTICHOKE_SEEDS = new ObjectHolder<>(() ->
        new ItemWaterSeeds(BewitchedBlocks.WATER_ARTICHOKE_CROP.get(), Blocks.water).setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> MANDRAKE_SEEDS = new ObjectHolder<>(() ->
        new ItemSeeds(BewitchedBlocks.MANDRAKE_CROP.get(), Blocks.farmland).setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> SNOW_WISP_SEEDS = new ObjectHolder<>(() ->
        new ItemSeeds(BewitchedBlocks.SNOW_WISP_CROP.get(), Blocks.farmland).setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> GARLIC = new ObjectHolder<>(() ->
        new ItemSeeds(BewitchedBlocks.GARLIC_CROP.get(), Blocks.farmland).setCreativeTab(Bewitched.CREATIVE_TAB)
    );

    // Crops
    public static final ObjectHolder<Item> BELLADONNA_FLOWER = new ObjectHolder<>(() ->
        new Item().setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> WOLFSBANE_FLOWER = new ObjectHolder<>(() ->
        new Item().setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> WATER_ARTICHOKE_GLOBE = new ObjectHolder<>(() ->
        new Item().setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> MANDRAKE_ROOT = new ObjectHolder<>(() ->
        new Item().setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> ICY_NEEDLE = new ObjectHolder<>(() ->
        new Item().setCreativeTab(Bewitched.CREATIVE_TAB)
    );

    // Wearables
    public static final ObjectHolder<Item> EARMUFFS = new ObjectHolder<>(() ->
        new ItemEarmuffs().setCreativeTab(Bewitched.CREATIVE_TAB)
    );

    // Other
    public static final ObjectHolder<Item> MUTANDIS = new ObjectHolder<>(() ->
        new ItemMutandis().setCreativeTab(Bewitched.CREATIVE_TAB)
    );
    public static final ObjectHolder<Item> MUTANDIS_EXTREMIS = new ObjectHolder<>(() ->
        new ItemMutandis().setCreativeTab(Bewitched.CREATIVE_TAB)
    );

    /**
     * Registers every item declared in this class with the game's item registry.
     */
    public static void registerItems() {
        registerItem(BELLADONNA_SEEDS.get(), "belladonna_seeds");
        registerItem(WOLFSBANE_SEEDS.get(), "wolfsbane_seeds");
        registerItem(WATER_ARTICHOKE_SEEDS.get(), "water_artichoke_seeds");
        registerItem(MANDRAKE_SEEDS.get(), "mandrake_seeds");
        registerItem(SNOW_WISP_SEEDS.get(), "snow_wisp_seeds");
        registerItem(GARLIC.get(), "garlic_seeds");

        registerItem(BELLADONNA_FLOWER.get(), "belladonna_flower");
        registerItem(WOLFSBANE_FLOWER.get(), "wolfsbane_flower");
        registerItem(WATER_ARTICHOKE_GLOBE.get(), "water_artichoke_globe");
        registerItem(MANDRAKE_ROOT.get(), "mandrake_root");
        registerItem(ICY_NEEDLE.get(), "icy_needle");

        registerItem(EARMUFFS.get(), "earmuffs");

        registerItem(MUTANDIS.get(), "mutandis");
        registerItem(MUTANDIS_EXTREMIS.get(), "mutandis_extremis");
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
