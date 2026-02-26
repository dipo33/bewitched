package com.dipo33.bewitched.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class WaterItemSeeds extends ItemSeeds {
    private final Block crop;

    public WaterItemSeeds(final Block crop, final Block soil) {
        super(crop, soil);
        this.crop = crop;
    }

    @Override
    public boolean onItemUse(final ItemStack item, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side,
                             final float hitX, final float hitY, final float hitZ) {
        return false;
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, player, true);
        if (movingObjectPosition == null) {
            return itemStack;
        } else {
            if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingObjectPosition.blockX;
                int j = movingObjectPosition.blockY;
                int k = movingObjectPosition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return itemStack;
                }

                if (!player.canPlayerEdit(i, j, k, movingObjectPosition.sideHit, itemStack)) {
                    return itemStack;
                }

                if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k)) {
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot =
                        net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, i, j + 1, k);
                    world.setBlock(i, j + 1, k, this.crop);
                    if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(player, blocksnapshot,
                        net.minecraftforge.common.util.ForgeDirection.UP).isCanceled()) {
                        blocksnapshot.restore(true, false);
                        return itemStack;
                    }

                    if (!player.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                }
            }

            return itemStack;
        }
    }
}
