package com.acuteterror233.mite.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;



public class AdamantiumOreBlock extends BlockWithEntity {

    protected AdamantiumOreBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(AdamantiumOreBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AdamantiumOreBlockEntity(pos, state);
    }
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof AdamantiumOreBlockEntity BlockEntity)) {
            return super.onUse(state, world, pos, player, hit);
        }
        BlockEntity.incrementClicks();
        player.sendMessage(Text.of("You've clicked the block for the " + BlockEntity.getClicks() + "th time."),true);

        return ActionResult.SUCCESS;
    }

}
