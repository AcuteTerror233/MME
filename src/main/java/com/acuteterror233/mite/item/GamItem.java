package com.acuteterror233.mite.item;

import com.acuteterror233.mite.component.AtDataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GamItem extends Item {
    public GamItem(Settings settings, int experience) {
        super(settings.component(AtDataComponentTypes.EXPERIENCE, experience));
    }
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Integer i = user.getStackInHand(hand).get(AtDataComponentTypes.EXPERIENCE);
        if (i != null) {
            user.addExperience(i);
            user.getStackInHand(hand).decrementUnlessCreative(1, user);
            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, user.getSoundCategory(), 1.0F, 1.0F);
            return ActionResult.CONSUME;
        }
        return super.use(world, user, hand);
    }
}
