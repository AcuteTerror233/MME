package com.acuteterror233.mite.block;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;


public enum Rune implements StringRepresentable {
    NUL, QUAS, POR, AN, NOX, FLAM, VAS, DES,
    ORT, TYM, CORP, LOR, MANI, JUX, YLEM, SANCT;

    @Override
    public @NonNull String getSerializedName() {
        return name().toLowerCase();
    }
}
