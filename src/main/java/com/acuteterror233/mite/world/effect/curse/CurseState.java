package com.acuteterror233.mite.world.effect.curse;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.*;

/**
 * Mutable per-player witch-curse bookkeeping, exposed through the {@link CurseCap} duck interface
 * on {@code Player} and persisted with the player's save data (keys {@code mme:Curses},
 * {@code Id}, {@code Witch}). It records which curse the player carries and which witch
 * inflicted it; the "at most one curse" rule is enforced by
 * {@link CurseLogic#applyCurseFromWitch}, not by this map, which would accept several entries.
 * Curses stay hidden forever — no particles, no icon, no reveal — and survive death through
 * {@link #copyFrom}.
 */
public class CurseState {
    private final Map<Holder<MobEffect>, UUID> witches = new LinkedHashMap<>();

    /** @return whether the player carries the given curse. */
    public boolean hasCurse(Holder<MobEffect> curse) {
        return witches.containsKey(curse);
    }

    /** Records a curse and its inflicting witch, replacing any previous entry for the same curse. */
    public void addCurse(Holder<MobEffect> curse, UUID witchId) {
        witches.put(curse, witchId);
    }

    /** Removes one curse. */
    public void remove(Holder<MobEffect> curse) {
        witches.remove(curse);
    }

    /** Removes every curse. */
    public void clear() {
        witches.clear();
    }

    /** @return an unmodifiable view of all carried curses. */
    public Set<Holder<MobEffect>> curses() {
        return Collections.unmodifiableSet(witches.keySet());
    }

    /** @return the UUID of the witch that inflicted the given curse, or null. */
    public UUID witchOf(Holder<MobEffect> curse) {
        return witches.get(curse);
    }

    /** Copies every curse from {@code other} (used to carry curses across respawns). */
    public void copyFrom(CurseState other) {
        witches.clear();
        witches.putAll(other.witches);
    }

    /** Persists the state into the player's save data. */
    public void save(ValueOutput output) {
        if (witches.isEmpty()) {
            return;
        }
        ValueOutput.ValueOutputList list = output.childrenList("mme:Curses");
        for (Map.Entry<Holder<MobEffect>, UUID> entry : witches.entrySet()) {
            ValueOutput child = list.addChild();
            child.putString("Id", BuiltInRegistries.MOB_EFFECT.getKey(entry.getKey().value()).toString());
            child.putString("Witch", entry.getValue().toString());
        }
    }

    /** Restores the state from the player's save data. */
    public void load(ValueInput input) {
        witches.clear();
        for (ValueInput child : input.childrenListOrEmpty("mme:Curses")) {
            Optional<String> id = child.getString("Id");
            Optional<String> witch = child.getString("Witch");
            if (id.isEmpty() || witch.isEmpty()) {
                continue;
            }
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.getValue(Identifier.parse(id.get()));
            if (effect == null) {
                continue;
            }
            try {
                addCurse(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), UUID.fromString(witch.get()));
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}
