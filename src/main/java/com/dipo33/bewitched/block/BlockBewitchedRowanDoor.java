package com.dipo33.bewitched.block;

import com.dipo33.bewitched.init.BewitchedItems;
import com.dipo33.bewitched.items.ItemRowanDoorKey;
import com.dipo33.bewitched.items.ItemRowanKeyring;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBewitchedRowanDoor extends BlockBewitchedDoor {

    public BlockBewitchedRowanDoor() {
        super(BewitchedItems.ROWAN_DOOR, false);
        setHardness(5.0F);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        int meta = world.getBlockMetadata(x, y, z);
        int bottomY = (meta & 8) != 0 ? y - 1 : y;
        if (!playerHasKey(player, x, bottomY, z, world.provider.dimensionId)) {
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, side, subX, subY, subZ);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 8) == 0) {
            boolean remove = false;
            if (world.getBlock(x, y + 1, z) != this) {
                world.setBlockToAir(x, y, z);
                remove = true;
            }
            if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
                world.setBlockToAir(x, y, z);
                remove = true;
                if (world.getBlock(x, y + 1, z) == this) {
                    world.setBlockToAir(x, y + 1, z);
                }
            }
            if (remove && !world.isRemote) {
                dropBlockAsItem(world, x, y, z, meta, 0);
            }
        } else {
            if (world.getBlock(x, y - 1, z) != this) {
                world.setBlockToAir(x, y, z);
            }
            if (neighbor != this) {
                onNeighborBlockChange(world, x, y - 1, z, neighbor);
            }
        }
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return null;
    }

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        if (event.block != this) {
            return;
        }
        if ((event.blockMetadata & 8) != 0) {
            return;
        }

        event.drops.clear();
        EntityPlayer player = event.harvester;
        if (player != null && playerHasKey(player, event.x, event.y, event.z, event.world.provider.dimensionId)) {
            event.drops.add(new ItemStack(BewitchedItems.ROWAN_DOOR.get()));
        } else {
            event.drops.add(new ItemStack(Items.stick, 24));
        }
    }

    private static boolean playerHasKey(EntityPlayer player, int x, int y, int z, int dimensionId) {
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.inventory.mainInventory[i];
            if (slot == null) {
                continue;
            }
            if (slot.getItem() instanceof ItemRowanDoorKey && ItemRowanDoorKey.matches(slot, x, y, z, dimensionId)) {
                return true;
            }
            if (slot.getItem() instanceof ItemRowanKeyring && ItemRowanKeyring.matches(slot, x, y, z, dimensionId)) {
                return true;
            }
        }
        return false;
    }
}
