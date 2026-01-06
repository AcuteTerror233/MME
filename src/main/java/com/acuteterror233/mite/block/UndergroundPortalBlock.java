package com.acuteterror233.mite.block;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import com.acuteterror233.mite.world.poi.PortalHelper;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;

public class UndergroundPortalBlock extends AbstractPortalBlock {
    public static final MapCodec<UndergroundPortalBlock> CODEC = simpleCodec(UndergroundPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final Logger LOGGER = LogUtils.getLogger();

    public UndergroundPortalBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        ResourceKey<Level> registryKey = world.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY ? Level.OVERWORLD : MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY;
        ServerLevel serverWorld = world.getServer().getLevel(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            double v = serverWorld.dimension() == MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY ? 200 : -60;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            double d = DimensionType.getTeleportationScale(world.dimensionType(), serverWorld.dimensionType());
            BlockPos blockPos = worldBorder.clampToBounds(entity.getX() * d, v, entity.getZ() * d);
            return this.getOrCreateExitPortalTarget(serverWorld, entity, pos, blockPos, worldBorder);
        }
    }
    @Nullable
    private TeleportTransition getOrCreateExitPortalTarget(
            ServerLevel world, Entity entity, BlockPos sourcePos, BlockPos scaledPos, WorldBorder worldBorder
    ) {
        ResourceKey<PoiType> portal = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "underground_portal"));
        Optional<BlockPos> optional = PortalHelper.getPortalPos(world, scaledPos, 16, worldBorder, portal, this);
        BlockUtil.FoundRectangle rectangle;
        TeleportTransition.PostTeleportTransition postDimensionTransition;
        if (optional.isPresent()) {
            BlockPos blockPos = optional.get();
            BlockState blockState = world.getBlockState(blockPos);
            rectangle = BlockUtil.getLargestRectangleAround(
                    blockPos, blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, posx -> world.getBlockState(posx) == blockState
            );
            postDimensionTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(entityx -> entityx.placePortalTicket(blockPos));
        } else {
            Direction.Axis axis = entity.level().getBlockState(sourcePos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
            Optional<BlockUtil.FoundRectangle> optional2 = PortalHelper.createPortal(world, scaledPos, axis, Blocks.OBSIDIAN, this);
            if (optional2.isEmpty()) {
                LOGGER.error("Unable to create a portal, likely target out of worldborder");
                return null;
            }
            rectangle = optional2.get();
            postDimensionTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        }
        return getExitPortalTarget(entity, sourcePos, rectangle, world, postDimensionTransition);
    }
    public static TeleportTransition getExitPortalTarget(
            Entity entity, BlockPos pos, BlockUtil.FoundRectangle exitPortalRectangle, ServerLevel world, TeleportTransition.PostTeleportTransition postDimensionTransition
    ) {
        BlockState blockState = entity.level().getBlockState(pos);
        Direction.Axis axis;
        Vec3 vec3d;
        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
            BlockUtil.FoundRectangle rectangle = BlockUtil.getLargestRectangleAround(
                    pos, axis, 21, Direction.Axis.Y, 21, posx -> entity.level().getBlockState(posx) == blockState
            );
            vec3d = entity.getRelativePortalPosition(axis, rectangle);
        } else {
            axis = Direction.Axis.X;
            vec3d = new Vec3(0.5, 0.0, 0.0);
        }
        return getExitPortalTarget(world, exitPortalRectangle, axis, vec3d, entity, postDimensionTransition);
    }

    public static TeleportTransition getExitPortalTarget(
            ServerLevel world,
            BlockUtil.FoundRectangle exitPortalRectangle,
            Direction.Axis axis,
            Vec3 positionInPortal,
            Entity entity,
            TeleportTransition.PostTeleportTransition postDimensionTransition
    ) {
        BlockPos blockPos = exitPortalRectangle.minCorner;
        BlockState blockState = world.getBlockState(blockPos);
        Direction.Axis axis2 = blockState.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double d = exitPortalRectangle.axis1Size;
        double e = exitPortalRectangle.axis2Size;
        EntityDimensions entityDimensions = entity.getDimensions(entity.getPose());
        int i = axis == axis2 ? 0 : 90;
        double f = entityDimensions.width() / 2.0 + (d - entityDimensions.width()) * positionInPortal.x();
        double g = (e - entityDimensions.height()) * positionInPortal.y();
        double h = 0.5 + positionInPortal.z();
        boolean bl = axis2 == Direction.Axis.X;
        Vec3 vec3d = new Vec3(blockPos.getX() + (bl ? f : h), blockPos.getY() + g, blockPos.getZ() + (bl ? h : f));
        Vec3 vec3d2 = PortalShape.findCollisionFreePosition(vec3d, world, entity, entityDimensions);
        return new TeleportTransition(world, vec3d2, Vec3.ZERO, i, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), postDimensionTransition);
    }

    @Override
    public @NotNull MapCodec<UndergroundPortalBlock> codec() {
        return CODEC;
    }


}
