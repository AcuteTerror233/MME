package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.inventory.GradeAnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MMEAnvilBlock extends AnvilBlock implements EntityBlock {
    private final TagKey<Item> notAllowedMaterial;
    public MMEAnvilBlock(Properties settings, TagKey<Item> notAllowedMaterial) {
        super(settings);
        this.notAllowedMaterial = notAllowedMaterial;
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getClockWise());
    }

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


    @Override
    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity && !stacks.isEmpty()) {
            stacks.getFirst().setDamageValue(anvilBlockEntity.getDamage());
        }
        return stacks;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(pos, state);
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, player) -> new GradeAnvilMenu(syncId, inventory, ContainerLevelAccess.create(world, pos), this.notAllowedMaterial), getName()
        );
    }
}