package com.acuteterror233.mite.newinterface;

public interface HungerManagerExtension {
     default int getMaxFoodLevel(){return 0;};
     default void setMaxFoodLevel(int maxFoodLevel){};
}
