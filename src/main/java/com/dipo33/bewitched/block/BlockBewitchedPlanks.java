package com.dipo33.bewitched.block;

import com.dipo33.bewitched.items.ItemBewitchedMultiTexture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBewitchedPlanks extends Block implements ItemBewitchedMultiTexture.VariantBlock {

    public static final String[] VARIANTS = {"rowan", "alder", "hawthorn"};

    public BlockBewitchedPlanks() {
        super(Material.wood);
    }

    @Override
    public String[] getVariants() {
        return VARIANTS;
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[VARIANTS.length];
        for (int i = 0; i < VARIANTS.length; i++) {
            icons[i] = reg.registerIcon(this.getTextureName() + "_" + VARIANTS[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[MathHelper.clamp_int(meta, 0, VARIANTS.length - 1)];
    }

    @Override
    public int damageDropped(int meta) {
        return MathHelper.clamp_int(meta, 0, VARIANTS.length - 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < VARIANTS.length; i++) {
            list.add(new ItemStack(item, 1, i));
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
        return meta == 2;
    }
}
