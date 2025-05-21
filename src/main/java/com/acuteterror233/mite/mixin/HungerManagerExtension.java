package com.acuteterror233.mite.mixin;

public interface HungerManagerExtension {
     int MaxFoodLevel = 0;
     default int getMaxFoodLevel(){return MaxFoodLevel;}
}
