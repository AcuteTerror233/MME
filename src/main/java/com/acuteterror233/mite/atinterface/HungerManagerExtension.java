package com.acuteterror233.mite.atinterface;

public interface HungerManagerExtension {
     default int getMaxFoodLevel(){return 0;};
     default void setMaxFoodLevel(int maxFoodLevel){};
}
