package com.dipo33.bewitched.items;

import com.dipo33.bewitched.block.BlockBwSapling;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemSaplingMultiTexture extends ItemMultiTexture {
    public ItemSaplingMultiTexture(final Block block) {
        super(block, block, BlockBwSapling.VARIANTS);
    }
}
