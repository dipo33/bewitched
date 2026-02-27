package com.dipo33.bewitched.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWaterSeeds extends ItemSeeds {
    private final Block crop;

    /**
     * Creates a WaterItemSeeds configured to place the specified crop when planted.
     *
     * @param crop
     *     the Block that will be placed as the crop when this seed is planted
     * @param soil
     *     the Block representing the required soil type for planting
     */
    public ItemWaterSeeds(final Block crop, final Block soil) {
        super(crop, soil);
        this.crop = crop;
    }

    /**
     * Prevents the standard item-use planting behavior for this seed item.
     *
     * @return `false` always, indicating the use action did not perform any placement
     */
    @Override
    public boolean onItemUse(final ItemStack item, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side,
                             final float hitX, final float hitY, final float hitZ) {
        return false;
    }

    /**
     * Attempts to plant the seed on the air block immediately above a targeted source water block when the player right-clicks.
     *
     * @return the (possibly modified) ItemStack after the right-click action
     */
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
