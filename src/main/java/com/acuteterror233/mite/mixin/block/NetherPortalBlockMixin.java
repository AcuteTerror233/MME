package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {
    @Shadow
    @Final
    public static EnumProperty<Direction.Axis> AXIS;
    @Shadow
    @Final
    private static Logger LOGGER;

    @Overwrite
    @Nullable
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        RegistryKey<World> registryKey = world.getRegistryKey() == World.NETHER ? AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY : World.NETHER;
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            boolean bl = serverWorld.getRegistryKey() == World.NETHER;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            int y = bl ? 50 : -60;
            double d = DimensionType.getCoordinateScaleFactor(world.getDimension(), serverWorld.getDimension());
            BlockPos blockPos = worldBorder.clampFloored(entity.getX() * d, y, entity.getZ() * d);
            return this.getOrCreateExitPortalTarget(serverWorld, entity, pos, blockPos, bl, worldBorder);
        }
    }

    @Shadow
    @Nullable
    private TeleportTarget getOrCreateExitPortalTarget(ServerWorld world, Entity entity, BlockPos pos, BlockPos blockPos, boolean bl, WorldBorder worldBorder) {
        return null;
    }
}
