package com.acuteterror233.mite.world.effect.curse;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import java.util.List;

/**
 * Skeleton registry for the witch curse system (source: mcmod item 35669).
 * Witches inflict one random curse on the player they have spotted; the curse is unknown
 * until its symptoms are first experienced and never fades on its own — it is only lifted
 * by killing the witch or drinking a curse-removing potion.
 * Every curse registers one placeholder {@link CurseMobEffect}; the actual gameplay logic
 * (durability doubling, breath cap, sprint/dietary bans, fear penalties, etc.) is not
 * implemented yet.
 */
public class MMECurses {
    /** Armour, weapon and tool durability is consumed twice as fast. */
    public static final Holder<MobEffect> CORROSIVE_SKIN = register("corrosive_skin", 0x6B8E23);

    /** Underwater air supply is capped at 30% of the normal maximum. */
    public static final Holder<MobEffect> CANNOT_HOLD_BREATH = register("cannot_hold_breath", 0x1E6B8C);

    /** The player cannot sprint. */
    public static final Holder<MobEffect> CANNOT_SPRINT = register("cannot_sprint", 0x8B4513);

    /** The player cannot eat any animal products. */
    public static final Holder<MobEffect> CANNOT_EAT_MEAT = register("cannot_eat_meat", 0xB03060);

    /** The player cannot eat any plant products. */
    public static final Holder<MobEffect> CANNOT_EAT_PLANTS = register("cannot_eat_plants", 0x556B2F);

    /** The player cannot consume any soup or stew (slurp-sound drinkable foods). */
    public static final Holder<MobEffect> CANNOT_DRINK_SOUP = register("cannot_drink_soup", 0xDAA520);

    /** Endermen attack the cursed player unprovoked. */
    public static final Holder<MobEffect> ENDER_HATRED = register("ender_hatred", 0x9370DB);

    /** Crafting bonus treats the player's level as 20 lower, worsening crafted tool quality. */
    public static final Holder<MobEffect> DIMINISHED_INTELLECT = register("diminished_intellect", 0x708090);

    /** Moving through plant blocks (vines, grass, ...) is heavily slowed. */
    public static final Holder<MobEffect> PLANT_FEAR = register("plant_fear", 0x2E8B57);

    /** The player cannot wear armour; equipped pieces are forcibly dropped. */
    public static final Holder<MobEffect> ARMOR_REJECTION = register("armor_rejection", 0x71797E);

    /** The player cannot open or use chests. */
    public static final Holder<MobEffect> CHEST_FEAR = register("chest_fear", 0x8B6C42);

    /** The player cannot fall asleep at night (beds still set spawn). */
    public static final Holder<MobEffect> SLEEPLESSNESS = register("sleeplessness", 0x191970);

    /** Only 1/4 of attacks actually land against spiders (phase/demon/cave/black widow included). */
    public static final Holder<MobEffect> SPIDER_FEAR = register("spider_fear", 0x2F2F2F);

    /** Only 1/4 of attacks actually land against wolves and dread wolves. */
    public static final Holder<MobEffect> WOLF_FEAR = register("wolf_fear", 0x696969);

    /** Only 1/4 of attacks actually land against creepers and infernal creepers. */
    public static final Holder<MobEffect> CREEPER_FEAR = register("creeper_fear", 0x3CB371);

    /** Only 1/4 of attacks actually land against undead (ghouls excepted). */
    public static final Holder<MobEffect> UNDEAD_FEAR = register("undead_fear", 0x5F6B6B);

    /** All curses in registration order; used as the random infliction pool. */
    public static final List<Holder<MobEffect>> ALL = List.of(
            CORROSIVE_SKIN, CANNOT_HOLD_BREATH, CANNOT_SPRINT, CANNOT_EAT_MEAT, CANNOT_EAT_PLANTS,
            CANNOT_DRINK_SOUP, ENDER_HATRED, DIMINISHED_INTELLECT, PLANT_FEAR, ARMOR_REJECTION,
            CHEST_FEAR, SLEEPLESSNESS, SPIDER_FEAR, WOLF_FEAR, CREEPER_FEAR, UNDEAD_FEAR);

    private static Holder<MobEffect> register(String id, int color) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), new CurseMobEffect(color));
    }

    /** No-op classloading hook that triggers static registration. */
    public static void init() {

    }
}
