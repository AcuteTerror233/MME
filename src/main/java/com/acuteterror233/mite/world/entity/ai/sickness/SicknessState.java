package com.acuteterror233.mite.world.entity.ai.sickness;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * Mutable per-animal sickness bookkeeping: one progress value per need, the quench
 * timers for thirst and grazing, and the current sickness flag.
 */
public class SicknessState {
    /** Progress toward sickness per need; 0 means the need is fully satisfied. */
    public final int[] progress = new int[SicknessType.COUNT];
    /** Whether the animal currently counts as sick. */
    public boolean sick;
    /** Clock time until which the animal's thirst stays quenched after drinking. */
    public long waterUntil;
    /** Clock time until which the animal's grazing need stays satisfied after eating. */
    public long grazeUntil;

    /** Persists the state into the animal's save data. */
    public void save(ValueOutput output) {
        output.putIntArray("mme:SicknessProgress", progress);
        output.putBoolean("mme:Sick", sick);
        output.putLong("mme:WaterUntil", waterUntil);
        output.putLong("mme:GrazeUntil", grazeUntil);
    }

    /** Restores the state from the animal's save data. */
    public void load(ValueInput input) {
        input.getIntArray("mme:SicknessProgress").ifPresent(values -> {
            int length = Math.min(values.length, progress.length);
            for (int i = 0; i < length; i++) {
                progress[i] = Math.max(0, values[i]);
            }
        });
        sick = input.getBooleanOr("mme:Sick", false);
        waterUntil = input.getLongOr("mme:WaterUntil", 0L);
        grazeUntil = input.getLongOr("mme:GrazeUntil", 0L);
    }
}
