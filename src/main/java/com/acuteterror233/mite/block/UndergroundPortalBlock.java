package com.acuteterror233.mite.block;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import com.acuteterror233.mite.world.poi.PortalHelper;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.NetherPortal;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;

public class UndergroundPortalBlock extends AbstractPortalBlock {
    public static final MapCodec<UndergroundPortalBlock> CODEC = createCodec(UndergroundPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    private static final Logger LOGGER = LogUtils.getLogger();

    public UndergroundPortalBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public static TeleportTarget getExitPortalTarget(
            Entity entity, BlockPos pos, BlockLocating.Rectangle exitPortalRectangle, ServerWorld world, TeleportTarget.PostDimensionTransition postDimensionTransition
    ) {
        BlockState blockState = entity.getWorld().getBlockState(pos);
        Direction.Axis axis;
        Vec3d vec3d;
        if (blockState.contains(Properties.HORIZONTAL_AXIS)) {
            axis = blockState.get(Properties.HORIZONTAL_AXIS);
            BlockLocating.Rectangle rectangle = BlockLocating.getLargestRectangle(
                    pos, axis, 21, Direction.Axis.Y, 21, posx -> entity.getWorld().getBlockState(posx) == blockState
            );
            vec3d = entity.positionInPortal(axis, rectangle);
        } else {
            axis = Direction.Axis.X;
            vec3d = new Vec3d(0.5, 0.0, 0.0);
        }
        return getExitPortalTarget(world, exitPortalRectangle, axis, vec3d, entity, postDimensionTransition);
    }

    public static TeleportTarget getExitPortalTarget(
            ServerWorld world,
            BlockLocating.Rectangle exitPortalRectangle,
            Direction.Axis axis,
            Vec3d positionInPortal,
            Entity entity,
            TeleportTarget.PostDimensionTransition postDimensionTransition
    ) {
        BlockPos blockPos = exitPortalRectangle.lowerLeft;
        BlockState blockState = world.getBlockState(blockPos);
        Direction.Axis axis2 = blockState.getOrEmpty(Properties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double d = exitPortalRectangle.width;
        double e = exitPortalRectangle.height;
        EntityDimensions entityDimensions = entity.getDimensions(entity.getPose());
        int i = axis == axis2 ? 0 : 90;
        double f = entityDimensions.width() / 2.0 + (d - entityDimensions.width()) * positionInPortal.getX();
        double g = (e - entityDimensions.height()) * positionInPortal.getY();
        double h = 0.5 + positionInPortal.getZ();
        boolean bl = axis2 == Direction.Axis.X;
        Vec3d vec3d = new Vec3d(blockPos.getX() + (bl ? f : h), blockPos.getY() + g, blockPos.getZ() + (bl ? h : f));
        Vec3d vec3d2 = NetherPortal.findOpenPosition(vec3d, world, entity, entityDimensions);
        return new TeleportTarget(world, vec3d2, Vec3d.ZERO, i, 0.0F, PositionFlag.combine(PositionFlag.DELTA, PositionFlag.ROT), postDimensionTransition);
    }

    @Override
    public MapCodec<UndergroundPortalBlock> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        RegistryKey<World> registryKey = world.getRegistryKey() == AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY ? World.OVERWORLD : AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY;
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            double v = serverWorld.getRegistryKey() == AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY ? 200 : -60;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            double d = DimensionType.getCoordinateScaleFactor(world.getDimension(), serverWorld.getDimension());
            BlockPos blockPos = worldBorder.clampFloored(entity.getX() * d, v, entity.getZ() * d);
            return this.getOrCreateExitPortalTarget(serverWorld, entity, pos, blockPos, worldBorder);
        }
    }

    @Nullable
    private TeleportTarget getOrCreateExitPortalTarget(
            ServerWorld world, Entity entity, BlockPos sourcePos, BlockPos scaledPos, WorldBorder worldBorder
    ) {
        RegistryKey<PointOfInterestType> portal = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(At_mite.MOD_ID, "underground_portal"));
        Optional<BlockPos> optional = PortalHelper.getPortalPos(world, scaledPos, 16, worldBorder, portal, this);
        BlockLocating.Rectangle rectangle;
        TeleportTarget.PostDimensionTransition postDimensionTransition;
        if (optional.isPresent()) {
            BlockPos blockPos = optional.get();
            BlockState blockState = world.getBlockState(blockPos);
            rectangle = BlockLocating.getLargestRectangle(
                    blockPos, blockState.get(Properties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, posx -> world.getBlockState(posx) == blockState
            );
            postDimensionTransition = TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(entityx -> entityx.addPortalChunkTicketAt(blockPos));
        } else {
            Direction.Axis axis = entity.getWorld().getBlockState(sourcePos).getOrEmpty(AXIS).orElse(Direction.Axis.X);
            Optional<BlockLocating.Rectangle> optional2 = PortalHelper.createPortal(world, scaledPos, axis, Blocks.OBSIDIAN, this);
            if (optional2.isEmpty()) {
                LOGGER.error("Unable to create a portal, likely target out of worldborder");
                return null;
            }
            rectangle = optional2.get();
            postDimensionTransition = TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET);
        }
        return getExitPortalTarget(entity, sourcePos, rectangle, world, postDimensionTransition);
    }
}
