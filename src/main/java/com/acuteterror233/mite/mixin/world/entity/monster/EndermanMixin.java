package com.acuteterror233.mite.mixin.world.entity.monster;

import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for {@code Enderman} — Endermen sense their pearls and hunt players carrying them.
 *
 * <p>At the head of {@code registerGoals}, a {@link NearestAttackableTargetGoal} targeting
 * {@code Player} is added at the same priority (1) as the vanilla stare-based
 * {@code EndermanLookForPlayerGoal}. Registered first, it wins the same-priority tie, so a
 * pearl-carrying player in range is attacked without being stared at — while the vanilla
 * stare mechanic still works against players without pearls, and retaliation plus anger
 * reset keep their vanilla semantics.</p>
 *
 * <p>"Carrying pearls" means any non-empty stack of {@link Items#ENDER_PEARL} in the
 * player's main inventory (hotbar included, armor/offhand excluded). Sight is not
 * required ({@code mustSee = false}) — pearls resonate with their otherworldly origin.
 * Creative and spectator players are ignored, and the hunt is disabled on peaceful
 * difficulty.</p>
 */
@Mixin(Enderman.class)
public abstract class EndermanMixin extends Monster {
    protected EndermanMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    /** Registers the pearl-hunt target goal before the vanilla goals. */
    @Inject(method = "registerGoals", at = @At("HEAD"))
    public void mme$registerPearlHuntGoal(CallbackInfo ci) {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                (Enderman) (Object) this, Player.class, false,
                this::mme$isPearlCarryingPlayer));
    }

    /** Registers the curse-hatred target goal: ENDER_HATRED carriers are attacked unprovoked. */
    @Inject(method = "registerGoals", at = @At("HEAD"))
    public void mme$registerCurseHatredGoal(CallbackInfo ci) {
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
                (Enderman) (Object) this, Player.class, false,
                (LivingEntity target, ServerLevel level) -> target.hasEffect(MMECurses.ENDER_HATRED)));
    }

    /**
     * Returns whether the candidate target is a player carrying ender pearls that this
     * enderman should hunt.
     */
    @Unique
    private boolean mme$isPearlCarryingPlayer(LivingEntity target, ServerLevel level) {
        if (level.getDifficulty() == Difficulty.PEACEFUL
                || !(target instanceof Player player)
                || player.isCreative()
                || player.isSpectator()) {
            return false;
        }
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (!stack.isEmpty() && stack.is(Items.ENDER_PEARL)) {
                return true;
            }
        }
        return false;
    }
}
