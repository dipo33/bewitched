package com.dipo33.bewitched.items;

import net.minecraft.block.Block;

public class ItemBewitchedLeaves extends ItemBewitchedMultiTexture {

    public ItemBewitchedLeaves(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        return damage | 4;
    }
}
