package com.acuteterror233.mite.mixin.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Mixin for {@code ExperienceCommand} — Makes bare {@code /xp} (and bare
 * {@code /experience}) default to querying the executor's own experience points,
 * equivalent to running {@code /xp query @s points}.
 *
 * <p>Mechanism: both {@code dispatcher.register} calls inside {@code register} are
 * intercepted and a default executor is attached to the root literal. On the {@code xp}
 * alias node the executor only fires when no subcommand follows (the node's redirect
 * still routes every real argument to {@code experience}); the executor re-dispatches
 * {@code xp query @s points} through the server dispatcher, so vanilla's query path,
 * feedback message and return value are reused unchanged. Permission behavior is
 * untouched — the command stays gamemaster-only, and non-player sources receive the
 * standard "must be a player" error.</p>
 */
@Mixin(ExperienceCommand.class)
public abstract class ExperienceCommandMixin {

    /**
     * Attaches the default executor to the root command builders just before they are
     * registered (both the {@code experience} literal and the {@code xp} alias).
     */
    @ModifyArg(
            method = "register",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/CommandDispatcher;register(Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;)Lcom/mojang/brigadier/tree/LiteralCommandNode;"
            )
    )
    private static LiteralArgumentBuilder<CommandSourceStack> register(
            LiteralArgumentBuilder<CommandSourceStack> builder) {
        return builder.executes(ExperienceCommandMixin::querySelfPoints);
    }

    /**
     * Re-dispatches {@code xp query @s points} so the vanilla query implementation runs
     * unchanged for the executor itself.
     */
    @Unique
    private static int querySelfPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer self = source.getPlayerOrException();
        CommandDispatcher<CommandSourceStack> dispatcher = source.getServer().getCommands().getDispatcher();
        ParseResults<CommandSourceStack> results = dispatcher.parse("xp query @s points", source);
        return dispatcher.execute(results);
    }
}
