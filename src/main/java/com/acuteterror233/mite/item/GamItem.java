package com.acuteterror233.mite.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Experience-yielding gem item, extends {@link Item}.
 * Right-clicking a gem (diamond, emerald, amethyst shard, echo shard, ...) grants the configured
 * experience points and consumes the item. Applied to vanilla gems via {@code VanillaItemModify}.
 */
public class GamItem extends Item {
    private final int experience;
    /**
     * @param settings   item properties
     * @param experience experience points granted per use
     */
    public GamItem(Properties settings, int experience) {
        super(settings);
        this.experience = experience;
    }
    /** Right-click: grants the configured experience points and consumes one item. */
    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {
        user.giveExperiencePoints(this.experience);
        user.getItemInHand(hand).consume(1, user);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, user.getSoundSource(), 1.0F, 1.0F);
        return InteractionResult.CONSUME;
    }
    /** Adds the XP value of the gem to the tooltip. */
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(Component.translatable("mme.coins.tooltip", this.experience));
    }
}
