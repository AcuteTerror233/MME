package com.acuteterror233.mite.world.effect.curse;

/**
 * Duck interface implemented by {@code PlayerMixin} on {@code Player} to expose the witch-curse
 * bookkeeping state; cast a {@code Player} to this interface to reach it.
 */
public interface CurseCap {
    /** @return the player's mutable curse state. */
    CurseState mme$curseState();
}
