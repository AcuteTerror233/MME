package com.acuteterror233.mite.mixin.world.entity.ai.goal.target;

import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code TargetGoal} — Globally scales mob follow (targeting) range by 1.5x, and
 * doubles it further under FROST_MOON.
 *
 * <p>Redirects the FOLLOW_RANGE attribute lookup in {@code getFollowDistance}: the attribute
 * value is multiplied by {@link #FOLLOW_DISTANCE_MULTIPLIER} for every {@code TargetGoal}
 * search, and further doubled when the dimension's special moon phase is
 * {@link SpecialMoonPhase#FROST_MOON}.</p>
 */
@Mixin(TargetGoal.class)
public abstract class TargetGoalMixin {

    /** Global targeting-range multiplier applied to every {@code TargetGoal} search. */
    private static final double FOLLOW_DISTANCE_MULTIPLIER = 1.5D;

    /** Returns the follow-range attribute value, scaled by 1.5x and doubled under FROST_MOON. */
    @Redirect(method = "getFollowDistance", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    protected double getFollowDistance(Mob instance, Holder holder) {
        return instance.getAttributeValue(holder) * FOLLOW_DISTANCE_MULTIPLIER * (instance.level().environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE).equals(SpecialMoonPhase.FROST_MOON) ? 2 : 1);
    }
}
