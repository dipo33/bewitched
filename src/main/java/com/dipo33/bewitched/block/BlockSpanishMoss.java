package com.dipo33.bewitched.block;

import net.minecraft.block.BlockVine;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

public class BlockSpanishMoss extends BlockVine implements IShearable {

    protected BlockSpanishMoss() {
    }

    @Override
    public boolean isLadder(final IBlockAccess world, final int x, final int y, final int z, final EntityLivingBase entity) {
        return false;
    }
}
