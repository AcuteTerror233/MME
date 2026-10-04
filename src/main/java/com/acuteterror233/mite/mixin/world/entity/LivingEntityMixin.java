package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * Mixin for {@code LivingEntity} — Doubles loot drops under the HUNT moon.
 *
 * <p>At the tail of {@code dropFromLootTable}, when the dimension's special moon phase
 * is {@link SpecialMoonPhase#HUNT_MOON}, the resolved loot table is rolled a second
 * time with the same seed, duplicating every drop of the kill.</p>
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
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
}
