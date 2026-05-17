package com.dipo33.bewitched.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBewitchedMultiTexture extends ItemMultiTexture {

    public interface VariantBlock {
        String[] getVariants();
    }

    public ItemBewitchedMultiTexture(Block block) {
        super(block, block, ((VariantBlock) block).getVariants());
    }
}
