package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

/**
 * Mixin for {@code LivingEntity} — Doubles loot drops under the HUNT moon, re-applies curses
 * after effect clears, lifts a witch's curses when it dies, and replaces the vanilla Resistance
 * damage reduction with the MITE armor-granting scheme.
 *
 * <p>At the tail of {@code dropFromLootTable}, when the dimension's special moon phase
 * is {@link SpecialMoonPhase#HUNT_MOON}, the resolved loot table is rolled a second
 * time with the same seed, duplicating every drop of the kill.</p>
 *
 * <p>Because curses are permanent and hidden, {@code removeAllEffects} (TAIL) re-applies every
 * curse the entity carries after milk/potions wipe them, and {@code die} (TAIL) lifts all curses
 * a dying witch inflicted. A {@code hurtServer} redirect excludes lava from one damage-tag test.</p>
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

    /** Rolls the loot table a second time (double drops) under the HUNT moon. */
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


    /** Curses survive effect clears (milk, potions, etc.) — they are re-applied from state. */
    @Inject(method = "removeAllEffects", at = @At("TAIL"))
    private void mme$reapplyCurses(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof CurseCap cap) {
            for (Holder<MobEffect> curse : cap.mme$curseState().curses()) {
                if (!self.hasEffect(curse)) {
                    self.addEffect(CurseLogic.hiddenInstance(curse));
                }
            }
        }
    }

    /** Killing a witch lifts every curse it inflicted. */
    @Inject(method = "die", at = @At("TAIL"))
    private void mme$witchDeathLiftsCurses(DamageSource source, CallbackInfo ci) {
        if ((Object) this instanceof Witch witch) {
            CurseLogic.onWitchDeath(witch);
        }
    }

    @Redirect(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean hurtServer(DamageSource instance, TagKey<DamageType> tag) {
        return !instance.is(DamageTypes.LAVA) && instance.is(tag);
    }
}
