package com.acuteterror233.mite.block;

import com.acuteterror233.mite.atinterface.AbstractBlockSettingsExtension;
import com.acuteterror233.mite.atinterface.FallingBlockEntityExtension;
import com.acuteterror233.mite.registry.tag.AtTags;
import com.acuteterror233.mite.screen.AtAnvilScreenHandler;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AtAnvilBlock extends AnvilBlock implements BlockEntityProvider {
    public AtAnvilBlock(Settings settings) {
        super(settings);
    }
    
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().rotateYClockwise());
    }
    
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.setMaxDamage(itemStack.getMaxDamage());
            anvilBlockEntity.setDamage(itemStack.getDamage()+1);
        }
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        List<ItemStack> stacks = super.getDroppedStacks(state, builder);
        BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity && !stacks.isEmpty()) {
            stacks.getFirst().setDamage(anvilBlockEntity.getDamage());
        }
        return stacks;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(pos, state);
    }
    
    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new AtAnvilScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), getDefaultState().getBlock().getName()
        );
    }
    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
            FallingBlockEntity fallingBlockEntity = spawnFromBlock(world, pos, state,world.getBlockEntity(pos));
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }
    public static FallingBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        if (blockEntity instanceof AnvilBlockEntity AnvilBlockEntity) {
            ((FallingBlockEntityExtension)fallingBlockEntity).setDamage(AnvilBlockEntity.getDamage(), AnvilBlockEntity.getMaxDamage());
        }
        return fallingBlockEntity;
    }
}