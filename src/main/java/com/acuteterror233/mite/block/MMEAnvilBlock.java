package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.interfaces.MetalMenuExtension;
import com.acuteterror233.mite.material.MetalMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * MME anvil block, extending vanilla anvil behavior with material restrictions.
 * Stores block entity {@link com.acuteterror233.mite.block.entity.AnvilBlockEntity}, degrading to a specified block when damaged.
 */
public class MMEAnvilBlock extends AnvilBlock implements EntityBlock {
    private final MetalMaterial metal;
    private final Block damageBlock;
    public MMEAnvilBlock(Properties settings, MetalMaterial metal, Block damageBlock) {
        super(settings);
        this.damageBlock = damageBlock;
        this.metal = metal;
    }

    /** Orients the anvil's {@code FACING} to the player's view direction rotated one step clockwise. */
    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getClockWise());
    }

    /**
     * Seeds the block entity's durability from the placed item stack
     * (max damage and existing damage from NBT), counting the placement itself as one use.
     */
    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.setMaxDamage(itemStack.getMaxDamage());
            anvilBlockEntity.setDamage(itemStack.getDamageValue());
            anvilBlockEntity.addDamage(1);
        }
    }


    /** Carries the block entity's accumulated damage onto the dropped anvil item. */
    @Override
    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity && !stacks.isEmpty()) {
            stacks.getFirst().setDamageValue(anvilBlockEntity.getDamage());
        }
        return stacks;
    }

    /** Creates the {@link AnvilBlockEntity} bound to this block's position in the damage chain. */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(pos, state, this.damageBlock);
    }

    /** Serves the vanilla {@link AnvilMenu} with this anvil's {@link MetalMaterial} injected via the duck interface. */
    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, player) -> {
                    AnvilMenu menu = new AnvilMenu(syncId, inventory, ContainerLevelAccess.create(world, pos));
                    ((MetalMenuExtension) menu).MME$SetMetalMaterial(this.metal);
                    return menu;
                }, getName()
        );
    }
}