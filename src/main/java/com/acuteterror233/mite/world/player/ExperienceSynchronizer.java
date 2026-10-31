package com.acuteterror233.mite.world.player;

import net.minecraft.server.level.ServerPlayer;

/**
 * Server-side experience reconciler. MME code changes either {@code experienceLevel} +
 * {@code experienceProgress} or {@code totalExperience} directly, so each tick this class detects
 * which representation changed and rebuilds the other one from it: level/progress win when either
 * changed, otherwise a change in total XP recomputes level and progress. It is ticked by
 * {@code ServerPlayerMixin.doTick} before the vanilla player tick.
 */
public class ExperienceSynchronizer {
    private final ServerPlayer player;

    /** Cached values from the previous tick, used to detect which representation changed. */
    private int prevLevel;
    private float prevProgress;
    private int prevTotal;

    /** @param player the server player whose experience fields are reconciled every tick */
    public ExperienceSynchronizer(ServerPlayer player) {
        this.player = player;
        this.prevLevel = player.experienceLevel;
        this.prevProgress = player.experienceProgress;
        this.prevTotal = player.totalExperience;
    }

    /** Detects which of level/progress/total changed and reconciles the other representations. */
    public void tick() {
        int curLevel = this.player.experienceLevel;
        float curProgress = this.player.experienceProgress;
        int curTotal = this.player.totalExperience;

        if (curLevel == this.prevLevel && curProgress == this.prevProgress && curTotal == this.prevTotal) {
            return;
        }

        boolean levelChanged = curLevel != this.prevLevel;
        boolean progressChanged = curProgress != this.prevProgress;
        boolean totalChanged = curTotal != this.prevTotal;

        if (levelChanged || progressChanged) {
            syncToTotal(curLevel, curProgress);
        } else if (totalChanged) {
            syncFromTotal(curTotal);
        }

        this.prevLevel = this.player.experienceLevel;
        this.prevProgress = this.player.experienceProgress;
        this.prevTotal = this.player.totalExperience;
    }

    /** Recomputes totalExperience from level and progress (authoritative when level/progress changed). */
    private void syncToTotal(int level, float progress) {
        int total = 0;
        for (int i = 0; i < level; i++) {
            total += getXpNeededForLevel(i);
        }
        total += (int) (progress * getXpNeededForLevel(level));
        this.player.totalExperience = total;
    }

    private void syncFromTotal(int total) {
        int remaining = total;
        int level = 0;
        while (true) {
            int needed = getXpNeededForLevel(level);
            if (remaining >= needed) {
                remaining -= needed;
                level++;
            } else {
                break;
            }
        }
        int neededForCurrent = getXpNeededForLevel(level);
        float progress = neededForCurrent > 0 ? (float) remaining / neededForCurrent : 0.0f;
        this.player.experienceLevel = level;
        this.player.experienceProgress = progress;
    }

    /**
     * Vanilla XP curve per level, scaled ×3 for MITE progression: 7+2*level (0-14),
     * 37+5*(level-15) (15-29), 112+9*(level-30) (30+), then multiplied by 3.
     */
    private int getXpNeededForLevel(int level) {
        int total;
        if (level >= 30) {
            total = 112 + (level - 30) * 9;
        } else if (level >= 15) {
            total =  37 + (level - 15) * 5;
        } else {
            total =  7 + level * 2;
        }
        return total * 3;
    }
}
