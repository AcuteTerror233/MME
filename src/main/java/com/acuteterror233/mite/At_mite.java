package com.acuteterror233.mite;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class At_mite implements ModInitializer {
	public static final String MOD_ID = "at_mite";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");
		ServerPlayConnectionEvents.JOIN.register((handler, server, player) -> {
			ServerPlayerEntity player1 = handler.getPlayer(); // 获取当前加入的玩家
			player1.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(6);
		});
	}
}