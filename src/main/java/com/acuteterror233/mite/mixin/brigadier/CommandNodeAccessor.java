package com.acuteterror233.mite.mixin.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.CommandNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

/**
 * Accessor mixin for Brigadier's {@link CommandNode} — allows patching already-built command
 * nodes in place. Used to re-gate individual vanilla subcommands (e.g. the {@code add}/{@code set}
 * branches of {@code /experience}) and to swap their executors after the builders have been
 * built into frozen child nodes.
 *
 * <p>Brigadier is shipped unobfuscated, so the field names are stable across game versions.</p>
 *
 * @param <S> the command source type
 */
@Mixin(CommandNode.class)
public interface CommandNodeAccessor<S> {

    /**
     * Replaces the node's permission predicate. The field is {@code final} in vanilla, so the
     * setter needs {@code @Mutable}.
     *
     * @param requirement the new permission predicate
     */
    @Accessor("requirement")
    @Mutable
    void MME$setRequirement(Predicate<S> requirement);

    /**
     * Replaces the node's executor. The field is non-final in Brigadier but private with no
     * public setter, so a plain accessor is enough.
     *
     * @param command the new executor
     */
    @Accessor("command")
    void MME$setCommand(Command<S> command);
}
