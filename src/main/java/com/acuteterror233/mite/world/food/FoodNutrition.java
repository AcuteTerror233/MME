package com.acuteterror233.mite.world.food;

import com.acuteterror233.mite.network.MMENetworking;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.level.Level;

/**
 * MITE-style nutrition component carried by food items. Every food contributes three
 * nutrient values — {@code protein}, {@code fiber} and {@code sugar} — which are credited to
 * the eater's MME food data on consumption and drive the malnutrition / insulin-resistance
 * status effects (see {@code MMEMobEffects}).
 *
 * <p>Attached to items through the vanilla {@link ConsumableListener} hook, so eating any
 * item with this data component automatically applies its nutrition; persistence and
 * networking are handled by {@link #DIRECT_CODEC} / {@link #DIRECT_STREAM_CODEC}.</p>
 *
 * @param protein protein value granted on consumption (non-negative; feeds the protein stat)
 * @param fiber   fiber value granted on consumption (non-negative; feeds the fiber stat)
 * @param sugar   sugar value granted on consumption (non-negative; excess sugar feeds insulin resistance)
 */
public record FoodNutrition(float protein, float fiber, float sugar) implements ConsumableListener {
    /** Data-component codec: reads/writes the three fields under {@code protein}, {@code fiber}, {@code sugar}. */
    public static final Codec<FoodNutrition> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
                Codec.FLOAT.fieldOf("protein").forGetter(FoodNutrition::protein),
                Codec.FLOAT.fieldOf("fiber").forGetter(FoodNutrition::fiber),
                Codec.FLOAT.fieldOf("sugar").forGetter(FoodNutrition::sugar)
            )
            .apply(instance, FoodNutrition::new)
    );
    /** Client-sync codec matching {@link #DIRECT_CODEC} field-for-field. */
    public static final StreamCodec<RegistryFriendlyByteBuf, FoodNutrition> DIRECT_STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.FLOAT,
        FoodNutrition::protein,
        ByteBufCodecs.FLOAT,
        FoodNutrition::fiber,
        ByteBufCodecs.FLOAT,
        FoodNutrition::sugar,
        FoodNutrition::new
    );

    /**
     * Consumable callback: when a server-side player finishes eating, credit this food's
     * three nutrient values to the player's MME food data.
     */
    @Override
    public void onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getFoodData().MME$AddFoodNutrition(this);
            // Immediate nutrition sync so the inventory bars update right after eating
            MMENetworking.sendFoodNutrition(serverPlayer, serverPlayer.getFoodData().MME$GetFoodNutrition());
        }
    }
    /** @return a fresh builder for constructing {@link FoodNutrition} values piecewise. */
    public static FoodNutrition.Builder builder() {
        return new FoodNutrition.Builder();
    }

    /** Fluent builder over the three nutrient fields; unset fields default to 0. */
    public static class Builder {
        private float protein = 0;
        private float fiber = 0;
        private float sugar = 0;

        /** @param protein protein value granted on consumption. */
        public FoodNutrition.Builder protein(float protein) {
            this.protein = protein;
            return this;
        }

        /** @param fiber fiber value granted on consumption. */
        public FoodNutrition.Builder fiber(float fiber) {
            this.fiber = fiber;
            return this;
        }

        /** @param sugar sugar value granted on consumption. */
        public FoodNutrition.Builder sugar(float sugar) {
            this.sugar = sugar;
            return this;
        }

        /** @return the assembled immutable {@link FoodNutrition}. */
        public FoodNutrition build() {
            return new FoodNutrition(this.protein, this.fiber, this.sugar);
        }
    }
}