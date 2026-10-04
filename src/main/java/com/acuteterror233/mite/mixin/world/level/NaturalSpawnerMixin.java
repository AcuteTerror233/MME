package com.acuteterror233.mite.mixin.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code NaturalSpawner} — thins natural passive-mob spawning and tightens
 * spawn-distance limits.
 *
 * <p>Two hooks: an inject at the head of {@code spawnCategoryForPosition} cancels roughly half of
 * all {@code CREATURE} category spawning attempts (server-side only, as the method only runs on the
 * server), and a redirect on the distance check inside
 * {@code isRightDistanceToPlayerAndSpawnPoint} replaces the vanilla minimum spawn radius with a
 * fixed 12 blocks, keeping spawns closer to players.</p>
 */
@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    /**
     * Injected at the head of {@code spawnCategoryForPosition}: cancels about half of the
     * passive ({@code CREATURE}) spawning attempts to reduce animal populations.
     *
     * @param mobCategory        the spawning category being attempted
     * @param serverLevel        the server level performing the spawn cycle
     * @param chunkAccess        the chunk being filled with mobs
     * @param blockPos           the reference position for this spawn attempt
     * @param spawnPredicate     vanilla filter deciding whether a mob may spawn
     * @param afterSpawnCallback vanilla callback invoked after each successful spawn
     * @param ci                 cancellation callback; cancelling skips the whole category attempt
     */
    @Inject(method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V", at = @At("HEAD"), cancellable = true)
    private static void onSpawnCategoryForPosition(MobCategory mobCategory, ServerLevel serverLevel, ChunkAccess chunkAccess, BlockPos blockPos, NaturalSpawner.SpawnPredicate spawnPredicate, NaturalSpawner.AfterSpawnCallback afterSpawnCallback, CallbackInfo ci) {
        if (mobCategory == MobCategory.CREATURE && serverLevel.getRandom().nextFloat() > 0.5F) {
            ci.cancel();
        }
    }

    /**
     * Redirects the {@code BlockPos#closerToCenterThan} call inside
     * {@code isRightDistanceToPlayerAndSpawnPoint}: shrinks the vanilla minimum spawn radius
     * (24 blocks) to 12 so mobs may spawn closer to players and the world spawn point.
     *
     * @param instance the block position being tested
     * @param position the center (player or spawn point) to measure against
     * @param v        the vanilla radius argument, ignored
     * @return whether the position is within 12 blocks of {@code position}
     */
    @Redirect(method = "isRightDistanceToPlayerAndSpawnPoint", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerToCenterThan(Lnet/minecraft/core/Position;D)Z"))
    private static boolean closerToCenterThan(BlockPos instance, Position position, double v){
        return instance.closerToCenterThan(position, 12);
    }
}