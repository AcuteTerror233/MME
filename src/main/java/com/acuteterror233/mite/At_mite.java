package com.acuteterror233.mite;

import com.acuteterror233.mite.item.At_Items;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.ToolMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class At_mite implements ModInitializer {
	public static final String MOD_ID = "at_mite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");
		At_Items.init();
	}
}