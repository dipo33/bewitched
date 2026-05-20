package com.dipo33.bewitched.block;

import com.dipo33.bewitched.Bewitched;
import com.dipo33.bewitched.init.BewitchedBlocks;
import com.dipo33.bewitched.init.BewitchedItems;
import com.dipo33.bewitched.items.ItemBewitchedMultiTexture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBewitchedLeaves extends BlockLeaves implements ItemBewitchedMultiTexture.VariantBlock {

    public static final String[] VARIANTS = {"rowan", "alder", "hawthorn"};

    private static final int COLOR_ALDER = 0x399B33;
    private static final int COLOR_HAWTHORN = 0x669066;

    public BlockBewitchedLeaves() {
        setCreativeTab(Bewitched.CREATIVE_TAB);
    }

    @Override
    public String[] getVariants() {
        return VARIANTS;
    }

    @Override
    public String[] func_150125_e() {
        return VARIANTS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        field_150129_M[0] = new IIcon[VARIANTS.length];
        field_150129_M[1] = new IIcon[VARIANTS.length];
        for (int i = 0; i < VARIANTS.length; i++) {
            field_150129_M[0][i] = reg.registerIcon(Bewitched.MODID + ":leaves_" + VARIANTS[i]);
            field_150129_M[1][i] = reg.registerIcon(Bewitched.MODID + ":leaves_" + VARIANTS[i] + "_opaque");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return field_150129_M[field_150127_b][MathHelper.clamp_int(meta & 3, 0, VARIANTS.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, int x, int y, int z) {
        return switch (worldIn.getBlockMetadata(x, y, z) & 3) {
            case 1 -> COLOR_ALDER;
            case 2 -> COLOR_HAWTHORN;
            default -> super.colorMultiplier(worldIn, x, y, z);
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return switch (meta & 3) {
            case 1 -> COLOR_ALDER;
            case 2 -> COLOR_HAWTHORN;
            default -> super.getRenderColor(meta);
        };
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(BewitchedBlocks.SAPLING.get());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < VARIANTS.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    protected int func_150123_b(int meta) {
        return (meta & 3) == 3 ? 40 : 20;
    }

    @Override
    protected void func_150124_c(World world, int x, int y, int z, int metadata, int chance) {
        if ((metadata & 3) == 0 && world.rand.nextInt(chance) == 0) {
            dropBlockAsItem(world, x, y, z, new ItemStack(BewitchedItems.ROWAN_BERRIES.get()));
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 30;
    }
}
