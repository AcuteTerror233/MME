package com.acuteterror233.mite.mixin.world.entity.player;

import com.acuteterror233.mite.registry.tag.MMEBlockTags;
import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.CurseState;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for {@code Player} — witch-curse host state and symptom handling.
 *
 * <p>Implements the {@link CurseCap} duck interface with a persisted {@link CurseState}
 * (saved with {@code addAdditionalSaveData}/{@code readAdditionalSaveData}). The tail of
 * {@code tick} re-applies effect instances cleared by vanilla (curses are permanent and
 * stay hidden forever) and force-drops armour for ARMOR_REJECTION carriers. The head of
 * {@code attack} makes fear curses (spider/wolf/creeper/undead) miss 3/4 of swings, and
 * the tail of {@code getBlockSpeedFactor}
 * slows PLANT_FEAR carriers moving through plant blocks.</p>
 */
@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar implements CurseCap {
    @Unique
    private final CurseState mme$curseState = new CurseState();

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public CurseState mme$curseState() {
        return this.mme$curseState;
    }

    /** Persists the curse state with the player. */
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void mme$saveCurses(ValueOutput output, CallbackInfo ci) {
        this.mme$curseState.save(output);
    }

    /** Restores the curse state with the player. */
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void mme$loadCurses(ValueInput input, CallbackInfo ci) {
        this.mme$curseState.load(input);
    }

    /**
     * Server-side housekeeping: keeps effect instances in sync with the state (curses are
     * permanent, so anything vanilla clears is re-applied) and force-drops armour for
     * ARMOR_REJECTION carriers.
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void mme$curseTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        for (Holder<MobEffect> curse : List.copyOf(this.mme$curseState.curses())) {
            if (!self.hasEffect(curse)) {
                self.addEffect(CurseLogic.hiddenInstance(curse));
            }
        }
        if (self.hasEffect(MMECurses.ARMOR_REJECTION) && self instanceof ServerPlayer serverPlayer) {
            CurseLogic.dropAllArmor(serverPlayer);
        }
    }

    /** Fear curses make 3/4 of attacks against the feared creature miss entirely. */
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void mme$fearMiss(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity living) {
            Player self = (Player) (Object) this;
            Holder<MobEffect> fear = CurseLogic.fearedBy(self, living);
            if (fear != null && getRandom().nextFloat() >= CurseLogic.FEAR_LAND_CHANCE) {
                ci.cancel();
            }
        }
    }

    /** Slows movement through plant blocks for PLANT_FEAR carriers. */
    @Inject(method = "getBlockSpeedFactor", at = @At("TAIL"), cancellable = true)
    private void mme$plantFearSlow(CallbackInfoReturnable<Float> cir) {
        if (hasEffect(MMECurses.PLANT_FEAR) && level().getBlockState(blockPosition()).is(MMEBlockTags.PLANT_BLOCKS)) {
            cir.setReturnValue(Math.min(cir.getReturnValue(), CurseLogic.PLANT_SLOW));
        }
    }
}
