package com.acuteterror233.mite.atinterface;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public interface AbstractBlockSettingsExtension {
    Block getAnvil();
    AbstractBlock.Settings setAnvil(Block block);
}
