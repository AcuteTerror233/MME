package com.acuteterror233.mite.network;

import com.acuteterror233.mite.item.SourceBucketLogic;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

/**
 * MME payload-type registry: registers every custom packet channel used by the mod
 * and provides the server-side send helpers for them.
 */
public class MMENetworking {
    /** Registers all payload codecs and receivers; called once from the mod's common init. */
    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(FoodNutritionPayload.TYPE, FoodNutritionPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PlaceSourceLiquidPayload.TYPE, PlaceSourceLiquidPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PlaceSourceLiquidPayload.TYPE,
                (payload, context) -> SourceBucketLogic.handlePlaceSource(context.player()));
    }

    /** Sends the player's current nutrition counters to their client (inventory nutrition bars). */
    public static void sendFoodNutrition(ServerPlayer player, FoodNutrition nutrition) {
        ServerPlayNetworking.send(player, new FoodNutritionPayload(nutrition));
    }
}
