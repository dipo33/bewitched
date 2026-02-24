package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockRegistry {
    public static Block belladonnaCrop = new BwBlockCrops()
        .setStages(4);

    public static void initBlocks() {
    }

    public static void registerBlocks() {
        registerBlock(belladonnaCrop, "belladonna");
    }

    public static void registerBlock(Block block, String name) {
        block.setBlockTextureName(Bewitched.MODID + ":" + name);
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
    }
}
