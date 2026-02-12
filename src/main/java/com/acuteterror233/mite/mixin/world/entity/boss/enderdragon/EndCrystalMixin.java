package com.acuteterror233.mite.mixin.world.entity.boss.enderdragon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity{
    public EndCrystalMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author AcuteTerror233
     * @reason 只允许下届合金镐破坏
     */
    @Overwrite
    public final boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        if (this.isInvulnerableToBase(damageSource)) {
            return false;
        } else if (damageSource.getEntity() instanceof EnderDragon) {
            return false;
        } else if (damageSource.getWeaponItem() == null || !damageSource.getWeaponItem().is(Items.NETHERITE_PICKAXE)) {
            return false;
        } else {
            if (!this.isRemoved()) {
                this.remove(Entity.RemovalReason.KILLED);
                if (!damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    DamageSource damageSource2 = damageSource.getEntity() != null ? this.damageSources().explosion(this, damageSource.getEntity()) : null;
                    serverLevel.explode(this, damageSource2, null, this.getX(), this.getY(), this.getZ(), 6.0F, false, Level.ExplosionInteraction.BLOCK);
                }
                this.onDestroyedBy(serverLevel, damageSource);
            }
            return true;
        }
    }
    @Shadow
    protected abstract void onDestroyedBy(ServerLevel serverLevel, DamageSource damageSource);
}
