package com.acuteterror233.mite.mixin.world.entity;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Bucketable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

/**
 * Mixin for {@code Bucketable} — Generalizes mob bucket pickup to modded buckets.
 *
 * <p>Pickup succeeds with any item tagged {@code WATER_BUCKET}; the filled result is
 * resolved by renaming the used bucket's item ID from {@code [water_]bucket} to
 * {@code <mob>_bucket} (see {@link #getBucketItemStack}).</p>
 */
@Mixin(Bucketable.class)
public interface BucketableMixin {

    /**
     * @author AcuteTerror233
     * @reason Modified the mob bucket placement logic
     */
    @Overwrite
    static <T extends LivingEntity & Bucketable> Optional<InteractionResult> bucketMobPickup(Player player, InteractionHand hand, T entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (entity.canBePickedUpWithBucket(itemStack) && entity.isAlive()) {
            entity.getBucketItemStack();
            entity.playSound(entity.getPickupSound(), 1.0F, 1.0F);
            ItemStack itemStack2 = getBucketItemStack(player, hand, entity);
            entity.saveToBucketTag(itemStack2);
            ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
            player.setItemInHand(hand, itemStack3);
            Level world = entity.level();
            if (!world.isClientSide()) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemStack2);
            }

            entity.discard();
            return Optional.of(InteractionResult.SUCCESS);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Only buckets tagged as water buckets can scoop up a mob.
     */
    @Overwrite
    default boolean canBePickedUpWithBucket(final ItemStack itemStack) {
        return itemStack.is(MMEItemTags.WATER_BUCKET);
    }

    /**
     * Builds the filled mob bucket by mapping the used bucket's item ID:
     * {@code [water_]bucket} is replaced with {@code <entity>_bucket} in the same namespace.
     */
    @Unique
    private static <T extends LivingEntity & Bucketable> ItemStack getBucketItemStack(Player player, InteractionHand hand, T entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        Identifier identifier = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        String s = "_";
        String s1 = "";
        // s is the separator consumed for generic buckets; s1 is the "water" prefix to strip
        if (itemStack.is(MMEItemTags.BUCKET)) {
            s = "";
        }
        if (itemStack.is(MMEItemTags.WATER_BUCKET)) {
            s1 = "water";
        }
        Item item = BuiltInRegistries.ITEM.getValue(
                Identifier.fromNamespaceAndPath(
                        identifier.getNamespace(),
                        identifier.getPath().replace(s1 + s + "bucket", BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath() + "_bucket")
                        )
        );
        return new ItemStack(item);
    }
}
