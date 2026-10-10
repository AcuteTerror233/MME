package com.acuteterror233.mite.mixin.world.entity.animal.wolf;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Wolf} — Wild wolves smell meat and hunt players carrying it.
 *
 * <p>At the head of {@code registerGoals}, a {@link NonTameRandomTargetGoal} targeting
 * {@code Player} is added at the same priority (5) as the vanilla prey goal. Registered
 * first, it wins the same-priority tie, so a meat-carrying player in range outranks
 * sheep/rabbit prey — while retaliation and owner-anger targeting (priorities 1-4)
 * still outrank it. Tamed and sitting wolves are excluded by the goal itself.</p>
 *
 * <p>"Carrying meat" means any non-empty stack tagged {@code #minecraft:meat} in the
 * player's main inventory (hotbar included, armor/offhand excluded). Sight is not
 * required ({@code mustSee = false}) — wolves hunt by smell. Creative and spectator
 * players are ignored, and the hunt is disabled on peaceful difficulty.</p>
 */
@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal {
    protected WolfMixin(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    /** Registers the meat-hunt target goal before the vanilla goals. */
    @Inject(method = "registerGoals", at = @At("HEAD"))
    public void mme$registerMeatHuntGoal(CallbackInfo ci) {
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(
                (Wolf) (Object) this, Player.class, false,
                (LivingEntity target, ServerLevel level) -> this.mme$isMeatCarryingPlayer(target, level)));
    }

    /**
     * Returns whether the candidate target is a player carrying meat that this wild
     * wolf should hunt.
     */
    @Unique
    private boolean mme$isMeatCarryingPlayer(LivingEntity target, ServerLevel level) {
        if (level.getDifficulty() == Difficulty.PEACEFUL
                || !(target instanceof Player player)
                || player.isCreative()
                || player.isSpectator()) {
            return false;
        }
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (!stack.isEmpty() && stack.is(ItemTags.MEAT)) {
                return true;
            }
        }
        return false;
    }
}
