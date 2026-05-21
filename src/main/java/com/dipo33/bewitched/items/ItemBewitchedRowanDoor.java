package com.dipo33.bewitched.items;

import com.dipo33.bewitched.init.BewitchedBlocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBewitchedRowanDoor extends ItemBewitchedDoor {

    public ItemBewitchedRowanDoor() {
        super(BewitchedBlocks.ROWAN_DOOR_BLOCK);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float subX, float subY, float subZ) {
        boolean placed = super.onItemUse(stack, player, world, x, y, z, side, subX, subY, subZ);
        if (placed && !world.isRemote) {
            int doorBottomY = y + 1;
            ItemStack key = ItemRowanDoorKey.create(x, doorBottomY, z, world.provider.dimensionId, world.provider.getDimensionName());
            EntityItem keyEntity = new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, key);
            keyEntity.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(keyEntity);
        }
        return placed;
    }
}
