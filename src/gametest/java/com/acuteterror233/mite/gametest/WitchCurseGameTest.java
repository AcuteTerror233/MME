package com.acuteterror233.mite.gametest;

import com.acuteterror233.mite.world.effect.MMEMobEffects;
import com.acuteterror233.mite.world.effect.curse.CurseCap;
import com.acuteterror233.mite.world.effect.curse.CurseLogic;
import com.acuteterror233.mite.world.effect.curse.CurseState;
import com.acuteterror233.mite.world.effect.curse.MMECurses;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

import java.util.Set;
import java.util.UUID;

/**
 * GameTests for the witch curse system ({@code CurseLogic} + curse mixins).
 *
 * <p>The tests drive a real server-side {@link Player}: a curse is applied from a fake witch
 * and stays hidden forever (ambient, no particles, no icon), survives a vanilla
 * {@code removeAllEffects} (the milk path re-applies curses from the duck state), and diet
 * curses ban the matching food categories through {@link CurseLogic#bannedBy}.</p>
 */
public class WitchCurseGameTest {
    /** Fake inflicting witch identity shared by the tests. */
    private static final UUID WITCH_ID = UUID.nameUUIDFromBytes("gametest-witch".getBytes());

    /**
     * Lifecycle: a fresh curse is present, permanently hidden (ambient, no particles, no
     * icon); {@code removeAllCurses} clears both the effect and the duck state.
     */
    @GameTest
    public void curseLifecycleHiddenForever(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Holder<MobEffect> curse = MMECurses.CANNOT_SPRINT;

        CurseLogic.applyCurse(player, curse, WITCH_ID);
        MobEffectInstance instance = player.getEffect(curse);
        if (instance == null) {
            helper.fail("Cursed player must carry the effect instance");
            return;
        }
        if (instance.isVisible() || instance.showIcon() || !instance.isAmbient()) {
            helper.fail("A curse must stay hidden (ambient, no particles, no icon), got: " + instance);
            return;
        }

        CurseLogic.removeAllCurses(player);
        if (player.hasEffect(curse) || curseState(player).hasCurse(curse)) {
            helper.fail("removeAllCurses must clear both effect and state");
            return;
        }
        helper.succeed();
    }

    /**
     * Curses are permanent: a vanilla full effect clear (the milk path) must be undone by the
     * {@code removeAllEffects} mixin tail, which re-applies every carried curse from the state.
     */
    @GameTest
    public void curseSurvivesMilkClear(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Holder<MobEffect> curse = MMECurses.CANNOT_HOLD_BREATH;

        CurseLogic.applyCurse(player, curse, WITCH_ID);
        player.removeAllEffects();
        if (!player.hasEffect(curse) || !curseState(player).hasCurse(curse)) {
            helper.fail("Curse must survive removeAllEffects (milk cannot lift it)");
            return;
        }

        CurseLogic.removeAllCurses(player);
        player.removeAllEffects();
        if (player.hasEffect(curse) || curseState(player).hasCurse(curse)) {
            helper.fail("After removeAllCurses a milk clear must leave the player clean");
            return;
        }
        helper.succeed();
    }

    /**
     * Diet bans: CANNOT_EAT_MEAT bans animal products but not plants, CANNOT_EAT_PLANTS bans
     * plant products but not meat, and the diabetes effect (INSULIN_RESISTANCE) bans sugary
     * foods while leaving the dietary curses untouched.
     */
    @GameTest
    public void dietCursesBanMatchingFood(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack meat = new ItemStack(Items.BEEF);
        ItemStack plant = new ItemStack(Items.BREAD);

        CurseLogic.applyCurse(player, MMECurses.CANNOT_EAT_MEAT, WITCH_ID);
        if (CurseLogic.bannedBy(player, meat) != MMECurses.CANNOT_EAT_MEAT) {
            helper.fail("CANNOT_EAT_MEAT must ban beef");
            return;
        }
        if (CurseLogic.bannedBy(player, plant) != null) {
            helper.fail("CANNOT_EAT_MEAT must not ban bread");
            return;
        }

        CurseLogic.applyCurse(player, MMECurses.CANNOT_EAT_PLANTS, WITCH_ID);
        if (CurseLogic.bannedBy(player, plant) != MMECurses.CANNOT_EAT_PLANTS) {
            helper.fail("CANNOT_EAT_PLANTS must ban bread");
            return;
        }
        if (CurseLogic.bannedBy(player, meat) != MMECurses.CANNOT_EAT_MEAT) {
            helper.fail("CANNOT_EAT_PLANTS must not re-ban beef as plants");
            return;
        }

        // INSULIN_RESISTANCE is a plain timed effect (not a witch curse) and bans sugary foods.
        ItemStack sugar = new ItemStack(Items.SUGAR);
        player.addEffect(new MobEffectInstance(MMEMobEffects.INSULIN_RESISTANCE, 200));
        if (CurseLogic.bannedBy(player, sugar) != MMEMobEffects.INSULIN_RESISTANCE) {
            helper.fail("INSULIN_RESISTANCE must ban sugar");
            return;
        }
        if (CurseLogic.bannedBy(player, plant) != MMECurses.CANNOT_EAT_PLANTS) {
            helper.fail("INSULIN_RESISTANCE must not re-ban bread as sugary");
            return;
        }
        helper.succeed();
    }

    /**
     * One curse at a time: a witch cannot curse a player who already carries a curse, even
     * a witch that has never cursed this player before; the original curse stays intact.
     */
    @GameTest
    public void witchCannotStackCurses(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Witch firstWitch = helper.spawn(EntityTypes.WITCH, new BlockPos(1, 1, 1));
        Witch secondWitch = helper.spawn(EntityTypes.WITCH, new BlockPos(2, 1, 1));

        CurseLogic.applyCurseFromWitch(firstWitch, player);
        Set<Holder<MobEffect>> carried = curseState(player).curses();
        if (carried.size() != 1) {
            helper.fail("A witch must inflict exactly one curse, got: " + carried.size());
            return;
        }
        Holder<MobEffect> original = carried.iterator().next();

        CurseLogic.applyCurseFromWitch(secondWitch, player);
        CurseState state = curseState(player);
        if (state.curses().size() != 1 || !state.hasCurse(original)) {
            helper.fail("An already-cursed player must be immune to further witches");
            return;
        }
        helper.succeed();
    }

    /** @return the curse duck state of the test player. */
    private static CurseState curseState(Player player) {
        return ((CurseCap) player).mme$curseState();
    }
}
