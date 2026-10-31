package com.acuteterror233.mite.world.effect;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

/**
 * MME status effect registry: registers every non-curse custom effect under the {@code mme}
 * namespace in {@code BuiltInRegistries.MOB_EFFECT}. Call {@link #init()} to force
 * class-loading/registration; witch curses are registered separately in {@code MMECurses}.
 */
public class MMEMobEffects {
    /** Nutrition-deprivation effect applied while a player's protein or fiber store is depleted (color 10404919). */
    public static final Holder<MobEffect> MALNUTRITION = register(
            "malnutrition",
            new PermanentNegativeMobEffect(10404919)
    );
    /**
     * Diabetes buff driven by sustained excess sugar intake; uses a custom blend profile (150, 20, 60).
     * The amplifier encodes the disease tier (0-2). Periodic symptom scheduling — pulsed blindness
     * (tier 1), pulsed nausea + blindness (tier 2), permanent nausea + blindness + wither (tier 3+)
     * — lives in {@code FoodDataMixin}; {@link #applySugarNausea} adds the on-eat nausea dose.
     */
    public static final Holder<MobEffect> INSULIN_RESISTANCE = register(
            "insulin_resistance",
            new PermanentNegativeMobEffect(16777215).setBlendDuration(150, 20, 60)
    );

    /** Nausea duration, in ticks, applied each time a diabetic eats sugary food (20 s). */
    public static final int SUGAR_NAUSEA_TICKS = 400;

    /**
     * Punishes eating sugary food while diabetic with {@link #SUGAR_NAUSEA_TICKS} ticks of nausea.
     * Kept apart from the tier scheduler in {@code FoodDataMixin} because it is event-driven (one
     * dose per bite) rather than part of the periodic symptom schedule.
     *
     * @param player the diabetic player that just ate
     */
    public static void applySugarNausea(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, SUGAR_NAUSEA_TICKS, 0), player);
    }

    /**
     * Eating-time multiplier applied to sugary food while diabetic: tier 1 (amplifier 0)
     * stretches the vanilla consume time 5x, tier 2 10x and tier 3+ 20x.
     *
     * @param amplifier the {@link #INSULIN_RESISTANCE} amplifier, 0-2 for tier 1-3
     * @return the multiplier {@code ItemStackMixin} applies to the item's use duration
     */
    public static int sugarEatingPenaltyMultiplier(int amplifier) {
        return switch (amplifier) {
            case 0 -> 5;
            case 1 -> 10;
            default -> 20;
        };
    }

    private static Holder<MobEffect> register(String id, MobEffect mobEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), mobEffect);
    }

    /** No-op classloading hook that triggers static registration. */
    public static void init() {

    }
}
