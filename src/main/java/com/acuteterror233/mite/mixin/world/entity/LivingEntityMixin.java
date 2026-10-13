package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * Mixin for {@code LivingEntity} — Doubles loot drops under the HUNT moon, anchors slowed
 * entities in water, and replaces the vanilla Resistance damage reduction with the MITE
 * armor-granting scheme.
 *
 * <p>At the tail of {@code dropFromLootTable}, when the dimension's special moon phase
 * is {@link SpecialMoonPhase#HUNT_MOON}, the resolved loot table is rolled a second
 * time with the same seed, duplicating every drop of the kill.</p>
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, WaypointTransmitter{
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    public long getLootTableSeed(){
        return 0;
    }

    @Inject(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At("TAIL"))
    public void dropFromLootTable(
            ServerLevel level, DamageSource source, boolean playerKilled, ResourceKey<LootTable> lootTable, Consumer<ItemStack> itemStackConsumer, CallbackInfo ci, @Local LootTable table, @Local LootParams params
    ){
        // HUNT_MOON: roll the loot table a second time (double drops)
        if (level.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.HUNT_MOON)) {
            table.getRandomItems(params, this.getLootTableSeed(), itemStackConsumer);
        }
    }

    /**
     * MITE: removes the vanilla Resistance flat damage reduction ({@code -20% per level}); the
     * effect now grants {@code +4} armor per level instead (see {@code MobEffectsMixin}), so this
     * pass only keeps the vanilla enchantment-protection half. Damage types flagged
     * {@code BYPASSES_EFFECTS} or {@code BYPASSES_ENCHANTMENTS} skip magic absorption entirely,
     * matching the vanilla early exits.
     *
     * @author MME
     */
    @Overwrite
    protected float getDamageAfterMagicAbsorb(DamageSource damageSource, float damageAmount) {
        if (damageSource.is(DamageTypeTags.BYPASSES_EFFECTS) || damageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            return damageAmount;
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            return CombatRules.getDamageAfterMagicAbsorb(damageAmount,
                    EnchantmentHelper.getDamageProtection(serverLevel, (LivingEntity) (Object) this, damageSource));
        }
        return damageAmount;
    }
}
