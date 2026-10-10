package com.acuteterror233.mite.network;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Server-to-client payload carrying the player's current nutrition counters
 * ({@link FoodNutrition}: protein / fiber / sugar) so the client can draw the
 * nutrition bars on the inventory screen.
 *
 * @param nutrition the player's current nutrient counters
 */
public record FoodNutritionPayload(FoodNutrition nutrition) implements CustomPacketPayload {
    /** Payload channel id under the MME namespace. */
    public static final Type<FoodNutritionPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MME.MOD_ID, "food_nutrition"));
    /** Client-sync codec wrapping {@link FoodNutrition#DIRECT_STREAM_CODEC} field-for-field. */
    public static final StreamCodec<RegistryFriendlyByteBuf, FoodNutritionPayload> STREAM_CODEC =
            StreamCodec.composite(FoodNutrition.DIRECT_STREAM_CODEC, FoodNutritionPayload::nutrition, FoodNutritionPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
