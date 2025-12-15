package com.acuteterror233.mite.mixin.block;

import com.acuteterror233.mite.atinterface.GeneralPortalExtension;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.generator.RunePortalCoordinateGenerator;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {
    @Shadow
    public abstract BlockState getPlacementState(ItemPlacementContext ctx);

    /**
     * @author  AcuteTerror233
     * @reason  添加了创建多种传送门的判断逻辑
     */
    @Overwrite
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            Optional<NetherPortal> optional = NetherPortal.getNewPortal(world, pos, Direction.Axis.X);
            if (optional.isPresent()) {
                GeneralPortalExtension extension = (GeneralPortalExtension) optional.get();
                RegistryKey<World> worldRegistryKey = world.getRegistryKey();
                if (worldRegistryKey == World.OVERWORLD) {
                    boolean adamantiumRunePortalValid = extension.isRunePortalValid(world, AtTags.ADAMANTIUM_RUNESTORE);
                    boolean mithrilPortalValid = extension.isRunePortalValid(world, AtTags.MITHRIL_RUNESTORE);
                    if (adamantiumRunePortalValid || mithrilPortalValid){
                        int maxDistance = 5000;
                        int minDistance = 3000;
                        if (adamantiumRunePortalValid){
                            maxDistance *= 8;
                            minDistance *= 8;
                        }
                        List<BlockPos> posList = extension.getCornerPosList();
                        List<BlockState> stateList = extension.getCornerStateList(world);
                        BlockPos originalCoord = posList.getFirst();
                        BlockPos newCoord = RunePortalCoordinateGenerator.getRunePortalCoordinate(stateList.getFirst().getBlock().toString(), stateList.get(1).getBlock().toString(), stateList.get(2).getBlock().toString(), stateList.get(3).getBlock().toString(), originalCoord, minDistance, maxDistance);
                        int y = world.getWorldChunk(newCoord).sampleHeightmap(Heightmap.Type.WORLD_SURFACE, newCoord.getX(), newCoord.getZ()) + 1;
                        extension.createRunePortal(world, AtBlocks.RUNE_PORTAL, BlockPos.ofFloored(newCoord.getX(), y, newCoord.getZ()));
                        return;
                    }else if (extension.CheckLowerCorner(world, Blocks.BEDROCK)) {
                        extension.createPortal(world, AtBlocks.UNDERGROUND_PORTAL);
                        return;
                    }
                } else if (worldRegistryKey == AtDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY && extension.CheckLowerCorner(world, AtBlocks.MANTLE)) {
                    NetherPortal portal = optional.get();
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
