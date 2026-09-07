package com.acuteterror233.mite.world.player;

import net.minecraft.server.level.ServerPlayer;

/**
 * 经验同步器。
 * 负责同步服务端玩家的 experienceLevel、experienceProgress、totalExperience。
 */
public class ExperienceSynchronizer {
    private final ServerPlayer player;

    private int prevLevel;
    private float prevProgress;
    private int prevTotal;

    public ExperienceSynchronizer(ServerPlayer player) {
        this.player = player;
        this.prevLevel = player.experienceLevel;
        this.prevProgress = player.experienceProgress;
        this.prevTotal = player.totalExperience;
    }

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
