package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockBewitchedDoor extends BlockDoor {

    private final Supplier<Item> itemSupplier;

    public BlockBewitchedDoor(Supplier<Item> itemSupplier) {
        super(Material.wood);
        setHardness(3.0F);
        setStepSound(Block.soundTypeWood);
        setCreativeTab(Bewitched.CREATIVE_TAB);
        this.itemSupplier = itemSupplier;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        int meta = func_150012_g(world, x, y, z);
        int newMeta = (meta & 7) ^ 4;
        int bottomY = (meta & 8) == 0 ? y : y - 1;

        world.setBlockMetadataWithNotify(x, bottomY, z, newMeta, 2);
        world.markBlockRangeForRenderUpdate(x, bottomY, z, x, bottomY + 1, z);
        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);

        world.notifyBlocksOfNeighborChange(x, bottomY, z, this);
        world.notifyBlocksOfNeighborChange(x, bottomY + 1, z, this);

        return true;
    }

    protected void onNeighborBlockChangeStructuralOnly(World world, int x, int y, int z, Block neighbor) {
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
                onNeighborBlockChangeStructuralOnly(world, x, y - 1, z, neighbor);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        int bottomY = (meta & 8) != 0 ? y - 1 : y;
        world.notifyBlocksOfNeighborChange(x, bottomY, z, this);
        world.notifyBlocksOfNeighborChange(x, bottomY - 1, z, this);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return (meta & 8) != 0 ? null : itemSupplier.get();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z) {
        return itemSupplier.get();
    }
}
