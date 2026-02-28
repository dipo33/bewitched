package com.dipo33.bewitched.client.effect.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
}
