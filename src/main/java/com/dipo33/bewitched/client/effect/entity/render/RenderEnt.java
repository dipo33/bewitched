package com.dipo33.bewitched.client.effect.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEnt extends RenderLiving {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation("bewitched:textures/entity/ent.png");

    public RenderEnt(final ModelBase model, final float shadowSize) {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TEXTURE;
    }
}
