package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class At_mite implements ModInitializer {
	public static final String MOD_ID = "at_mite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.ofVanilla("block_interaction_range");
	public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.ofVanilla("entity_interaction_range");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");
		AtItems.init();
		AtBlocks.init();
	}
}