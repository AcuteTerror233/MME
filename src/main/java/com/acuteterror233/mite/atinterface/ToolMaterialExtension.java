package com.acuteterror233.mite.atinterface;

public interface ToolMaterialExtension {
    default int getDurability() { return 0; }
    default void setDurability(int durability){ };
}
