package com.dipo33.bewitched.entity.model;

import com.dipo33.bewitched.entity.EntityEnt;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * Poses {@link ModelEnt}'s bones for walking and attacking before delegating to its static-pose render().
 *
 * <p>Kept separate from {@link ModelEnt} so that file can be freely regenerated from Blockbench (aside from
 * widening its bone fields to {@code protected}) without losing this animation logic.</p>
 */
public class ModelEntAnimated extends ModelEnt {

    /** How far, in radians, the arms swing during the attack animation. */
    private static final float ATTACK_SWING_AMPLITUDE = 2.2F;

    /** How far, in radians, the legs swing per step while walking. */
    private static final float LEG_SWING_AMPLITUDE = 1.4F;

    /** How far, in radians, the arms swing per step while walking. */
    private static final float ARM_SWING_AMPLITUDE = 2.0F;

    /** How far, in radians, the arms sway on their own while idle. */
    private static final float IDLE_ARM_SWING_AMPLITUDE = 0.05F;

    /** How fast, in radians per tick of {@code ageInTicks}, the idle arm sway oscillates. */
    private static final float IDLE_ARM_SWING_SPEED = 0.05F;

    // Blockbench-authored resting pose for each animated bone, captured once so animation offsets can be added
    // on top of whatever pose the model currently has, instead of overwriting it with a hardcoded value.
    private final float restRightLegX = this.rightLeg.rotateAngleX;
    private final float restLeftLegX = this.leftLeg.rotateAngleX;
    private final float restRightArmX = this.rightArm.rotateAngleX;
    private final float restLeftArmX = this.leftArm.rotateAngleX;

    @Override
    public void render(
        final Entity entity,
        final float limbSwing,
        final float limbSwingAmount,
        final float ageInTicks,
        final float netHeadYaw,
        final float headPitch,
        final float scaleFactor
    ) {
        this.rightLeg.rotateAngleX = this.restRightLegX
            + MathHelper.cos(limbSwing * 0.6662F) * LEG_SWING_AMPLITUDE * limbSwingAmount;
        this.leftLeg.rotateAngleX = this.restLeftLegX
            + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * LEG_SWING_AMPLITUDE * limbSwingAmount;

        final float idleArmSway = MathHelper.sin(ageInTicks * IDLE_ARM_SWING_SPEED) * IDLE_ARM_SWING_AMPLITUDE;

        float rightArmSwing = idleArmSway
            + MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * ARM_SWING_AMPLITUDE * limbSwingAmount;
        float leftArmSwing = -idleArmSway
            + MathHelper.cos(limbSwing * 0.6662F) * ARM_SWING_AMPLITUDE * limbSwingAmount;

        if (entity instanceof EntityEnt) {
            final EntityEnt ent = (EntityEnt) entity;
            if (ent.attackTimer > 0) {
                final float progress = 1.0F - (ent.attackTimer / (float) EntityEnt.ARM_SWING_TICKS);
                final float attackSwing = MathHelper.sin(progress * (float) Math.PI) * ATTACK_SWING_AMPLITUDE;
                rightArmSwing = -attackSwing;
                leftArmSwing = -attackSwing;
            }
        }

        this.rightArm.rotateAngleX = this.restRightArmX + rightArmSwing;
        this.leftArm.rotateAngleX = this.restLeftArmX + leftArmSwing;

        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }
}
