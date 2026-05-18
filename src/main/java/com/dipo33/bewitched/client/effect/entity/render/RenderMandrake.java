package com.dipo33.bewitched.client.effect.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMandrake extends RenderLiving {
    // TODO: change to mandrake texture
    private static final ResourceLocation texture =
        new ResourceLocation("minecraft:textures/blocks/dirt.png");

    public RenderMandrake(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
