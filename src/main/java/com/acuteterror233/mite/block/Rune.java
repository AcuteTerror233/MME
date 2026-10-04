package com.acuteterror233.mite.block;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;


/**
 * The sixteen rune types, keys of a {@link RunestoneCollection}
 * ({@code nul}, {@code quas}, {@code por}, ... {@code sanct}).
 */
public enum Rune implements StringRepresentable {
    NUL, QUAS, POR, AN, NOX, FLAM, VAS, DES,
    ORT, TYM, CORP, LOR, MANI, JUX, YLEM, SANCT;

    /** Lowercase rune name, e.g. {@code quas}. */
    @Override
    public @NonNull String getSerializedName() {
        return name().toLowerCase();
    }
}
