package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.RunePortalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Rune portal block.
 * Stores destination coordinates via the block entity {@link RunePortalBlockEntity}, teleporting to the predetermined destination.
 *
 * <p>Teleport semantics: the transition always reuses the portal's own dimension as target level,
 * so a rune portal teleports within the same dimension (no cross-dimension travel). The coordinates
 * come from the stored {@link RunePortalBlockEntity#getDestinationPos()}; the fallback
 * {@code world.getRespawnData().pos()} yields the overworld world spawn for 26.3 (Nether/End
 * {@code getRespawnData()} delegates through {@code DerivedLevelData} to the wrapped overworld
 * data), and those coordinates are then applied in the portal's own dimension.
 */
public class RunePortalBlock extends AbstractPortalBlock implements EntityBlock {

    public RunePortalBlock(Properties settings) {
        super(settings);
    }

    /**
     * Builds a same-dimension teleport to the stored destination, falling back to the
     * world spawn position when this portal has no block entity data.
     *
     * @param world  the dimension containing this portal; reused as the target dimension
     * @param entity the entity passing through the portal
     * @param pos    the portal block position, used to look up its block entity
     * @return a transition within {@code world}; velocity and rotation stay relative to the
     *         entity's current motion
     */
    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel world, Entity entity, BlockPos pos) {
        BlockPos pos1;
        if (world.getBlockEntity(pos) instanceof RunePortalBlockEntity runePortal) pos1 = runePortal.getDestinationPos();
        else pos1 = world.getRespawnData().pos();
        return new TeleportTransition(world, Vec3.atBottomCenterOf(pos1), Vec3.ZERO, 0.0F, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
    }

    /** Creates the {@link RunePortalBlockEntity} that stores this portal's destination. */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RunePortalBlockEntity(pos, state);
    }
}
