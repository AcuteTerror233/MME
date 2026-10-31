package com.acuteterror233.mite.world.entity.monster;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

/**
 * Skeleton Lord entity, inherits skeleton behavior. Spawns in a full rusted iron set holding
 * a rusted iron sword, has 50 max health with 3 armor points, periodically summons ancient
 * corpses while it has a target (buffing itself with Strength I and Speed I), and heals
 * skeletons (including itself) within 5 blocks by 1 health point every second.
 */
public class SkeletonLord extends Skeleton {
    /** Ticks between ancient corpse summons (one minute). */
    private static final int SUMMON_COOLDOWN_TICKS = 1200;
    /** Duration of the Strength I / Speed I self-buff granted on each summon. */
    private static final int SELF_BUFF_DURATION_TICKS = 1200;
    /** Ticks between heal aura pulses (one second). */
    private static final int HEAL_INTERVAL_TICKS = 20;
    /** Radius of the heal aura around the lord. */
    private static final double HEAL_RADIUS = 5.0D;
    /** Health points restored per heal pulse. */
    private static final float HEAL_AMOUNT = 1.0F;

    /** Countdown to the next ancient corpse summon. */
    private int summonCooldown;
    /** Countdown to the next heal aura pulse. */
    private int healCooldown;

    /** Skeleton lord base attributes: skeleton stats with 50 max health and 3 armor. */
    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ARMOR, 3.0D);
    }

    public SkeletonLord(EntityType<? extends SkeletonLord> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * While the lord has a target it summons three ancient corpses every minute, buffing
     * itself with Strength I and Speed I; independently, it pulses a heal aura that restores
     * 1 health point to every skeleton within 5 blocks (itself included) every second.
     */
    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        if (this.getTarget() != null && --this.summonCooldown <= 0) {
            this.summonCooldown = SUMMON_COOLDOWN_TICKS;
            this.summonAncientCorpses(serverLevel);
            this.addEffect(new MobEffectInstance(MobEffects.STRENGTH, SELF_BUFF_DURATION_TICKS, 0));
            this.addEffect(new MobEffectInstance(MobEffects.SPEED, SELF_BUFF_DURATION_TICKS, 0));
        }
        if (--this.healCooldown <= 0) {
            this.healCooldown = HEAL_INTERVAL_TICKS;
            for (Skeleton skeleton : serverLevel.getEntitiesOfClass(Skeleton.class, this.getBoundingBox().inflate(HEAL_RADIUS))) {
                if (skeleton.isAlive()) {
                    skeleton.heal(HEAL_AMOUNT);
                }
            }
        }
    }

    /** Spawns three ancient corpses in a ring around the lord. */
    private void summonAncientCorpses(ServerLevel serverLevel) {
        for (int i = 0; i < 3; i++) {
            AncientCorpse corpse = MMEEntityTypes.ANCIENT_CORPSE.create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
            if (corpse != null) {
                double angle = this.getRandom().nextDouble() * (Math.PI * 2);
                corpse.snapTo(this.getX() + Math.cos(angle) * 2.0D, this.getY(), this.getZ() + Math.sin(angle) * 2.0D, this.getRandom().nextFloat() * 360.0F, 0.0F);
                corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(corpse.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
                serverLevel.addFreshEntity(corpse);
            }
        }
    }

    /** Always spawns in a full rusted iron set holding a rusted iron sword. */
    @Override
    protected void populateDefaultEquipmentSlots(@NonNull RandomSource randomSource, @NonNull DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(MMEItems.RUSTED_IRON_HELMET));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(MMEItems.RUSTED_IRON_CHESTPLATE));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(MMEItems.RUSTED_IRON_LEGGINGS));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(MMEItems.RUSTED_IRON_BOOTS));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MMEItems.RUSTED_IRON_SWORD));
    }
}
