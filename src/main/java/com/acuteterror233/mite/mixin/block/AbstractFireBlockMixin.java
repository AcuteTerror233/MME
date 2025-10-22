package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.GeneralPortalExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
    @Overwrite
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            Optional<NetherPortal> optional = NetherPortal.getNewPortal(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                GeneralPortalExtension extension = (GeneralPortalExtension) optional.get();
                NetherPortal portal = optional.get();
                List<BlockPos> cornerList = extension.getCornerList();
                boolean isAdamantiumRunePortal = true;
                boolean isMithrilRunePortal = true;
                for (BlockPos blockPos : cornerList) {
                    BlockState blockState = world.getBlockState(blockPos);
                    if (isAdamantiumRunePortal && !blockState.isIn(AtTags.ADAMANTIUM_RUNESTORE)) {
                        isAdamantiumRunePortal = false;
                    }
                    if (isMithrilRunePortal && !blockState.isIn(AtTags.MITHRIL_RUNESTORE)) {
                        isMithrilRunePortal = false;
                    }
                    if (!isAdamantiumRunePortal && !isMithrilRunePortal) {
                        break;
                    }
                }
                final int ADAMANTIUM_PORTAL_DISTANCE = 20000;
                final int MITHRIL_PORTAL_DISTANCE = 4000;
                int distance = isMithrilRunePortal ? MITHRIL_PORTAL_DISTANCE : ADAMANTIUM_PORTAL_DISTANCE;
                if (isAdamantiumRunePortal || isMithrilRunePortal && world.getRegistryKey() == World.OVERWORLD) {
                    //符文门传送逻辑(待补全)
                    BlockPos pos1 = cornerList.getFirst();
                    Vec3d centerPos = pos1.toCenterPos();
                    int i = world.getWorldChunk(pos1).sampleHeightmap(Heightmap.Type.WORLD_SURFACE, pos1.getX(), pos1.getZ()) + 1;
//                    extension.createRunePortal(world, AtBlocks.RUNE_PORTAL, BlockPos.ofFloored(centerPos.x, i, centerPos.z));
                    extension.createRunePortal(world, AtBlocks.RUNE_PORTAL, world.getSpawnPos());
                    return;
                } else if (world.getRegistryKey() == World.OVERWORLD && extension.CheckLowerCorner(world, Blocks.BEDROCK)) {
                    extension.createPortal(world, AtBlocks.UNDERGROUND_PORTAL);
                    return;
                } else if (world.getRegistryKey() == AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY && extension.CheckLowerCorner(world, AtBlocks.MANTLE)) {
                    portal.createPortal(world);
                    return;
                }
                extension.createPortal(world, AtBlocks.HOME_PORTAL);
                return;
            }
        }

        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
    }
}
