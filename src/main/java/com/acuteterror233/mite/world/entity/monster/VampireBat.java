package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VampireBat extends FlyingMob implements Enemy {
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(VampireBat.class, EntityDataSerializers.BYTE);
    private static final int FLAG_RESTING = 1;
    private static final TargetingConditions BAT_RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0);
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState restAnimationState = new AnimationState();
    @Nullable
    private BlockPos targetPosition;
    AttackPhase attackPhase = AttackPhase.LOITERED;

    public VampireBat(EntityType<? extends VampireBat> entityType, Level level) {
        super(entityType, level);
        if (!level.isClientSide) {
            this.setResting(true);
        }
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        this.goalSelector.addGoal(1, new VampireBatAttackStrategyGoal());
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.95f;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isResting()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPosRaw(this.getX(), (double)Mth.floor(this.getY()) + 1.0 - (double)this.getBbHeight(), this.getZ());
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
        this.setupAnimationStates();
    }

    private void setAttackPhase(VampireBat.AttackPhase attackPhase){
        this.attackPhase = attackPhase;
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        if (this.attackPhase == AttackPhase.LOITERED){
            BlockPos blockPos = this.blockPosition();
            BlockPos blockPos2 = blockPos.above();
            if (this.isResting()) {
                boolean bl = this.isSilent();
                if (serverLevel.getBlockState(blockPos2).isRedstoneConductor(serverLevel, blockPos)) {
                    if (this.random.nextInt(200) == 0) {
                        this.yHeadRot = this.random.nextInt(360);
                    }
                    if (serverLevel.getNearestPlayer(BAT_RESTING_TARGETING, this) != null) {
                        this.setResting(false);
                        if (!bl) {
                            serverLevel.levelEvent(null, 1025, blockPos, 0);
                        }
                    }
                } else {
                    this.setResting(false);
                    if (!bl) {
                        serverLevel.levelEvent(null, 1025, blockPos, 0);
                    }
                }
            } else {
                if (!(this.targetPosition == null || serverLevel.isEmptyBlock(this.targetPosition) && this.targetPosition.getY() > serverLevel.getMinY())) {
                    this.targetPosition = null;
                }
                if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0)) {
                    this.targetPosition = BlockPos.containing(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
                }
                double d = this.targetPosition.getX() + 0.5 - this.getX();
                double e = this.targetPosition.getY() + 0.1 - this.getY();
                double f = this.targetPosition.getZ() + 0.5 - this.getZ();
                Vec3 vec3 = this.getDeltaMovement();
                Vec3 vec32 = vec3.add((Math.signum(d) * 0.5 - vec3.x) * (double)0.1f, (Math.signum(e) * (double)0.7f - vec3.y) * (double)0.1f, (Math.signum(f) * 0.5 - vec3.z) * (double)0.1f);
                this.setDeltaMovement(vec32);
                float g = (float)(Mth.atan2(vec32.z, vec32.x) * 57.2957763671875) - 90.0f;
                float h = Mth.wrapDegrees(g - this.getYRot());
                this.zza = 0.5f;
                this.setYRot(this.getYRot() + h);
                if (this.random.nextInt(100) == 0 && serverLevel.getBlockState(blockPos2).isRedstoneConductor(serverLevel, blockPos2)) {
                    this.setResting(true);
                }
            }
        }
    }
    
    private void setupAnimationStates() {
        if (this.isResting()) {
            this.flyAnimationState.stop();
            this.restAnimationState.startIfStopped(this.tickCount);
        } else {
            this.restAnimationState.stop();
            this.flyAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_FLAGS, (byte) 0);
    }

    public boolean isResting() {
        return (this.entityData.get(DATA_ID_FLAGS) & FLAG_RESTING) != 0;
    }

    public void setResting(boolean resting) {
        byte flags = this.entityData.get(DATA_ID_FLAGS);
        if (resting) {
            this.entityData.set(DATA_ID_FLAGS, (byte) (flags | FLAG_RESTING));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte) (flags & ~FLAG_RESTING));
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return this.isResting() && this.random.nextInt(4) != 0 ? null : SoundEvents.BAT_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BAT_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hurt = super.doHurtTarget(level, target);
        if (hurt) {
            this.heal(2.0F);
        }
        return hurt;
    }

    @Override
    public float getSoundVolume() {
        return 0.1F;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public boolean isFlapping() {
        return !this.isResting() && (float)this.tickCount % 10.0f == 0.0f;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_ID_FLAGS, compoundTag.getByteOr("BatFlags", (byte)0));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("BatFlags", this.entityData.get(DATA_ID_FLAGS));
    }

    boolean canAttack(ServerLevel serverLevel, LivingEntity livingEntity) {
        return TargetingConditions.DEFAULT.test(serverLevel, this, livingEntity);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 6.0);
    }

    public static boolean checkVampireBatSpawnRules(
            EntityType<? extends VampireBat> entityType,
            ServerLevelAccessor serverLevelAccessor,
            EntitySpawnReason entitySpawnReason,
            BlockPos blockPos,
            RandomSource randomSource
    ) {
        if (blockPos.getY() >= serverLevelAccessor.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, blockPos).getY()) {
            return false;
        }
        int i = serverLevelAccessor.getMaxLocalRawBrightness(blockPos);
        int j = 4;
        if (randomSource.nextBoolean()) {
            return false;
        }
        if (i > randomSource.nextInt(j)) {
            return false;
        }
        if (!serverLevelAccessor.getBlockState(blockPos.below()).is(BlockTags.BATS_SPAWNABLE_ON)) {
            return false;
        }
        return Mob.checkMobSpawnRules(entityType, serverLevelAccessor, entitySpawnReason, blockPos, randomSource);
    }

    enum AttackPhase {
        LOITERED,
        SWOOP
    }


    class VampireBatAttackStrategyGoal extends Goal {
        private int ticksUntilNextAttack;
        VampireBatAttackStrategyGoal() {
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = VampireBat.this.getTarget();
            if (livingEntity != null) {
                return VampireBat.this.canAttack(VampireBat.VampireBatAttackStrategyGoal.getServerLevel(VampireBat.this.level()), livingEntity);
            }
            return false;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            VampireBat.this.setAttackPhase(AttackPhase.SWOOP);
            this.ticksUntilNextAttack = 0;
        }

        @Override
        public void stop() {
            VampireBat.this.setAttackPhase(AttackPhase.LOITERED);
        }

        @Override
        public void tick() {
            LivingEntity target = VampireBat.this.getTarget();
            RandomSource random = VampireBat.this.getRandom();
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                VampireBat.this.doHurtTarget(MeleeAttackGoal.getServerLevel(VampireBat.this), target);
            }
            double d = target.getX() - VampireBat.this.getX();
            double e = target.getY() + 1 - VampireBat.this.getY();
            double f = target.getZ() - VampireBat.this.getZ();
            if (this.ticksUntilNextAttack <= 10){
                d = d + random.nextDouble();
                f = f + random.nextDouble();
            }else {
                d = d - random.nextDouble();
                f = f - random.nextDouble();
            }
            Vec3 vec3 = VampireBat.this.getDeltaMovement();
            Vec3 vec32 = vec3.add((Math.signum(d) * 0.5 - vec3.x) * (double)0.1f, (Math.signum(e) * (double)0.7f - vec3.y) * (double)0.1f, (Math.signum(f) * 0.5 - vec3.z) * (double)0.1f);
            VampireBat.this.setDeltaMovement(vec32);
            float g = (float)(Mth.atan2(vec32.z, vec32.x) * 57.2957763671875) - 90.0f;
            float h = Mth.wrapDegrees(g - VampireBat.this.getYRot());
            VampireBat.this.zza = 0.5f;
            VampireBat.this.setYRot(VampireBat.this.getYRot() + h);
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        protected boolean canPerformAttack(LivingEntity livingEntity) {
            return this.ticksUntilNextAttack <= 0 && VampireBat.this.isWithinMeleeAttackRange(livingEntity) && VampireBat.this.getSensing().hasLineOfSight(livingEntity);
        }
    }
}