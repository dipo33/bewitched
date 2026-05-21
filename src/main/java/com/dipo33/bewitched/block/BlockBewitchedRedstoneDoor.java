package com.dipo33.bewitched.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBewitchedRedstoneDoor extends BlockBewitchedDoor {

    public BlockBewitchedRedstoneDoor(Supplier<Item> itemSupplier) {
        super(itemSupplier);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return func_150015_f(world, x, y, z) ? 15 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        return (side == 1 && func_150015_f(world, x, y, z)) ? 15 : 0;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        onNeighborBlockChangeStructuralOnly(world, x, y, z, neighbor);
    }
}
