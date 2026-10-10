package com.acuteterror233.mite.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Server-side logic for the place-source-liquid hotkey: while holding a filled bucket, the
 * key places a <em>source</em> block of the bucket's fluid at the raytraced crosshair target
 * for a fixed cost of {@value #PLACE_COST_XP} XP points.
 *
 * <p>Normal bucket placement creates a <em>temporary</em> source (recorded by
 * {@code BucketItemMixin} and removed by {@code MMETempFluidSources} after one second); this
 * hotkey is the way to place a <em>permanent</em> source: it discards the temporary record right
 * after placing or promotes an existing flowing block of the same fluid to a source. A
 * successful placement deducts the XP and consumes the bucket's fluid (the held filled bucket
 * is swapped for its empty counterpart, creative keeps it), just like a normal placement;
 * mob buckets release their carried mob alongside the fluid.
 * Players without enough XP get no feedback at all and nothing is consumed. Players with
 * infinite materials (creative) bypass the cost.</p>
 */
public final class SourceBucketLogic {
    /** Fixed XP-point cost for placing one source fluid block. */
    public static final int PLACE_COST_XP = 100;

    private SourceBucketLogic() {
    }

    /** Handles the hotkey: validates the held bucket, the target position and the XP cost, then places a source. */
    public static void handlePlaceSource(ServerPlayer player) {
        ItemStack held = player.getMainHandItem();
        if (!(held.getItem() instanceof BucketItem bucket)) {
            return;
        }
        Fluid fluid = bucket.getContent();
        if (!(fluid instanceof FlowingFluid flowing)) {
            return;
        }
        ServerLevel level = (ServerLevel) player.level();
        BlockHitResult hit = raytraceTarget(player, level);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }
        BlockPos pos = hit.getBlockPos();
        BlockState state = level.getBlockState(pos);
        BlockPos target;
        boolean fluidBlockTarget = false;
        FluidState hitFluid = state.getFluidState();
        if (!hitFluid.isEmpty()) {
            // Aiming at a fluid block: only upgrade a flowing block of the same fluid to source in place
            if (hitFluid.isSource() || !hitFluid.getType().isSame(fluid)) {
                return;
            }
            target = pos;
            fluidBlockTarget = true;
        } else {
            // Aiming at a solid block: place against the hit face like a vanilla bucket
            target = pos.relative(hit.getDirection());
        }
        if (!level.mayInteract(player, target) || !player.mayUseItemAt(target, hit.getDirection(), held)) {
            return;
        }
        if (!player.hasInfiniteMaterials() && totalXp(player) < PLACE_COST_XP) {
            return;
        }

        if (fluidBlockTarget) {
            level.setBlock(target, flowing.defaultFluidState().createLegacyBlock(), 11);
        } else if (!bucket.emptyContents(player, level, target, hit)) {
            return;
        }
        // The emptyContents injection just recorded the placement as temporary; undo it —
        // this hotkey's source is permanent
        MMETempFluidSources.discard(level, target);
        BlockState finalState = level.getBlockState(target);
        FluidState placedFluid = finalState.getFluidState();
        if (placedFluid.isEmpty() || !placedFluid.isSource() || !placedFluid.getType().isSame(fluid)) {
            return;
        }
        // Mob buckets release their carried mob along with the fluid, mirroring the vanilla use path
        bucket.checkExtraContent(player, level, held, target);
        player.giveExperiencePoints(-PLACE_COST_XP);
        // Consume the bucket's fluid like a normal placement: survival swaps the held filled
        // bucket for its empty counterpart, creative keeps the filled bucket untouched
        player.setItemInHand(InteractionHand.MAIN_HAND, emptySuccessItem(player, held));
        level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    /** @return the stack the player ends up holding: the bucket's empty counterpart in survival, the filled bucket itself in creative. */
    private static ItemStack emptySuccessItem(ServerPlayer player, ItemStack held) {
        if (held.getItem() instanceof MMEBucketItem bucket) {
            return bucket.getEmptyBarrelSuccessItem(held, player);
        }
        if (held.getItem() instanceof MMEMobBucketItem bucket) {
            return bucket.getEmptyBarrelSuccessItem(held, player);
        }
        return BucketItem.getEmptySuccessItem(held, player);
    }

    /**
     * Replicates the vanilla item-use raytrace: eye position along the view vector across the
     * block interaction range, hitting fluid blocks of any level.
     */
    private static BlockHitResult raytraceTarget(ServerPlayer player, Level level) {
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getViewVector(1.0F).scale(player.blockInteractionRange()));
        return level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
    }

    /** @return the player's current total XP points (accumulated level costs plus current-level progress). */
    private static int totalXp(ServerPlayer player) {
        int total = Math.round(player.experienceProgress * player.getXpNeededForNextLevel());
        for (int level = 0; level < player.experienceLevel; level++) {
            total += xpToNextLevel(level);
        }
        return total;
    }

    /** @return the XP points required to advance from the given level to the next one. */
    private static int xpToNextLevel(int level) {
        if (level >= 31) {
            return 9 * level - 158;
        }
        if (level >= 16) {
            return 5 * level - 38;
        }
        return 2 * level + 7;
    }
}
