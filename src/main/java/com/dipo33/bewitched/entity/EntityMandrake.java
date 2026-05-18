package com.dipo33.bewitched.entity;

import com.dipo33.bewitched.init.BewitchedItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityMandrake extends EntityMob {

    /**
     * Nausea II for 15 seconds
     */
    private static final int NAUSEA_DURATION_TICKS = 15 * 20;
    private static final int NAUSEA_AMPLIFIER = 1;

    public EntityMandrake(final World world) {
        super(world);
        this.setSize(0.4F, 0.6F);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0F, false));
        this.tasks.addTask(2, new EntityAIWander(this, 0.8));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.experienceValue = 0;
    }

    /**
     * Harmless to players: no melee or collision damage, only nausea
     */
    @Override
    public boolean attackEntityAsMob(final Entity target) {
        if (!this.worldObj.isRemote && target instanceof EntityPlayer player) {
            ItemStack helmet = player.inventory.armorInventory[3];
            if (helmet != null && helmet.getItem() == BewitchedItems.EARMUFFS.get()) {
                return true;
            }

            PotionEffect nausea = player.getActivePotionEffect(Potion.confusion);
            if (nausea != null && nausea.getAmplifier() >= NAUSEA_AMPLIFIER && nausea.getDuration() > 0) {
                return true;
            }

            player.addPotionEffect(new PotionEffect(Potion.confusion.id, NAUSEA_DURATION_TICKS, NAUSEA_AMPLIFIER));
        }

        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.65D);
    }

    @Override
    protected String getLivingSound() {
        return "mob.ghast.scream";
    }

    @Override
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }

    @Override
    protected String getDeathSound() {
        return "mob.ghast.death";
    }

    @Override
    protected Item getDropItem() {
        return BewitchedItems.MANDRAKE_SEEDS.get();
    }

    @Override
    protected void dropFewItems(final boolean recentlyHit, final int looting) {
        super.dropFewItems(recentlyHit, looting);
        this.dropItem(BewitchedItems.MANDRAKE_ROOT.get(), 1);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }
}
