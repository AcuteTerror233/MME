package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
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

    /**
     * @author  AcuteTerror233
     * @reason  更改的地狱传送门的传送逻辑
     */
    @Overwrite
    @Nullable
    public TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        ResourceKey<Level> registryKey = world.dimension() == Level.NETHER ? MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY : Level.NETHER;
        ServerLevel serverWorld = world.getServer().getLevel(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            boolean bl = serverWorld.dimension() == Level.NETHER;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            int y = bl ? 50 : -60;
            double d = DimensionType.getTeleportationScale(world.dimensionType(), serverWorld.dimensionType());
            BlockPos blockPos = worldBorder.clampToBounds(entity.getX() * d, y, entity.getZ() * d);
            return this.getExitPortal(serverWorld, entity, pos, blockPos, bl, worldBorder);
        }
    }

    @Shadow
    @Nullable
    private TeleportTransition getExitPortal(ServerLevel world, Entity entity, BlockPos pos, BlockPos blockPos, boolean bl, WorldBorder worldBorder) {
        return null;
    }
}
