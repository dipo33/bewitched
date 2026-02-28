package com.dipo33.bewitched.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;


@SideOnly(Side.CLIENT)
public class EntityMutandisFX extends EntityFX {

    public EntityMutandisFX(World world, double x, double y, double z,
                            double mx, double my, double mz) {
        super(world, x, y, z, mx, my, mz);

        this.particleGravity = 0.0F;
        this.particleScale = 1f;
        this.particleMaxAge = 20 + this.rand.nextInt(10);

        this.setRBGColorF(1F, 1F, 1F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY += 0.002;
        this.particleAlpha = 1.0F - (float) this.particleAge / (float) this.particleMaxAge;
    }

    public static void spawnMutandisFX(World world, double x, double y, double z) {
        double radius = 0.8;
        int count = 32;

        for (int i = 0; i < count; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 2 * radius;
            double offsetY = (world.rand.nextDouble() - 0.5) * radius;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 2 * radius;

            double motionX = (world.rand.nextDouble() - 0.5) * 0.05;
            double motionY = world.rand.nextDouble() * 0.03;
            double motionZ = (world.rand.nextDouble() - 0.5) * 0.05;

            EntityFX fx = new EntityMutandisFX(
                world,
                x + 0.5 + offsetX,
                y + 0.5 + offsetY,
                z + 0.5 + offsetZ,
                motionX, motionY, motionZ
            );

            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }
}
