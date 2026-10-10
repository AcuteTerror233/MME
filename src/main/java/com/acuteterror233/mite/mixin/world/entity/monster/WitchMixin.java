package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin for {@code Witch} — witches curse the player they target. {@code setTarget} is
 * declared on {@code Mob} (witches do not override it), so the added override passes through
 * to the vanilla logic first and then curses the spotted server-side player; curse
 * application is throttled by the once-per-witch-per-player rule inside
 * {@link CurseLogic#applyCurseFromWitch}.
 */
@Mixin(Witch.class)
public abstract class WitchMixin extends Raider implements RangedAttackMob {

    protected WitchMixin(EntityType<? extends Raider> type, Level level) {
        super(type, level);
    }

    /** Applies a random hidden curse when a witch targets a player, keeping vanilla targeting. */
    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target);
        if (target instanceof ServerPlayer player) {
            CurseLogic.applyCurseFromWitch((Witch) (Object) this, player);
        }
    }
}
