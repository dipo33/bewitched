package com.dipo33.bewitched.client.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public interface IEffect {
    void play(World world, double x, double y, double z);
}
