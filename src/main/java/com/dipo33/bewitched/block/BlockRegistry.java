package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.data.ObjectHolder;
import com.dipo33.bewitched.items.ItemRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockRegistry {
    public static final ObjectHolder<Block> BELLADONNA_CROP = new ObjectHolder<>(() ->
        new BwBlockCrops(ItemRegistry.BELLADONNA_SEED, ItemRegistry.BELLADONNA_FLOWER)
            .setStages(4)
    );

    public static void registerBlocks() {
        registerBlock(BELLADONNA_CROP.get(), "belladonna");
    }

    public static void registerBlock(Block block, String name) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
    }
}
