package com.acuteterror233.mite.world.effect.curse;

import com.acuteterror233.mite.world.effect.PermanentNegativeMobEffect;

/**
 * Marker base class for witch curses (see mcmod item 35669). Curses are permanent harmful
 * effects: they are always applied as infinite, ambient, particle-less and icon-less instances
 * via {@link CurseLogic#hiddenInstance}, so they never tick down. The behaviour is not
 * implemented on the effect itself — it is driven by {@link CurseLogic} and by curse checks in
 * the mixins (armour corrosion, breath cap, sprint/dietary bans, fear penalties, ...).
 */
public class CurseMobEffect extends PermanentNegativeMobEffect {
    /** @param i effect color (RGB24, {@code 0xRRGGBB}) used for the effect icon. */
    public CurseMobEffect(int i) {
        super(i);
    }
}
