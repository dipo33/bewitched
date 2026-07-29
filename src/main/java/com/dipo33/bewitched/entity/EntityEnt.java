package com.dipo33.bewitched.entity;

import java.util.Random;
import java.util.Set;

import com.dipo33.bewitched.init.BewitchedBlocks;
import com.dipo33.bewitched.init.BewitchedItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class EntityEnt extends EntityMob {

    /** DataWatcher slot carrying the screaming flag so it renders correctly on the client. */
    private static final int DW_SCREAMING = 20;

    /** No single hit may deal more than this much damage, whatever the source. */
    private static final float MAX_DAMAGE_PER_HIT = 15.0F;

    /** Axes deal triple damage (applied after the universal cap). */
    private static final float AXE_DAMAGE_MULTIPLIER = 3.0F;

    /** Length, in ticks, of the melee arm-swing animation; kept in sync on both sides via entity state 4. */
    public static final int ARM_SWING_TICKS = 10;

    /** Odds, per tick, of applying bonemeal to the block beneath the Ent (~once every 15 seconds). */
    private static final int FERTILIZER_CHANCE = 300;

    /** Counts down the melee arm-swing animation; kept in sync on both sides via entity state 4. */
    public int attackTimer;

    public EntityEnt(final World world) {
        super(world);
        this.setSize(1.4F, 3.5F);
        this.experienceValue = 25;

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.75D, false));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.75D, 32.0F));
        this.tasks.addTask(3, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));

        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(DW_SCREAMING, Byte.valueOf((byte) 0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(DW_SCREAMING) != 0;
    }

    private void setScreaming(final boolean screaming) {
        this.dataWatcher.updateObject(DW_SCREAMING, Byte.valueOf((byte) (screaming ? 1 : 0)));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if (!this.worldObj.isRemote) {
            this.setScreaming(this.getAttackTarget() != null);

            if (this.rand.nextInt(FERTILIZER_CHANCE) == 0) {
                this.applyFertilizer();
            }
        }
    }

    /**
     * Silently apply bonemeal to the block directly beneath the Ent via a FakePlayer, accelerating any plant growth
     * that bonemeal affects.
     */
    private void applyFertilizer() {
        if (!(this.worldObj instanceof WorldServer)) {
            return;
        }

        final int x = MathHelper.floor_double(this.posX);
        final int y = MathHelper.floor_double(this.boundingBox.minY) - 1;
        final int z = MathHelper.floor_double(this.posZ);

        final FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer) this.worldObj);
        ItemDye.applyBonemeal(new ItemStack(net.minecraft.init.Items.dye, 1, 15), this.worldObj, x, y, z, fakePlayer);
    }

    @Override
    public boolean attackEntityAsMob(final Entity target) {
        final float damage = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        final boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);

        // The Ent strikes on every tick it's in range; the swing, sound, and launch only fire when a hit actually
        // lands, so the target's own post-hit invulnerability paces them to one every 10 ticks.
        if (hit) {
            target.motionY += 0.4D;
            this.attackTimer = ARM_SWING_TICKS;
            this.worldObj.setEntityState(this, (byte) 4);
            this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }

        return hit;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte state) {
        if (state == 4) {
            this.attackTimer = ARM_SWING_TICKS;
            this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        } else {
            super.handleHealthUpdate(state);
        }
    }

    @Override
    public boolean attackEntityFrom(final DamageSource source, float amount) {
        // Universal per-hit cap applied to the attacker's already fully-computed damage.
        amount = Math.min(amount, MAX_DAMAGE_PER_HIT);

        // Axe multiplier is applied only to melee hits, after the cap.
        final Entity direct = source.getSourceOfDamage();
        if (direct instanceof net.minecraft.entity.EntityLivingBase) {
            final ItemStack held = ((net.minecraft.entity.EntityLivingBase) direct).getHeldItem();
            if (held != null && isAxe(held)) {
                amount *= AXE_DAMAGE_MULTIPLIER;
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    private static boolean isAxe(final ItemStack stack) {
        if (stack.getItem() instanceof ItemAxe) {
            return true;
        }
        final Set<String> toolClasses = stack.getItem().getToolClasses(stack);
        return toolClasses != null && toolClasses.contains("axe");
    }

    /**
     * The Ent's air supply is never decremented, so it can stay submerged indefinitely without drowning.
     */
    @Override
    protected int decreaseAirSupply(final int air) {
        return air;
    }

    /**
     * Always renders at full brightness, so the Ent is fully visible even in complete darkness.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float partialTicks) {
        return 0xF000F0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getBrightness(final float partialTicks) {
        return 1.0F;
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return "mob.horse.zombie.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.horse.zombie.death";
    }

    @Override
    protected void func_145780_a(final int x, final int y, final int z, final Block block) {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    @Override
    protected void dropFewItems(final boolean recentlyHit, final int looting) {
        this.dropItem(BewitchedItems.ENT_TWIG.get(), 1);
        final int saplingMeta = this.rand.nextInt(3);
        this.entityDropItem(new ItemStack(BewitchedBlocks.SAPLING.get(), 1, saplingMeta), 0.0F);
    }

    /**
     * Rolls the Ent spawn chance for a Bewitched log that has just been broken, and — if it succeeds and a valid
     * spawn location is found — spawns one Ent nearby along with the associated feedback effects. Server-side only.
     *
     * @param world
     *     the world the log was broken in
     * @param x
     *     broken log X
     * @param y
     *     broken log Y
     * @param z
     *     broken log Z
     */
    public static void trySpawnFromLogBreak(final World world, final int x, final int y, final int z) {
        if (world.isRemote) {
            return;
        }

        final Random rand = world.rand;
        final Block log = BewitchedBlocks.LOG.get();

        int adjacent = 0;
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == log) {
                adjacent++;
            }
        }

        // 1% base + 1% per adjacent log, capped at 5%.
        final int chancePercent = Math.min(1 + adjacent, 5);
        if (rand.nextInt(100) >= chancePercent) {
            return;
        }

        // Random offset of magnitude 8-16 in X and Z.
        final int targetX = x + (8 + rand.nextInt(9)) * (rand.nextBoolean() ? 1 : -1);
        final int targetZ = z + (8 + rand.nextInt(9)) * (rand.nextBoolean() ? 1 : -1);

        // Find the natural ground surface: highest solid-topped block no more than 8 above the log, with no lower
        // limit, so a nearby valley, ravine, or cave can pull the spawn point well below the tree.
        int groundY = -1;
        for (int cy = y + 8; cy > 0; cy--) {
            if (world.isSideSolid(targetX, cy, targetZ, ForgeDirection.UP) && world.isAirBlock(targetX, cy + 1, targetZ)) {
                groundY = cy;
                break;
            }
        }
        if (groundY < 0) {
            return;
        }

        // At least 3 blocks of vertical clearance above the ground.
        final int spawnY = groundY + 1;
        if (!world.isAirBlock(targetX, spawnY, targetZ)
            || !world.isAirBlock(targetX, spawnY + 1, targetZ)
            || !world.isAirBlock(targetX, spawnY + 2, targetZ)) {
            return;
        }

        // Feedback at the broken log.
        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "note.harp", 1.0F, 1.0F);
        if (world instanceof WorldServer) {
            ((WorldServer) world).func_147487_a("instantSpell", x + 0.5D, y + 0.5D, z + 0.5D, 30, 0.5D, 0.5D, 0.5D, 0.0D);
        }

        final EntityEnt ent = new EntityEnt(world);
        ent.setLocationAndAngles(targetX + 0.5D, spawnY, targetZ + 0.5D, rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntityInWorld(ent);

        // Feedback at the Ent's spawn point.
        world.playSoundEffect(targetX + 0.5D, spawnY + 1.0D, targetZ + 0.5D, "mob.horse.skeleton.death", 1.0F, 1.0F);
        if (world instanceof WorldServer) {
            ((WorldServer) world).func_147487_a("largesmoke", targetX + 0.5D, spawnY + 1.5D, targetZ + 0.5D, 30, 0.5D, 0.5D, 0.5D, 0.0D);
        }
    }
}
