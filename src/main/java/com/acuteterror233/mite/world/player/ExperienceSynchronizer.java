package com.acuteterror233.mite.world.player;

import net.minecraft.server.level.ServerPlayer;

/**
 * Experience synchronizer.
 * Responsible for synchronizing the server-side player's experienceLevel, experienceProgress, and totalExperience.
 */
public class ExperienceSynchronizer {
    private final ServerPlayer player;

    /** Cached values from the previous tick, used to detect which representation changed. */
    private int prevLevel;
    private float prevProgress;
    private int prevTotal;

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

    /** Derives level and progress from totalExperience (capped at level 50) when only the total changed. */
    private void syncFromTotal(int total) {
        int remaining = total;
        int level = 0;
        while (level < 50) {
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

    /** Vanilla XP curve: 7+2*level (0-14), 37+5*(level-15) (15-29), 112+9*(level-30) (30+). */
    private int getXpNeededForLevel(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5;
        } else {
            return 7 + level * 2;
        }
    }
}
