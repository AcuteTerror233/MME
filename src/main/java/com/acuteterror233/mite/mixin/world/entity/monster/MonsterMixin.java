package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Monster.class)
/**
 * Mixin for {@code Monster} — 扩展敌对生物通用行为。
 */
public abstract class MonsterMixin extends PathfinderMob implements Enemy {

    protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        float f1 = f;
        if (this.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE) && damageSource.getWeaponItem() != null && damageSource.getWeaponItem().is(MMEItemTags.SILVER_TOOLS)) {
            f1 += 2F;
        }
        return super.hurtServer(serverLevel, damageSource, f1);
    }
}
