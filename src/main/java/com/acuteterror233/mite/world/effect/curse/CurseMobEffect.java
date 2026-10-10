package com.acuteterror233.mite.world.effect.curse;

import com.acuteterror233.mite.world.effect.PermanentNegativeMobEffect;

/**
 * Base class for witch curses (see mcmod item 35669): permanent negative effects
 * inflicted by witches, removed only by killing the witch or drinking a curse-removing potion.
 * Each curse is currently a skeleton registration; gameplay logic (armour durability doubling,
 * breath cap, sprint denial, dietary bans, fear penalties, etc.) is not implemented yet.
 */
public class CurseMobEffect extends PermanentNegativeMobEffect {
    /** @param i particle color (ARGB) used for the effect icon. */
    public CurseMobEffect(int i) {
        super(i);
    }
}
