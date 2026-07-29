package com.dipo33.bewitched.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

public class ModelEnt extends ModelBase {
    protected final ModelRenderer head;
    protected final ModelRenderer face;
    protected final ModelRenderer body;
    protected final ModelRenderer legs;
    protected final ModelRenderer rightLeg;
    protected final ModelRenderer rightThigh_r1;
    protected final ModelRenderer leftLeg;
    protected final ModelRenderer leftThigh_r1;
    protected final ModelRenderer arms;
    protected final ModelRenderer rightArm;
    protected final ModelRenderer rightHand;
    protected final ModelRenderer rightFingers;
    protected final ModelRenderer leftArm;
    protected final ModelRenderer leftHand;
    protected final ModelRenderer leftFingers;
    protected final ModelRenderer canopy;

    public ModelEnt() {
        textureWidth = 16;
        textureHeight = 16;

        head = new ModelRenderer(this);
        head.setRotationPoint(-2.0F, -15.0F, -4.5F);
        head.cubeList.add(new ModelBox(head, -17, -9, -3.5F, -14.0F, -1.5F, 11, 14, 10, 0.0F));

        face = new ModelRenderer(this);
        face.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(face);
        face.cubeList.add(new ModelBox(face, 1, 0, 5.5F, -9.0F, -2.5F, 2, 3, 1, 0.0F));
        face.cubeList.add(new ModelBox(face, 1, 0, -3.5F, -9.0F, -2.5F, 2, 3, 1, 0.0F));
        face.cubeList.add(new ModelBox(face, 0, -1, 0.5F, -9.0F, -2.5F, 3, 3, 1, 0.0F));
        face.cubeList.add(new ModelBox(face, 0, 0, -1.5F, 0.0F, -2.5F, 2, 3, 2, 0.0F));
        face.cubeList.add(new ModelBox(face, 0, 0, 3.5F, 0.0F, -2.5F, 2, 3, 2, 0.0F));
        face.cubeList.add(new ModelBox(face, -8, -9, -3.5F, -6.0F, -2.5F, 11, 6, 1, 0.0F));
        face.cubeList.add(new ModelBox(face, -8, -9, -3.5F, -14.0F, -2.5F, 11, 5, 1, 0.0F));

        body = new ModelRenderer(this);
        body.setRotationPoint(-2.0F, -15.0F, -4.5F);
        body.cubeList.add(new ModelBox(body, -24, -13, -5.5F, -2.0F, -0.5F, 15, 13, 13, 0.0F));
        body.cubeList.add(new ModelBox(body, -20, -11, -4.5F, 11.0F, 0.5F, 13, 2, 11, 0.0F));
        body.cubeList.add(new ModelBox(body, -24, -13, -5.5F, 13.0F, -0.5F, 15, 3, 13, 0.0F));
        body.cubeList.add(new ModelBox(body, -2, -3, -0.5F, 13.0F, -1.5F, 5, 5, 1, 0.0F));
        body.cubeList.add(new ModelBox(body, -14, -3, -0.5F, 16.0F, -0.5F, 5, 2, 13, 0.0F));
        body.cubeList.add(new ModelBox(body, -5, -1, 9.5F, -2.0F, 3.0F, 3, 6, 6, 0.0F));
        body.cubeList.add(new ModelBox(body, -5, -1, -8.5F, -2.0F, 3.0F, 3, 6, 6, 0.0F));

        legs = new ModelRenderer(this);
        legs.setRotationPoint(-2.0F, -15.0F, -4.5F);


        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-2.0F, 15.0F, 6.0F);
        legs.addChild(rightLeg);
        rightLeg.cubeList.add(new ModelBox(rightLeg, -8, -4, -3.0F, 9.0F, -4.5F, 6, 7, 6, 0.0F));
        rightLeg.cubeList.add(new ModelBox(rightLeg, -9, -4, -3.0F, 16.0F, -4.5F, 6, 5, 7, 0.0F));
        rightLeg.cubeList.add(new ModelBox(rightLeg, -11, -4, -3.0F, 21.0F, -4.5F, 6, 3, 9, 0.0F));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 0, -3.0F, 22.0F, -6.5F, 2, 2, 2, 0.0F));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 1, 1, 0.0F, 22.0F, -6.5F, 1, 2, 2, 0.0F));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 1, 1, 2.0F, 22.0F, -6.5F, 1, 2, 2, 0.0F));

        rightThigh_r1 = new ModelRenderer(this);
        rightThigh_r1.setRotationPoint(1.5F, 11.0F, -1.5F);
        rightLeg.addChild(rightThigh_r1);
        setRotationAngle(rightThigh_r1, -0.2618F, 0.0F, 0.0F);
        rightThigh_r1.cubeList.add(new ModelBox(rightThigh_r1, -6, -3, -4.0F, -11.0F, -4.0F, 5, 11, 5, 0.0F));

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(6.0F, 15.0F, 6.0F);
        legs.addChild(leftLeg);
        leftLeg.cubeList.add(new ModelBox(leftLeg, -8, -4, -3.0F, 9.0F, -4.5F, 6, 7, 6, 0.0F));
        leftLeg.cubeList.add(new ModelBox(leftLeg, -9, -4, -3.0F, 16.0F, -4.5F, 6, 5, 7, 0.0F));
        leftLeg.cubeList.add(new ModelBox(leftLeg, -11, -4, -3.0F, 21.0F, -4.5F, 6, 3, 9, 0.0F));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 0, 1.0F, 22.0F, -6.5F, 2, 2, 2, 0.0F));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 1, 1, -1.0F, 22.0F, -6.5F, 1, 2, 2, 0.0F));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 1, 1, -3.0F, 22.0F, -6.5F, 1, 2, 2, 0.0F));

        leftThigh_r1 = new ModelRenderer(this);
        leftThigh_r1.setRotationPoint(1.5F, 11.0F, -1.5F);
        leftLeg.addChild(leftThigh_r1);
        setRotationAngle(leftThigh_r1, -0.2618F, 0.0F, 0.0F);
        leftThigh_r1.cubeList.add(new ModelBox(leftThigh_r1, -6, -3, -4.0F, -11.0F, -4.0F, 5, 11, 5, 0.0F));

        arms = new ModelRenderer(this);
        arms.setRotationPoint(-2.0F, -15.0F, -4.5F);


        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-7.0F, 1.0F, 6.0F);
        arms.addChild(rightArm);
        setRotationAngle(rightArm, 0.0F, 0.0F, 0.1745F);
        rightArm.cubeList.add(new ModelBox(rightArm, -6, -3, -6.5F, -2.0F, -2.5F, 5, 13, 5, 0.0F));
        rightArm.cubeList.add(new ModelBox(rightArm, -2, -1, -5.5F, 11.0F, -1.5F, 3, 3, 3, 0.0F));

        rightHand = new ModelRenderer(this);
        rightHand.setRotationPoint(-4.0F, 11.5F, 0.0F);
        rightArm.addChild(rightHand);
        setRotationAngle(rightHand, -0.6109F, 0.0F, 0.0F);
        rightHand.cubeList.add(new ModelBox(rightHand, -10, -5, -3.5F, 1.5F, -3.5F, 7, 13, 7, 0.0F));

        rightFingers = new ModelRenderer(this);
        rightFingers.setRotationPoint(14.5F, -12.5F, 11.0F);
        rightHand.addChild(rightFingers);
        rightFingers.cubeList.add(new ModelBox(rightFingers, 0, 0, -13.0F, 27.0F, -14.5F, 2, 2, 2, 0.0F));
        rightFingers.cubeList.add(new ModelBox(rightFingers, 1, 0, -13.0F, 27.0F, -8.5F, 2, 4, 1, 0.0F));
        rightFingers.cubeList.add(new ModelBox(rightFingers, 1, 0, -18.0F, 27.0F, -8.5F, 2, 4, 1, 0.0F));
        rightFingers.cubeList.add(new ModelBox(rightFingers, 0, 0, -18.0F, 27.0F, -11.5F, 2, 4, 2, 0.0F));
        rightFingers.cubeList.add(new ModelBox(rightFingers, 0, 0, -18.0F, 27.0F, -14.5F, 2, 4, 2, 0.0F));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(11.0F, 1.0F, 6.0F);
        arms.addChild(leftArm);
        setRotationAngle(leftArm, 0.0F, 0.0F, -0.1745F);
        leftArm.cubeList.add(new ModelBox(leftArm, -6, -3, 0.5F, -2.0F, -2.5F, 5, 13, 5, 0.0F));
        leftArm.cubeList.add(new ModelBox(leftArm, -2, -1, 1.5F, 11.0F, -1.5F, 3, 3, 3, 0.0F));

        leftHand = new ModelRenderer(this);
        leftHand.setRotationPoint(3.0F, 12.5F, 0.0F);
        leftArm.addChild(leftHand);
        setRotationAngle(leftHand, -0.6109F, 0.0F, 0.0F);
        leftHand.cubeList.add(new ModelBox(leftHand, -10, -5, -3.5F, 0.5F, -3.5F, 7, 13, 7, 0.0F));

        leftFingers = new ModelRenderer(this);
        leftFingers.setRotationPoint(-10.5F, -13.5F, -14.0F);
        leftHand.addChild(leftFingers);
        leftFingers.cubeList.add(new ModelBox(leftFingers, 0, 0, 7.0F, 27.0F, 10.5F, 2, 2, 2, 0.0F));
        leftFingers.cubeList.add(new ModelBox(leftFingers, 1, 0, 12.0F, 27.0F, 16.5F, 2, 4, 1, 0.0F));
        leftFingers.cubeList.add(new ModelBox(leftFingers, 1, 0, 7.0F, 27.0F, 16.5F, 2, 4, 1, 0.0F));
        leftFingers.cubeList.add(new ModelBox(leftFingers, 0, 0, 12.0F, 27.0F, 13.5F, 2, 4, 2, 0.0F));
        leftFingers.cubeList.add(new ModelBox(leftFingers, 0, 0, 12.0F, 27.0F, 10.5F, 2, 4, 2, 0.0F));

        canopy = new ModelRenderer(this);
        canopy.setRotationPoint(-1.5F, 24.0F, -1.5F);
        canopy.cubeList.add(new ModelBox(canopy, -30, -21, -10.0F, -59.0F, -5.5F, 23, 6, 11, 0.0F));
        canopy.cubeList.add(new ModelBox(canopy, -18, -9, -4.0F, -65.0F, -5.5F, 11, 6, 11, 0.0F));
        canopy.cubeList.add(new ModelBox(canopy, -14, -9, -4.0F, -59.0F, -11.5F, 11, 6, 6, 0.0F));
        canopy.cubeList.add(new ModelBox(canopy, -14, -9, -4.0F, -59.0F, 5.5F, 11, 6, 6, 0.0F));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        head.render(f5);
        body.render(f5);
        legs.render(f5);
        arms.render(f5);
        canopy.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
