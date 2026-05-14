package com.dipo33.bewitched.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class BewitchedModel extends ModelBiped {

    protected boolean hideBody = true;
    protected boolean hideArms = true;
    protected boolean hideLegs = true;
    protected boolean hideHeadwear = true;

    public BewitchedModel(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        bipedHead.cubeList.clear();
    }

    /**
     * Call this in constructor AFTER model parts are created
     */
    protected void setArmorPartVisibility() {
        bipedHead.showModel = !hideHeadwear;
        bipedHeadwear.showModel = false;
        bipedBody.showModel = !hideBody;
        bipedRightArm.showModel = !hideArms;
        bipedLeftArm.showModel = !hideArms;
        bipedRightLeg.showModel = !hideLegs;
        bipedLeftLeg.showModel = !hideLegs;
    }

    /**
     * Attach helper
     */
    protected void attachToHead(ModelRenderer part) {
        this.bipedHead.addChild(part);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if (entity != null) {
            this.isSneak = entity.isSneaking();
        }

        if (entity instanceof EntityLivingBase) {
            this.heldItemRight = ((EntityLivingBase) entity).getHeldItem() != null ? 1 : 0;
        }

        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }
}
