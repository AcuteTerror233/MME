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

/**
 * Mixin for {@code Monster} — Adds a silver-weapon vulnerability.
 *
 * <p>Hostile mobs tagged {@code SENSITIVE_TO_SMITE} (e.g. undead) take 2 extra damage
 * when struck by a weapon tagged {@code MMEItemTags#SILVER_TOOLS}.</p>
 */
@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob implements Enemy {

    protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    /** Adds 2 damage when a smite-sensitive monster is hit by a silver weapon. */
    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        if (this.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_SMITE) && damageSource.getWeaponItem() != null && damageSource.getWeaponItem().is(MMEItemTags.SILVER_TOOLS)) {
            f += 2F;
        }
        return super.hurtServer(serverLevel, damageSource, f);
    }
}
