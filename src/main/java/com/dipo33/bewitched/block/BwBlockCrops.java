package com.dipo33.bewitched.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BwBlockCrops extends BlockCrops {

    private int stages = 4;

    // prefer earlier stages to grow faster
    private static final int[][] META_STAGE_TO_ICON = new int[][]{
        new int[]{0, 0, 0, 0, 0, 0, 0, 0}, // 1 stage
        new int[]{0, 0, 0, 0, 0, 0, 0, 1}, // 2 stages
        new int[]{0, 0, 0, 1, 1, 1, 1, 2}, // 3 stages
        new int[]{0, 0, 1, 1, 2, 2, 2, 3}, // 4 stages
        new int[]{0, 1, 1, 2, 2, 3, 3, 4}, // 5 stages
        new int[]{0, 1, 2, 3, 3, 4, 4, 5}, // 6 stages
        new int[]{0, 1, 2, 3, 4, 5, 5, 6}, // 7 stages
        new int[]{0, 1, 2, 3, 4, 5, 6, 7}, // 8 stages
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BwBlockCrops() {
        super();
    }

    public BwBlockCrops setStages(int stages) {
        this.stages = stages;
        return this;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.icons[META_STAGE_TO_ICON[this.stages - 1][meta]];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.icons = new IIcon[this.stages];

        for (int i = 0; i < this.icons.length; ++i) {
            this.icons[i] = reg.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
}
