package com.dipo33.bewitched.entity.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelMandrake extends ModelBase {

    private final ModelRenderer leg_r;
    private final ModelRenderer leg_l;
    private final ModelRenderer arm_r;
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leaf_1;
    private final ModelRenderer leaf_middle_r1;
    private final ModelRenderer leaf_bottom_r1;
    private final ModelRenderer leaf_tip_r1;
    private final ModelRenderer leaf_2;
    private final ModelRenderer leaf_middle_r2;
    private final ModelRenderer leaf_bottom_r2;
    private final ModelRenderer leaf_tip_r2;
    private final ModelRenderer leaf_3;
    private final ModelRenderer leaf_middle_r3;
    private final ModelRenderer leaf_bottom_r3;
    private final ModelRenderer leaf_tip_r3;
    private final ModelRenderer leaf_4;
    private final ModelRenderer leaf_middle_r4;
    private final ModelRenderer leaf_bottom_r4;
    private final ModelRenderer leaf_tip_r4;
    private final ModelRenderer arm_l;

    public ModelMandrake() {
        textureWidth = 32;
        textureHeight = 32;

        leg_r = new ModelRenderer(this);
        leg_r.setRotationPoint(-1.5F, 19.0F, -0.5F);
        leg_r.cubeList.add(new ModelBox(leg_r, 0, 16, -1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F));
        leg_r.cubeList.add(new ModelBox(leg_r, 16, 16, -0.5F, 2.0F, -0.5F, 1, 3, 1, 0.0F));

        leg_l = new ModelRenderer(this);
        leg_l.setRotationPoint(1.5F, 19.0F, -0.5F);
        leg_l.cubeList.add(new ModelBox(leg_l, 16, 16, -0.5F, 2.0F, -0.5F, 1, 3, 1, 0.0F));
        leg_l.cubeList.add(new ModelBox(leg_l, 0, 16, -1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F));

        arm_r = new ModelRenderer(this);
        arm_r.setRotationPoint(-0.5F, 14.0F, -0.25F);
        setRotationAngle(arm_r, 0.0F, 0.0F, 0.5672F);
        arm_r.cubeList.add(new ModelBox(arm_r, 16, 12, -0.5F, 3.0F, -0.5F, 1, 3, 1, 0.0F));
        arm_r.cubeList.add(new ModelBox(arm_r, 0, 11, -1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 17.0F, -0.5F);
        body.cubeList.add(new ModelBox(body, 0, 6, -1.5F, -3.0F, -1.0F, 3, 3, 2, 0.0F));
        body.cubeList.add(new ModelBox(body, 0, 0, -2.0F, 0.0F, -1.5F, 4, 3, 3, 0.0F));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 14.0F, -0.5F);
        head.cubeList.add(new ModelBox(head, 8, 12, -1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F));

        leaf_1 = new ModelRenderer(this);
        leaf_1.setRotationPoint(0.0F, -2.75F, 0.0F);
        head.addChild(leaf_1);
        setRotationAngle(leaf_1, 0.0F, -0.7854F, 0.0F);

        leaf_middle_r1 = new ModelRenderer(this);
        leaf_middle_r1.setRotationPoint(4.5F, -2.5F, 0.5F);
        leaf_1.addChild(leaf_middle_r1);
        setRotationAngle(leaf_middle_r1, 0.0F, 0.0F, -0.1745F);
        leaf_middle_r1.cubeList.add(new ModelBox(leaf_middle_r1, 10, 9, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_bottom_r1 = new ModelRenderer(this);
        leaf_bottom_r1.setRotationPoint(2.0F, -1.5F, 0.5F);
        leaf_1.addChild(leaf_bottom_r1);
        setRotationAngle(leaf_bottom_r1, 0.0F, 0.0F, -0.7854F);
        leaf_bottom_r1.cubeList.add(new ModelBox(leaf_bottom_r1, 10, 6, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_tip_r1 = new ModelRenderer(this);
        leaf_tip_r1.setRotationPoint(7.0F, -2.0F, 0.5F);
        leaf_1.addChild(leaf_tip_r1);
        setRotationAngle(leaf_tip_r1, 0.0026F, -0.0035F, 0.3927F);
        leaf_tip_r1.cubeList.add(new ModelBox(leaf_tip_r1, 14, 0, -2.0F, 0.0F, -2.0F, 2, 0, 3, 0.0F));

        leaf_2 = new ModelRenderer(this);
        leaf_2.setRotationPoint(0.0F, -2.75F, 0.0F);
        head.addChild(leaf_2);
        setRotationAngle(leaf_2, 0.0F, -2.3562F, 0.0F);

        leaf_middle_r2 = new ModelRenderer(this);
        leaf_middle_r2.setRotationPoint(4.5F, -2.5F, 0.5F);
        leaf_2.addChild(leaf_middle_r2);
        setRotationAngle(leaf_middle_r2, 0.0F, 0.0F, -0.1745F);
        leaf_middle_r2.cubeList.add(new ModelBox(leaf_middle_r2, 10, 9, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_bottom_r2 = new ModelRenderer(this);
        leaf_bottom_r2.setRotationPoint(2.0F, -1.5F, 0.5F);
        leaf_2.addChild(leaf_bottom_r2);
        setRotationAngle(leaf_bottom_r2, 0.0F, 0.0F, -0.7854F);
        leaf_bottom_r2.cubeList.add(new ModelBox(leaf_bottom_r2, 10, 6, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_tip_r2 = new ModelRenderer(this);
        leaf_tip_r2.setRotationPoint(7.0F, -2.0F, 0.5F);
        leaf_2.addChild(leaf_tip_r2);
        setRotationAngle(leaf_tip_r2, 0.0026F, -0.0035F, 0.3927F);
        leaf_tip_r2.cubeList.add(new ModelBox(leaf_tip_r2, 14, 0, -2.0F, 0.0F, -2.0F, 2, 0, 3, 0.0F));

        leaf_3 = new ModelRenderer(this);
        leaf_3.setRotationPoint(0.0F, -2.75F, 0.0F);
        head.addChild(leaf_3);
        setRotationAngle(leaf_3, 0.0F, 2.3562F, 0.0F);

        leaf_middle_r3 = new ModelRenderer(this);
        leaf_middle_r3.setRotationPoint(4.5F, -2.5F, 0.5F);
        leaf_3.addChild(leaf_middle_r3);
        setRotationAngle(leaf_middle_r3, 0.0F, 0.0F, -0.1745F);
        leaf_middle_r3.cubeList.add(new ModelBox(leaf_middle_r3, 10, 9, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_bottom_r3 = new ModelRenderer(this);
        leaf_bottom_r3.setRotationPoint(2.0F, -1.5F, 0.5F);
        leaf_3.addChild(leaf_bottom_r3);
        setRotationAngle(leaf_bottom_r3, 0.0F, 0.0F, -0.7854F);
        leaf_bottom_r3.cubeList.add(new ModelBox(leaf_bottom_r3, 10, 6, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_tip_r3 = new ModelRenderer(this);
        leaf_tip_r3.setRotationPoint(7.0F, -2.0F, 0.5F);
        leaf_3.addChild(leaf_tip_r3);
        setRotationAngle(leaf_tip_r3, 0.0026F, -0.0035F, 0.3927F);
        leaf_tip_r3.cubeList.add(new ModelBox(leaf_tip_r3, 14, 0, -2.0F, 0.0F, -2.0F, 2, 0, 3, 0.0F));

        leaf_4 = new ModelRenderer(this);
        leaf_4.setRotationPoint(0.0F, -2.75F, 0.0F);
        head.addChild(leaf_4);
        setRotationAngle(leaf_4, 0.0F, 0.7854F, 0.0F);

        leaf_middle_r4 = new ModelRenderer(this);
        leaf_middle_r4.setRotationPoint(4.5F, -2.5F, 0.5F);
        leaf_4.addChild(leaf_middle_r4);
        setRotationAngle(leaf_middle_r4, 0.0F, 0.0F, -0.1745F);
        leaf_middle_r4.cubeList.add(new ModelBox(leaf_middle_r4, 10, 9, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_bottom_r4 = new ModelRenderer(this);
        leaf_bottom_r4.setRotationPoint(2.0F, -1.5F, 0.5F);
        leaf_4.addChild(leaf_bottom_r4);
        setRotationAngle(leaf_bottom_r4, 0.0F, 0.0F, -0.7854F);
        leaf_bottom_r4.cubeList.add(new ModelBox(leaf_bottom_r4, 10, 6, -2.0F, 0.0F, -2.0F, 3, 0, 3, 0.0F));

        leaf_tip_r4 = new ModelRenderer(this);
        leaf_tip_r4.setRotationPoint(7.0F, -2.0F, 0.5F);
        leaf_4.addChild(leaf_tip_r4);
        setRotationAngle(leaf_tip_r4, 0.0026F, -0.0035F, 0.3927F);
        leaf_tip_r4.cubeList.add(new ModelBox(leaf_tip_r4, 14, 0, -2.0F, 0.0F, -2.0F, 2, 0, 3, 0.0F));

        arm_l = new ModelRenderer(this);
        arm_l.setRotationPoint(0.5F, 14.0F, -0.25F);
        setRotationAngle(arm_l, 0.0F, 0.0F, -0.5672F);
        arm_l.cubeList.add(new ModelBox(arm_l, 16, 12, -0.5F, 3.0F, -0.5F, 1, 3, 1, 0.0F));
        arm_l.cubeList.add(new ModelBox(arm_l, 0, 11, -1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        leg_r.render(scaleFactor);
        leg_l.render(scaleFactor);
        arm_r.render(scaleFactor);
        body.render(scaleFactor);
        head.render(scaleFactor);
        arm_l.render(scaleFactor);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor,
                                  Entity entity) {
        head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        leg_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        arm_r.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        arm_l.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
