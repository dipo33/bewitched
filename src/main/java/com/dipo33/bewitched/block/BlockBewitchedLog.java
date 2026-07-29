package com.dipo33.bewitched.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import com.dipo33.bewitched.entity.EntityEnt;
import com.dipo33.bewitched.items.ItemBewitchedMultiTexture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBewitchedLog extends BlockLog implements ItemBewitchedMultiTexture.VariantBlock {

    public static final String[] VARIANTS = {"rowan", "alder", "hawthorn"};

    public String[] getVariants() {
        return VARIANTS;
    }

    @SideOnly(Side.CLIENT)
    private IIcon[][] icons; // [species][0=bark, 1=end-grain]

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[VARIANTS.length][2];
        for (int i = 0; i < VARIANTS.length; i++) {
            icons[i][0] = reg.registerIcon(this.getTextureName() + "_" + VARIANTS[i] + "_side");
            icons[i][1] = reg.registerIcon(this.getTextureName() + "_" + VARIANTS[i] + "_top");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int species = MathHelper.clamp_int(meta & 3, 0, VARIANTS.length - 1);
        int axis = (meta >> 2) & 3;

        boolean isEnd = switch (axis) {
            case 1 -> (side == 4 || side == 5); // X-axis: west/east faces
            case 2 -> (side == 2 || side == 3); // Z-axis: north/south faces
            default -> (side == 0 || side == 1); // Y-axis (and all-bark): top/bottom
        };

        return icons[species][isEnd ? 1 : 0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < VARIANTS.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta); // vanilla leaf-decay trigger
        EntityEnt.trySpawnFromLogBreak(world, x, y, z);
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn(world.getBlockMetadata(x, y, z)) ? 0 : 5;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return isHawthorn(world.getBlockMetadata(x, y, z)) ? 1 : 5;
    }

    private static boolean isHawthorn(int meta) {
        return (meta & 3) == 2;
    }
}
