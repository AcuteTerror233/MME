package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code Item} — dietary curse bans. The head of {@code use} rejects consuming
 * food items matching the ANIMAL_PRODUCTS / PLANT_PRODUCTS / SOUPS tags while the matching
 * curse is active. On the client the interaction is returned as CONSUME (instead of FAIL)
 * so the use packet still reaches the server, which then reveals the curse.
 */
@Mixin(Item.class)
public abstract class ItemMixin {

    /** Blocks eating food banned by the active dietary curse and reveals it server-side. */
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);
        Holder<MobEffect> ban = CurseLogic.bannedBy(player, stack);
        if (ban == null) {
            return;
        }
        if (level.isClientSide()) {
            cir.setReturnValue(InteractionResult.CONSUME);
        } else {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
