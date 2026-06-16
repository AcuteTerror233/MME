package com.acuteterror233.mite.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 相位蜘蛛实体，继承蜘蛛行为。
 * 可进行短距离传送。
 */
public class PhaseSpider extends Spider {
    private static final EntityDataAccessor<Integer> DODGE_CHARGES = SynchedEntityData.defineId(PhaseSpider.class, EntityDataSerializers.INT);
    private static final int MAX_DODGE_CHARGES = 5;

    public PhaseSpider(EntityType<? extends PhaseSpider> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Armadillo.class, 6.0F, 1.0, 1.2, livingEntity -> !((Armadillo)livingEntity).isScared()));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new PhaseSpiderAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DODGE_CHARGES, MAX_DODGE_CHARGES);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData
    ) {
        return spawnGroupData;
    }

    @Override
    public @NotNull Vec3 getVehicleAttachmentPoint(Entity entity) {
        return entity.getBbWidth() <= this.getBbWidth() ? new Vec3(0.0, 0.21875 * this.getScale(), 0.0) : super.getVehicleAttachmentPoint(entity);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
            return !randomTeleport();
        }

        if (this.getDodgeCharges() > 0) {
            this.addDodgeCharges(-1);
            return !this.randomTeleport();
        }

        return super.hurtServer(level, damageSource, amount);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("DodgeCharges", getDodgeCharges());
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        this.addDodgeCharges(tag.getInt("DodgeCharges").orElse(MAX_DODGE_CHARGES));
    }

    public int getDodgeCharges() {
        return this.entityData.get(DODGE_CHARGES);
    }

    private void addDodgeCharges(int charges) {
        this.entityData.set(DODGE_CHARGES, this.getDodgeCharges() + charges);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Spider.createAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    protected boolean randomTeleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double x = this.getX() + (this.random.nextDouble() - 0.5) * 16.0;
            double y = this.getY() + (this.random.nextInt(10) - 5);
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * 16.0;
            return this.teleport(x, y, z);
        } else {
            return false;
        }
    }

    protected boolean teleportTowards(Entity entity) {
        Vec3 vec3 = new Vec3(this.getX() - entity.getX(), this.getY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3 = vec3.normalize();
        double x = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 5.0;
        double y = this.getY() + (this.random.nextInt(16) - 8) - vec3.y * 5.0;
        double z = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 5.0;
        return this.teleport(x, y, z);
    }

    private boolean teleport(double x, double y, double z) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);

        while (mutableBlockPos.getY() > this.level().getMinY() && !this.level().getBlockState(mutableBlockPos).blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        BlockState blockState = this.level().getBlockState(mutableBlockPos);
        if (blockState.blocksMotion()) {
            Vec3 vec3 = this.position();
            boolean bl3 = this.randomTeleport(x, y, z, true);
            if (bl3) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
                if (!this.isSilent()) {
                    this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }
            return bl3;
        } else {
            return false;
        }
    }

    static class PhaseSpiderAttackGoal extends MeleeAttackGoal {
        private static final int CHARGE_REGEN_TICKS = 100;
        private int chargeRegenTimer;
        private int teleportTimer;
        private int stopTimer;
        PhaseSpider phaseSpider;
        public PhaseSpiderAttackGoal(PhaseSpider spider) {
            super(spider, 1.0, true);
            this.phaseSpider = spider;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }

        @Override
        public void tick() {
            super.tick();
            LivingEntity target = this.mob.getTarget();
            if (++this.chargeRegenTimer % 5 == 0 && this.stopTimer <= 0){
                this.phaseSpider.randomTeleport();
            }else {
                this.stopTimer--;
            }
            if (this.chargeRegenTimer >= CHARGE_REGEN_TICKS) {
                this.chargeRegenTimer = 0;
                if (this.phaseSpider.getDodgeCharges() < MAX_DODGE_CHARGES) {
                    this.phaseSpider.addDodgeCharges(1);
                }
            }
            if (target != null && target.distanceToSqr(this.phaseSpider) > 256.0
                    && this.teleportTimer++ >= this.adjustedTickDelay(10)
                    && this.phaseSpider.teleportTowards(target)) {
                this.stopTimer += 40;
                this.teleportTimer = 0;
            }
        }
    }
}
