package com.acuteterror233.mite.block;

import com.acuteterror233.mite.block.entity.AnvilBlockEntity;
import com.acuteterror233.mite.screen.AtAnvilScreenHandler;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 自定义铁砧方块：支持保留与传递方块实体中的耐久数据，
 * 在掉落为实体或破坏掉落时同步当前损伤；放置时依据物品耐久初始化。
 */
public class AtAnvilBlock extends AnvilBlock implements BlockEntityProvider {
    private final TagKey<Item> notAllowedMaterial;
    public AtAnvilBlock(Settings settings, TagKey<Item> notAllowedMaterial) {
        super(settings);
        this.notAllowedMaterial = notAllowedMaterial;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().rotateYClockwise());
    }

    @Override
    // 放置时：用物品耐久初始化方块实体，并额外+1 点损伤避免满新放置
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity) {
            anvilBlockEntity.setMaxDamage(itemStack.getMaxDamage());
            anvilBlockEntity.setDamage(itemStack.getDamage());
            anvilBlockEntity.addDamage(1);
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
                (syncId, inventory, player) -> new AtAnvilScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos), this.notAllowedMaterial), getName()
        );
    }
}