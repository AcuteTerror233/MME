package com.acuteterror233.mite.world.food;

import com.acuteterror233.mite.atinterface.FoodDataExtension;
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

public record FoodNutrition(float protein, float fiber, float sugar) implements ConsumableListener {
	public static final Codec<FoodNutrition> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.FLOAT.fieldOf("protein").forGetter(FoodNutrition::protein),
				Codec.FLOAT.fieldOf("fiber").forGetter(FoodNutrition::fiber),
				Codec.FLOAT.fieldOf("sugar").forGetter(FoodNutrition::sugar)
			)
			.apply(instance, FoodNutrition::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, FoodNutrition> DIRECT_STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.FLOAT,
		FoodNutrition::protein,
		ByteBufCodecs.FLOAT,
		FoodNutrition::fiber,
		ByteBufCodecs.FLOAT,
		FoodNutrition::sugar,
		FoodNutrition::new
	);

	@Override
	public void onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack, Consumable consumable) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getFoodData() instanceof FoodDataExtension foodData) {
                foodData.MME$AddFoodNutrition(this);
            }
        }
	}
	public static FoodNutrition.Builder builder() {
		return new FoodNutrition.Builder();
	}

	public static class Builder {
		private float protein = 0;
		private float fiber = 0;
		private float sugar = 0;

		public FoodNutrition.Builder protein(float protein) {
			this.protein = protein;
			return this;
		}

		public FoodNutrition.Builder fiber(float fiber) {
			this.fiber = fiber;
			return this;
		}

		public FoodNutrition.Builder sugar(float sugar) {
			this.sugar = sugar;
			return this;
		}

		public FoodNutrition build() {
			return new FoodNutrition(this.protein, this.fiber, this.sugar);
		}
	}
}