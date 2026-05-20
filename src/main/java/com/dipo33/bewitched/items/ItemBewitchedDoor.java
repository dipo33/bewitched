package com.dipo33.bewitched.items;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBewitchedDoor extends Item {

    private final Supplier<Block> blockSupplier;

    public ItemBewitchedDoor(Supplier<Block> blockSupplier) {
        this.maxStackSize = 1;
        this.blockSupplier = blockSupplier;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float subX, float subY, float subZ) {
        if (side != 1) {
            return false;
        }
        y++;
        if (!player.canPlayerEdit(x, y, z, side, stack) || !player.canPlayerEdit(x, y + 1, z, side, stack)) {
            return false;
        }
        Block block = blockSupplier.get();
        if (!block.canPlaceBlockAt(world, x, y, z)) {
            return false;
        }
        int facing = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
        ItemDoor.placeDoorBlock(world, x, y, z, facing, block);
        world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5,
            block.stepSound.func_150496_b(),
            (block.stepSound.getVolume() + 1.0F) / 2.0F,
            block.stepSound.getPitch() * 0.8F);
        stack.stackSize--;
        return true;
    }
}
