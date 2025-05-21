package com.acuteterror233.mite.mixin;

import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodComponent.class)
public abstract class FoodComponentMixin implements Consumable {
    @Inject(method = "onConsume",at = @At("HEAD"),cancellable = true)
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable, CallbackInfo ci) {
        Random random = user.getRandom();
        world.playSound((Entity)null, user.getX(), user.getY(), user.getZ(), (SoundEvent)consumable.sound().value(), SoundCategory.NEUTRAL, 1.0F, random.nextTriangular(1.0F, 0.4F));
        if (user instanceof PlayerEntity playerEntity) {
            playerEntity.getHungerManager().eat(((FoodComponent)(Object)this));
            world.playSound((Entity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, MathHelper.nextBetween(random, 0.9F, 1.0F));
        }
        ci.cancel();
    }
}
