package com.acuteterror233.mite.world.player;

import net.minecraft.world.entity.player.Player;

/**
 * Experience-level scaling for the two stats MME derives from player progression instead of from
 * attributes: block breaking progress and outgoing melee damage.
 *
 * <p>Both are applied at the very end of their calculation rather than through attribute modifiers,
 * so they compose with everything else the game already folded into the number (tool material,
 * Efficiency, potion effects, critical hits, weapon enchantment bonus) instead of being re-scaled
 * by them. That also keeps the displayed attack-damage tooltip honest: the tooltip shows the
 * weapon's own value, and the level bonus is only ever visible on the damage that actually lands.</p>
 *
 * <p>Curve, both linear and capped at {@link #LEVEL_CAP} (the level at which max health/hunger
 * reaches its own 20 HP cap, so all level rewards stop at the same point):</p>
 * <ul>
 *   <li>Mining speed: {@value #MINING_SPEED_PER_LEVEL_PERCENT}% per level, x1.70 at level 35.</li>
 *   <li>Attack damage: {@value #ATTACK_DAMAGE_PER_LEVEL_PERCENT}% per level, x1.35 at level 35.</li>
 * </ul>
 *
 * <p>Attack damage deliberately scales at half the mining rate: the MITE damage curve is very
 * compressed (bare fist 1.0, netherite sword about 9), so +1%/level already reads as a real gain
 * on a late-game weapon, while mining is 11.7x slower than vanilla and can absorb a larger bonus
 * without trivialising progression.</p>
 */
public final class PlayerLevelScaling {
    /** Highest level that still grants bonuses; matches the max health/hunger cap (20 HP). */
    public static final int LEVEL_CAP = 35;

    /** Mining speed bonus per level, in percent (2 -> +70% at the level cap). */
    public static final int MINING_SPEED_PER_LEVEL_PERCENT = 2;

    /** Attack damage bonus per level, in percent (1 -> +35% at the level cap). */
    public static final int ATTACK_DAMAGE_PER_LEVEL_PERCENT = 1;

    private PlayerLevelScaling() {
    }

    /**
     * @param player the player to read the level from
     * @return the player's experience level clamped to {@code [0, LEVEL_CAP]}
     */
    public static int effectiveLevel(Player player) {
        return Math.clamp(player.experienceLevel, 0, LEVEL_CAP);
    }

    /**
     * @param player the mining player
     * @return multiplier applied to the per-tick block breaking progress ({@code 1.0 + 2% per level})
     */
    public static float miningSpeedMultiplier(Player player) {
        return 1.0F + effectiveLevel(player) * MINING_SPEED_PER_LEVEL_PERCENT / 100.0F;
    }

    /**
     * @param player the attacking player
     * @return multiplier applied to the final melee damage ({@code 1.0 + 1% per level})
     */
    public static float attackDamageMultiplier(Player player) {
        return 1.0F + effectiveLevel(player) * ATTACK_DAMAGE_PER_LEVEL_PERCENT / 100.0F;
    }
}
