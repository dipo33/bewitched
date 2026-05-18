package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBewitchedStairs extends BlockStairs {

    private final boolean isHawthorn;

    public BlockBewitchedStairs(Block planks, int variant) {
        super(planks, variant);
        this.isHawthorn = variant == 2;
        setCreativeTab(Bewitched.CREATIVE_TAB);
        useNeighborBrightness = true;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn ? 1 : 20;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn ? 1 : 5;
    }
}
