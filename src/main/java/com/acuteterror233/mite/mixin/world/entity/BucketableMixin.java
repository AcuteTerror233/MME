package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    /**
     * @author AcuteTerror233
     * @reason 修改了生物桶的放置逻辑
     */
    @Overwrite
    static <T extends LivingEntity & Bucketable> Optional<InteractionResult> bucketMobPickup(Player player, InteractionHand hand, T entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(MMETags.WATER_BUCKET) && entity.isAlive()) {
            entity.playSound(entity.getPickupSound(), 1.0F, 1.0F);
            ItemStack itemStack2 = new ItemStack(BuiltInRegistries.ITEM.getValue(ResourceLocation.parse(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString().replace("water", "")).withPrefix(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString().replace("minecraft:", ""))));
            entity.saveToBucketTag(itemStack2);
            ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
            player.setItemInHand(hand, itemStack3);
            Level world = entity.level();
            if (!world.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemStack2);
            }

            entity.discard();
            return Optional.of(InteractionResult.SUCCESS);
        } else {
            return Optional.empty();
        }
    }
}
