package com.dipo33.bewitched.items;

import com.dipo33.bewitched.block.BlockBewitchedSlab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemBewitchedSlab extends ItemSlab {

    public ItemBewitchedSlab(Block block, BlockBewitchedSlab single, BlockBewitchedSlab dbl) {
        super(block, single, dbl, false);
    }
}
