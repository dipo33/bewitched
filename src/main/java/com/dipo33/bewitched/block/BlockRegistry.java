package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.items.ItemRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;

public class BlockRegistry {
    public static final ObjectHolder<Block> BELLADONNA_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.BELLADONNA_SEED, ItemRegistry.BELLADONNA_FLOWER)
            .setStages(5)
    );
    public static final ObjectHolder<Block> WOLFSBANE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.WOLFSBANE_SEED, ItemRegistry.WOLFSBANE_FLOWER)
            .setStages(8)
    );
    public static final ObjectHolder<Block> WATER_ARTICHOKE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.WATER_ARTICHOKE_SEED, ItemRegistry.WATER_ARTICHOKE_GLOBE)
            .setStages(5)
    );
    public static final ObjectHolder<Block> MANDRAKE_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.MANDRAKE_SEED, ItemRegistry.MANDRAKE_ROOT)
            .setStages(5)
    );
    public static final ObjectHolder<Block> SNOW_WISP_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.SNOW_WISP_SEED, new ObjectHolder<>(() -> Items.snowball))
            .setStages(5)
            .addAdditionalDrops(ItemRegistry.ICY_NEEDLE, 0.1D)
    );
    public static final ObjectHolder<Block> GARLIC_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.GARLIC, ItemRegistry.GARLIC)
            .setStages(6)
    );

    public static void registerBlocks() {
        registerBlock(BELLADONNA_CROP.get(), "belladonna");
        registerBlock(WOLFSBANE_CROP.get(), "wolfsbane");
        registerBlock(WATER_ARTICHOKE_CROP.get(), "water_artichoke");
        registerBlock(MANDRAKE_CROP.get(), "mandrake");
        registerBlock(SNOW_WISP_CROP.get(), "snow_wisp");
        registerBlock(GARLIC_CROP.get(), "garlic");
    }

    public static void registerBlock(Block block, String name) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
    }
}
