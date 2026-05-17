package com.dipo33.bewitched.integration.waila;

import com.dipo33.bewitched.block.BlockBewitchedCrops;
import com.dipo33.bewitched.block.BlockBewitchedLog;
import com.dipo33.bewitched.init.BewitchedBlocks;
import com.dipo33.bewitched.init.BewitchedItems;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HUDHandlerBewitched implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        if (block == BewitchedBlocks.SNOW_WISP_CROP.get()) {
            return new ItemStack(BewitchedItems.ICY_NEEDLE.get());
        } else if (block instanceof BlockBewitchedCrops blockCrops) {
            return new ItemStack(blockCrops.func_149865_P());
        } else if (block instanceof BlockBewitchedLog) {
            return new ItemStack(block, 1, accessor.getMetadata() & 3);
        }

        return null;
    }

    @Override
    public List<String> getWailaHead(final ItemStack itemStack, final List<String> currentTip, final IWailaDataAccessor accessor,
                                     final IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        if (block instanceof BlockBewitchedCrops) {
            String name = DisplayUtil.itemDisplayNameShort(new ItemStack(block));
            if (name != null) {
                if (currentTip.isEmpty()) {
                    currentTip.add(name);
                } else {
                    currentTip.set(0, name);
                }
            }
        }

        return currentTip;
    }

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currentTip, final IWailaDataAccessor accessor,
                                     final IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    public List<String> getWailaTail(final ItemStack itemStack, final List<String> currentTip, final IWailaDataAccessor accessor,
                                     final IWailaConfigHandler config) {
        return currentTip;
    }

    @Override
    public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag, final World world, final int x,
                                     final int y, final int z) {
        return tag;
    }

    public static void register(IWailaRegistrar registrar) {
        IWailaDataProvider provider = new HUDHandlerBewitched();
        registrar.registerStackProvider(provider, BlockBewitchedCrops.class);
        registrar.registerHeadProvider(provider, BlockBewitchedCrops.class);
        registrar.registerStackProvider(provider, BlockBewitchedLog.class);
    }
}
