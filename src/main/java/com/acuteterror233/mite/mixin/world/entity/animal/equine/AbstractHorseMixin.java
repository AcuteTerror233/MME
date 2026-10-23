package com.acuteterror233.mite.mixin.world.entity.animal.equine;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements PlayerRideableJumping, HasCustomInventoryScreen, OwnableEntity {

    protected AbstractHorseMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Unique
    private static final int DEFAULT_FEEDING_COOLING = 24000;
    @Unique
    public int feedingCooling = 0;

    @Override
    public void tick() {
        super.tick();
        if (feedingCooling > 0) {
            feedingCooling--;
        }
    }

    @Inject(method = "fedFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    public void fedFood(Player player, ItemStack itemStack, CallbackInfoReturnable<InteractionResult> cir) {
        feedingCooling += DEFAULT_FEEDING_COOLING;
    }

    @Inject(method = "handleEating", at = @At("HEAD"), cancellable = true)
    public void handleEating(Player player, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if (feedingCooling > 0) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getMaxTemper", at = @At("TAIL"), cancellable = true)
    public void getMaxTemper(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(300);
    }

}
