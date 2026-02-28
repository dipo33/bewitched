package com.dipo33.bewitched.client.effect;

import com.dipo33.bewitched.client.effect.entity.EntityMutandisFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class MutandisFX implements IEffect {

    @Override
    public void play(final World world, final double x, final double y, final double z) {
        double radius = 0.8;
        int count = 32;

        for (int i = 0; i < count; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 2 * radius;
            double offsetY = (world.rand.nextDouble() - 0.5) * radius;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 2 * radius;

            double motionX = (world.rand.nextDouble() - 0.5) * 0.05;
            double motionY = world.rand.nextDouble() * 0.03;
            double motionZ = (world.rand.nextDouble() - 0.5) * 0.05;

            EntityFX fx = new EntityMutandisFX(world, x + 0.5 + offsetX, y + 0.5 + offsetY, z + 0.5 + offsetZ, motionX, motionY, motionZ);

            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }
}
