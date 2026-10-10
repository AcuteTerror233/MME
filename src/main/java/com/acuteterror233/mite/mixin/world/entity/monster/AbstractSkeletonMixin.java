package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.entity.ai.SkeletonTargetMotionTracker;
import com.acuteterror233.mite.world.entity.ai.goal.MonsterFindPlayerGoal;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code AbstractSkeleton} — Extends skeleton behavior: dimension/Y-based
 * weapon population and reduced combat attributes.
 *
 * <p>Mechanism:</p>
 * <ul>
 *   <li>{@code populateDefaultEquipmentSlots} overwrite: after the vanilla pass,
 *       skeletons in the underground dimension or the overworld may receive a tiered
 *       weapon (bow or melee) chosen by Y-level (see the {@code set*Weapon} helpers).</li>
 *   <li>{@code createAttributes} overwrite: movement speed 0.33, max health 6.</li>
 * </ul>
 *
 * <p>Weapon drop rates: 0.25 below the dimension's Y threshold (underground 125,
 * overworld 0), 0.05 at or above it, doubled on HARD difficulty.</p>
 */
@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin extends Monster implements RangedAttackMob {
    @Unique
    private static final float LOW_Y_THRESHOLD_UNDERGROUND = 125.0F;
    @Unique
    private static final float LOW_Y_THRESHOLD_OVERWORLD = 0.0F;
    @Unique
    private static final float LOW_Y_DROP_RATE = 0.75F;
    @Unique
    private static final float HIGH_Y_DROP_RATE = 0.5F;
    @Unique
    private static final float HARD_DIFFICULTY_MULTIPLIER = 2.0F;
    /** Skeleton arrow launch speed in blocks per tick; raised from the vanilla 1.6 to shorten flight time. */
    @Unique
    private static final double ARROW_SPEED = 2.4D;
    /** Same launch speed as {@link #ARROW_SPEED} for the float parameter of {@code shoot}. */
    @Unique
    private static final float ARROW_SPEED_F = 2.4F;
    /** Arrow gravity per tick squared ({@code AbstractArrow} default), used for the drop compensation. */
    @Unique
    private static final double ARROW_GRAVITY = 0.05D;
    /** Flight-time solve iterations; the estimate converges after 2-3 rounds. */
    @Unique
    private static final int LEAD_ITERATIONS = 3;
    /** Hard cap per axis on the predicted lead offset, so distant strafing shots stay sane. */
    @Unique
    private static final double MAX_LEAD_OFFSET = 6.0D;
    /** Targets slower than this are treated as stationary and keep the vanilla aim. */
    @Unique
    private static final double MIN_TARGET_SPEED = 0.08D;

    protected AbstractSkeletonMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author AcuteTerror233
     * @reason Add held weapons
     */
    @Overwrite
    public void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        Level level = this.level();
        // Drop rate: 0.25 below the Y threshold, 0.05 above; doubled on HARD difficulty
        if (level.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_UNDERGROUND ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate) {
                int weaponIndex = randomSource.nextInt(6);
                if (getY() < LOW_Y_THRESHOLD_UNDERGROUND) {
                    setUndergroundLowYWeapon(weaponIndex);
                } else {
                    setUndergroundHighYWeapon(weaponIndex);
                }
            }
        } else if (level.dimension() == Level.OVERWORLD) {
            float populateRate = (getY() < LOW_Y_THRESHOLD_OVERWORLD ? LOW_Y_DROP_RATE : HIGH_Y_DROP_RATE) * (level.getDifficulty() == Difficulty.HARD ? HARD_DIFFICULTY_MULTIPLIER : 1.0F);
            if (randomSource.nextFloat() < populateRate) {
                int weaponIndex = randomSource.nextInt(4);
                if (getY() < LOW_Y_THRESHOLD_OVERWORLD) {
                    setOverworldLowYWeapon(weaponIndex);
                } else {
                    setOverworldHighYWeapon(weaponIndex);
                }
            }
        }
    }

    // Underground low-Y weapon assignment
    @Unique
    private void setUndergroundLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.ANCIENT_METAL_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.IRON_BATTLE_AXE)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
        }
    }

    // Underground high-Y weapon assignment
    @Unique
    private void setUndergroundHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_BATTLE_AXE)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 4: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 5: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
        }
    }

    // Overworld low-Y weapon assignment
    @Unique
    private void setOverworldLowYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.COPPER_SWORD)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.COPPER_BATTLE_AXE)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.SILVER_SWORD)); break;
        }
    }

    // Overworld high-Y weapon assignment
    @Unique
    private void setOverworldHighYWeapon(int index) {
        switch (index) {
            case 0: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); break;
            case 1: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CLUB)); break;
            case 2: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.WOODEN_CUDGEL)); break;
            case 3: this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.FLINT_AXE)); break;
        }
    }

    /**
     * Rebuilt monster attributes: movement speed 0.33 and max health 6.
     *
     * @author AcuteTerror233
     * @reason Modify attributes
     */
    @Overwrite
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MOVEMENT_SPEED, 0.33);
        builder.add(Attributes.MAX_HEALTH, 6);
        return builder;
    }

    /**
     * Replaces the vanilla sight-gated player-targeting goal (the only
     * {@code NearestAttackableTargetGoal} at priority 2) with {@link MonsterFindPlayerGoal}
     * so skeletons acquire and track players without line of sight. Applies to every
     * skeleton variant (stray, bogged, wither skeleton) since none of them override
     * {@code registerGoals}.
     */
    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void mme$noSightPlayerTarget(CallbackInfo ci) {
        this.targetSelector.getAvailableGoals().removeIf(wrapped ->
                wrapped.getPriority() == 2 && wrapped.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(2, new MonsterFindPlayerGoal((AbstractSkeleton) (Object) this));
    }

    @Shadow
    protected AbstractArrow getArrow(final ItemStack projectile, final float power, final @Nullable ItemStack firingWeapon) {
        return null;
    }

    /**
     * Ranged attack with target leading ("predictive shooting"). The vanilla projectile
     * selection, spawn, and sound are kept, but the launch speed is raised to
     * {@value #ARROW_SPEED_F} blocks/tick (vanilla 1.6) and the vanilla fixed
     * {@code 0.2 * horizontalDistance} arc is replaced by a physical drop compensation
     * ({@code 0.5 * g * t^2}), since that heuristic is calibrated for the slower vanilla arrow
     * and shoots above the target's head at the higher speed. The aim point is iteratively
     * projected onto the target's predicted position using its velocity estimate.
     *
     * <p>Target velocity comes from {@link SkeletonTargetMotionTracker}, which samples the
     * target's position once per tick ({@code MobMixin} feeds it from {@code aiStep}). Both
     * {@code getDeltaMovement()} and the {@code xo/yo/zo} diff are always zero for server-side
     * players because movement packets are applied before entity ticking. The lead is
     * difficulty-scaled (easy 0.5 / normal 0.75 / hard 1.0), capped per axis, and skipped for
     * near-stationary targets, which fall through to the exact vanilla aim.</p>
     */
    @Override
    public void performRangedAttack(final LivingEntity target, final float power) {
        ItemStack bowItem = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
        ItemStack projectile = this.getProjectile(bowItem);
        AbstractArrow arrow = this.getArrow(projectile, power, bowItem);

        Vec3 targetVelocity = SkeletonTargetMotionTracker.velocity(target, this.level().getGameTime());
        double velX = targetVelocity.x;
        double velY = targetVelocity.y;
        double velZ = targetVelocity.z;

        float leadScale = switch (this.level().getDifficulty()) {
            case EASY -> 0.5F;
            case NORMAL -> 0.75F;
            case HARD -> 1.0F;
            default -> 0.0F;
        };
        double targetHorizontalSpeed = Math.sqrt(velX * velX + velZ * velZ);
        boolean usePrediction = leadScale > 0.0F && targetHorizontalSpeed > MIN_TARGET_SPEED;

        double aimX = target.getX();
        double aimY = target.getY(0.3333333333333333);
        double aimZ = target.getZ();
        if (usePrediction) {
            double arrowY = arrow.getY();
            for (int i = 0; i < LEAD_ITERATIONS; i++) {
                double dx = aimX - this.getX();
                double dy = aimY - arrowY;
                double dz = aimZ - this.getZ();
                double flightTicks = Math.sqrt(dx * dx + dy * dy + dz * dz) / ARROW_SPEED;
                double leadX = Mth.clamp(velX * flightTicks * leadScale, -MAX_LEAD_OFFSET, MAX_LEAD_OFFSET);
                double leadY = Mth.clamp(velY * flightTicks * leadScale, -MAX_LEAD_OFFSET, MAX_LEAD_OFFSET);
                double leadZ = Mth.clamp(velZ * flightTicks * leadScale, -MAX_LEAD_OFFSET, MAX_LEAD_OFFSET);
                aimX = target.getX() + leadX;
                aimY = target.getY(0.3333333333333333) + leadY;
                aimZ = target.getZ() + leadZ;
            }
        }

        double xd = aimX - this.getX();
        double yd = aimY - arrow.getY();
        double zd = aimZ - this.getZ();
        double flightTicks = Math.sqrt(xd * xd + yd * yd + zd * zd) / ARROW_SPEED;
        // Physical drop compensation (0.5 * g * t^2) instead of the vanilla fixed
        // 0.2 * horizontalDistance heuristic: that factor is calibrated for speed 1.6 and
        // massively over-arcs once the launch speed is raised (shots fly above the head).
        double arcCompensation = 0.5D * ARROW_GRAVITY * flightTicks * flightTicks;
        if (this.level() instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileUsingShoot(arrow, serverLevel, projectile, xd, yd + arcCompensation, zd, ARROW_SPEED_F, this.rangedAttackUncertainty(serverLevel));
        }

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }
}
