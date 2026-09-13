package com.acuteterror233.mite.block;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

/**
 * The three damage states of an anvil.
 * The registry prefix is used to build the block/item id (e.g. {@code chipped_copper_anvil}).
 */
public enum AnvilState implements StringRepresentable {
    INTACT(""),
    CHIPPED("chipped_"),
    DAMAGED("damaged_");

    private final String prefix;

    AnvilState(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name().toLowerCase();
    }
}
