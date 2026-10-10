package com.acuteterror233.mite.world.effect.curse;

import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.entity.monster.Ghoul;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Static gameplay logic for the witch curse system. Curse presence checks use the synced
 * {@link MobEffect} (works on both sides); the per-player bookkeeping
 * ({@link CurseState}) is authoritative for persistence, witch attribution and reveal state.
 */
public final class CurseLogic {
    /** Chance that an attack under a fear curse actually lands. */
    public static final float FEAR_LAND_CHANCE = 0.25F;
    /** Fraction of the normal air supply usable underwater. */
    public static final float BREATH_CAP_FRACTION = 0.3F;
    /** Movement speed factor inside plant blocks. */
    public static final float PLANT_SLOW = 0.25F;
    /** Effective experience levels removed from crafting quality while DIMINISHED_INTELLECT is active. */
    public static final int DIMINISHED_LEVEL_PENALTY = 20;

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private CurseLogic() {
    }

    /** @return whether the player currently carries the given curse. */
    public static boolean hasCurse(Player player, Holder<MobEffect> curse) {
        return player.hasEffect(curse);
    }

    /**
     * Called when a witch spots a player: inflicts one random curse from the pool the player
     * does not carry yet. Each witch can curse a given player at most once; creative and
     * spectator players are immune. The curse stays hidden until its symptoms appear.
     */
    public static void applyCurseFromWitch(Witch witch, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || serverPlayer.isCreative() || serverPlayer.isSpectator()) {
            return;
        }
        CurseState state = ((CurseCap) player).mme$curseState();
        if (state.hasWitch(witch.getUUID())) {
            return;
        }
        List<Holder<MobEffect>> pool = MMECurses.ALL.stream().filter(curse -> !state.hasCurse(curse)).toList();
        if (pool.isEmpty()) {
            return;
        }
        Holder<MobEffect> curse = pool.get(witch.getRandom().nextInt(pool.size()));
        applyCurse(player, curse, witch.getUUID());
        serverPlayer.sendOverlayMessage(Component.translatable("mme.curse.afflicted"));
    }

    /** Records and applies a curse as a permanent hidden effect (witch path and GameTests). */
    public static void applyCurse(Player player, Holder<MobEffect> curse, UUID witchId) {
        ((CurseCap) player).mme$curseState().addCurse(curse, witchId);
        if (!player.hasEffect(curse)) {
            player.addEffect(hiddenInstance(curse));
        }
    }

    /** Lifts every curse from the player (purifying bottle). */
    public static void removeAllCurses(Player player) {
        if (!(player instanceof CurseCap cap)) {
            return;
        }
        CurseState state = cap.mme$curseState();
        if (state.curses().isEmpty()) {
            return;
        }
        for (Holder<MobEffect> curse : List.copyOf(state.curses())) {
            player.removeEffect(curse);
        }
        state.clear();
        if (!player.level().isClientSide()) {
            player.sendSystemMessage(Component.translatable("mme.curse.lifted_bottle"));
        }
    }

    /** Lifts every curse that was inflicted by the dying witch. */
    public static void onWitchDeath(Witch witch) {
        if (!(witch.level() instanceof ServerLevel level)) {
            return;
        }
        UUID witchId = witch.getUUID();
        for (ServerPlayer player : level.players()) {
            CurseState state = ((CurseCap) player).mme$curseState();
            for (Holder<MobEffect> curse : List.copyOf(state.curses())) {
                if (witchId.equals(state.witchOf(curse))) {
                    state.remove(curse);
                    player.removeEffect(curse);
                }
            }
        }
    }

    /** @return the dietary curse banning the given food item, or null if allowed. */
    public static Holder<MobEffect> bannedBy(Player player, ItemStack stack) {
        if (player.hasEffect(MMECurses.CANNOT_EAT_MEAT) && stack.is(MMEItemTags.ANIMAL_PRODUCTS)) {
            return MMECurses.CANNOT_EAT_MEAT;
        }
        if (player.hasEffect(MMECurses.CANNOT_EAT_PLANTS) && stack.is(MMEItemTags.PLANT_PRODUCTS)) {
            return MMECurses.CANNOT_EAT_PLANTS;
        }
        if (player.hasEffect(MMECurses.CANNOT_DRINK_SOUP) && stack.is(MMEItemTags.SOUPS)) {
            return MMECurses.CANNOT_DRINK_SOUP;
        }
        if (player.hasEffect(MMEMobEffects.INSULIN_RESISTANCE) && stack.is(MMEItemTags.SUGARY_FOODS)) {
            return MMEMobEffects.INSULIN_RESISTANCE;
        }
        return null;
    }

    /** @return the fear curse triggered by attacking the given creature, or null. */
    public static Holder<MobEffect> fearedBy(Player player, LivingEntity target) {
        if (player.hasEffect(MMECurses.SPIDER_FEAR) && target instanceof Spider) {
            return MMECurses.SPIDER_FEAR;
        }
        if (player.hasEffect(MMECurses.WOLF_FEAR) && target instanceof Wolf) {
            return MMECurses.WOLF_FEAR;
        }
        if (player.hasEffect(MMECurses.CREEPER_FEAR) && target instanceof Creeper) {
            return MMECurses.CREEPER_FEAR;
        }
        if (player.hasEffect(MMECurses.UNDEAD_FEAR) && !(target instanceof Ghoul)
                && target.getType().builtInRegistryHolder().is(EntityTypeTags.UNDEAD)) {
            return MMECurses.UNDEAD_FEAR;
        }
        return null;
    }

    /** Force-drops every worn armour piece (armour rejection curse). */
    public static void dropAllArmor(ServerPlayer player) {
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                player.setItemSlot(slot, ItemStack.EMPTY);
                ItemEntity drop = new ItemEntity(player.level(), player.getX(), player.getY() + 0.5, player.getZ(), stack);
                drop.setPickUpDelay(40);
                player.level().addFreshEntity(drop);
            }
        }
    }

    /**
     * @return a permanent, fully hidden instance of the curse — infinite duration, ambient,
     * no particles, no icon; it never becomes visible.
     */
    public static MobEffectInstance hiddenInstance(Holder<MobEffect> curse) {
        return new MobEffectInstance(curse, -1, 0, true, false, false);
    }
}
