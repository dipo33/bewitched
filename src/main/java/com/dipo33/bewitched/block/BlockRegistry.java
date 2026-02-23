package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockRegistry {
    public static Block hemleafCrop = new BwBlockCrops();

    public static void initBlocks() {
    }

    public static void registerBlocks() {
        registerBlock(hemleafCrop, "hemleaf");
    }

    public static void registerBlock(Block block, String name) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
    }
}
