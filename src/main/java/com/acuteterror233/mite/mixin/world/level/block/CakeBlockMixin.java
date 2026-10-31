package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.interfaces.FoodDataExtension;
import com.acuteterror233.mite.world.food.FoodNutrition;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for {@code CakeBlock} — reworks the nutrition granted by eating cake.
 *
 * <p>The {@code eat} redirect intercepts the {@code FoodData#eat} call: for MME's extended food
 * data ({@link FoodDataExtension}) it additionally records {@link FoodNutrition} with 1600 protein
 * (fiber and sugar both 0), and replaces the vanilla saturation modifier with a flat 1.0 regardless
 * of the vanilla argument. Applies on the server where eating is processed; the client mirrors the
 * result through sync.</p>
 */
@Mixin(CakeBlock.class)
public class CakeBlockMixin {
    /**
     * Redirects the {@code FoodData#eat(int, float)} call inside {@code CakeBlock#eat}: injects the
     * MME nutrition entry ({@code FoodNutrition(protein=1600, fiber=0, sugar=0)}) when supported and
     * forces the saturation argument to 1.0.
     *
     * @param foodData the player's food data being fed
     * @param i        the vanilla food value restored
     * @param f        the vanilla saturation modifier (ignored, replaced by 1.0)
     */
    @Redirect(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"))
    private static void eat(FoodData foodData, int i, float f) {
        if (foodData instanceof FoodDataExtension ext) {
            ext.MME$AddFoodNutrition(new FoodNutrition(1600, 0, 0));
        }
        foodData.eat(i, 1.0F);
    }
}
