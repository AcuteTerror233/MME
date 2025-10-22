package com.acuteterror233.mite.mixin.screen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Unique
    ScreenHandlerContext context;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
        super(type, syncId, playerInventory, context, forgingSlotsManager);
    }

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void init(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        this.context = context;
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private void updateResult(CallbackInfo ci) {
        Optional<BlockState> blockState = this.context.get(World::getBlockState);
        if (blockState.isPresent()) {
            if (blockState.get().getBlock() == AtBlocks.MITHRIL_ANVIL) {
                this.input.getStack(0).isIn(AtTags.MITHRIL_NONREPAIRABLE);
            }
        }
    }
}
