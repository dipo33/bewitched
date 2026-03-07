package com.dipo33.bewitched.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockBwSapling extends BlockSapling {
    public static final String[] VARIANTS = {"rowan", "alder", "hawthorn"};

    @SideOnly(Side.CLIENT)
    private final IIcon[] ICONS = new IIcon[VARIANTS.length];

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister reg) {
        for (int i = 0; i < VARIANTS.length; i++) {
            ICONS[i] = reg.registerIcon(this.getTextureName() + "_" + VARIANTS[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return ICONS[MathHelper.clamp_int(meta & 7, 0, VARIANTS.length - 1)];
    }

    @Override
    public int damageDropped(final int meta) {
        return MathHelper.clamp_int(meta & 7, 0, VARIANTS.length);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List<ItemStack> list) {
        for (int i = 0; i < VARIANTS.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void func_149878_d(final World world, final int x, final int y, final int z, final Random rng) {
        if (!TerrainGen.saplingGrowTree(world, rng, x, y, z)) {
            return;
        }

        int meta = world.getBlockMetadata(x, y, z) & 7;
        WorldGenerator gen;

        switch (meta) {
            // TODO: Grow actual correct trees
            case 0:
                gen = new WorldGenForest(true, false);
                break;
            case 1:
                gen = new WorldGenMegaPineTree(false, true);
                break;
            case 2:
                gen = new WorldGenMegaJungle(true, 10, 20, 3, 3);
                break;
            default:
                return;
        }

        world.setBlockToAir(x, y, z);

        if (!gen.generate(world, rng, x, y, z)) {
            world.setBlock(x, y, z, this, meta, 4);
        }
    }
}
