package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBewitchedSlab extends BlockSlab {

    public static final String[] VARIANTS = {"rowan", "alder", "hawthorn"};

    private BlockBewitchedSlab singleSlab;

    public BlockBewitchedSlab(boolean isDouble) {
        super(isDouble, Material.wood);
        setCreativeTab(Bewitched.CREATIVE_TAB);
        useNeighborBrightness = true;
    }

    public BlockBewitchedSlab setSingleSlab(BlockBewitchedSlab single) {
        this.singleSlab = single;
        return this;
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[VARIANTS.length];
        for (int i = 0; i < VARIANTS.length; i++) {
            icons[i] = reg.registerIcon(Bewitched.MODID + ":planks_" + VARIANTS[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[MathHelper.clamp_int(meta & 7, 0, VARIANTS.length - 1)];
    }

    @Override
    public String func_150002_b(int meta) {
        if (meta < 0 || meta >= VARIANTS.length) {
            meta = 0;
        }
        return super.getUnlocalizedName() + "." + VARIANTS[meta];
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(singleSlab != null ? singleSlab : this);
    }

    @Override
    protected ItemStack createStackedBlock(int meta) {
        Block singleBlock = singleSlab != null ? singleSlab : this;
        return new ItemStack(Item.getItemFromBlock(singleBlock), 2, meta & 7);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(singleSlab != null ? singleSlab : this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        if (!field_150004_a) {
            for (int i = 0; i < VARIANTS.length; i++) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn(world.getBlockMetadata(x, y, z)) ? 1 : 20;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn(world.getBlockMetadata(x, y, z)) ? 1 : 5;
    }

    private static boolean isHawthorn(int meta) {
        return (meta & 7) == 2;
    }
}
