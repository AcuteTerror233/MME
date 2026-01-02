package com.acuteterror233.mite.mixin.entity.player;

import com.acuteterror233.mite.atinterface.HungerManagerExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public int experienceLevel;
    @Shadow
    protected HungerManager hungerManager;

    @Shadow
    public abstract HungerManager getHungerManager();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
                .add(EntityAttributes.MAX_HEALTH, 6)
                .add(EntityAttributes.BLOCK_INTERACTION_RANGE, 2)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.5)
        );
    }

    @Inject(method = "getNextLevelExperience", at = @At("RETURN"), cancellable = true)
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
        this.getAttributes().getCustomInstance(EntityAttributes.MAX_HEALTH).setBaseValue(max);
        ((HungerManagerExtension) this.getHungerManager()).setMaxFoodLevel(max);
    }
}
