package com.acuteterror233.mite.mixin.entity;

import com.acuteterror233.mite.registry.tag.AtTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    @Overwrite
    static <T extends LivingEntity & Bucketable> Optional<ActionResult> tryBucket(PlayerEntity player, Hand hand, T entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(AtTags.WATER_BUCKET) && entity.isAlive()) {
            entity.playSound(entity.getBucketFillSound(), 1.0F, 1.0F);
            System.out.println(Identifier.of(Registries.ITEM.getId(itemStack.getItem()).toString().replace("water","")).withPrefixedPath(Registries.ENTITY_TYPE.getId(entity.getType()).toString().replace("minecraft:", "")));
            ItemStack itemStack2 = new ItemStack(Registries.ITEM.get(Identifier.of(Registries.ITEM.getId(itemStack.getItem()).toString().replace("water","")).withPrefixedPath(Registries.ENTITY_TYPE.getId(entity.getType()).toString().replace("minecraft:", ""))));
            entity.copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
            player.setStackInHand(hand, itemStack3);
            World world = entity.getWorld();
            if (!world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
            }

            entity.discard();
            return Optional.of(ActionResult.SUCCESS);
        } else {
            return Optional.empty();
        }
    }
}
