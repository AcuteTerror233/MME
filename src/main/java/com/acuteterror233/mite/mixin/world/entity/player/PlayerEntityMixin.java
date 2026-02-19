package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public int experienceLevel;
    @Shadow
    public abstract FoodData getFoodData();

    @Redirect(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"))
    public float blockUsingItem(LivingEntity instance) {
        float v = instance.getSecondsToDisableBlocking();
        return v > 0 ? v : 0.25F;
    }

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.BLOCK_INTERACTION_RANGE, 2.5)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 1.5)
        );
    }

    @Inject(method = "getXpNeededForNextLevel", at = @At("RETURN"), cancellable = true)
    public void getNextLevelExperience(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        int maxHealth = Math.max(6, Math.min(20, 6 + (this.experienceLevel / 5) * 2));
        this.setMaxHealth(maxHealth);
    }

    @Unique
    public void setMaxHealth(int max) {
        if ((int) this.getAttributes().getInstance(Attributes.MAX_HEALTH).getValue() != max) {
            this.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(max);
            ((FoodDataExtension) this.getFoodData()).MME$SetMaxFoodLevel(max);
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V"))
    public void attack(Entity entity, CallbackInfo ci) {
        this.getFoodData().addExhaustion(0.5F);
    }
}
