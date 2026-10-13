package com.acuteterror233.mite.mixin.server.commands;

import com.acuteterror233.mite.mixin.brigadier.CommandNodeAccessor;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collection;

/**
 * Mixin for {@code ExperienceCommand} — three adjustments to the {@code /experience} and
 * {@code /xp} command tree:
 * <ul>
 *   <li>Bare {@code /xp} (and bare {@code /experience}) defaults to querying the executor's own
 *       experience points, equivalent to running {@code /xp query @s points}.</li>
 *   <li>Permission split: querying is free for everyone, while the mutating {@code add} and
 *       {@code set} subcommands keep the gamemaster requirement.</li>
 *   <li>Points retarget: the {@code points} operations (query, add, set) act directly on
 *       {@code Player.totalExperience} — MME's authoritative experience currency — instead of
 *       the within-level progress. Derived level/progress are reconciled per tick by
 *       {@code ExperienceSynchronizer}.</li>
 * </ul>
 *
 * <p>Mechanism: both {@code dispatcher.register} calls inside {@code register} are
 * intercepted. At that point the root builders are still mutable, but their subcommand
 * builders were already built into child nodes by {@code .then(...)} — so the roots' gamemaster
 * gate is relaxed in place via {@code requires}, while the requirement and executor writes are
 * applied to the frozen child nodes through {@link CommandNodeAccessor}. The {@code xp} literal
 * is a redirect to the {@code experience} tree, so it inherits every change automatically.
 * Vanilla feedback translation keys and argument order are reused, and every tree lookup is
 * validated first — any layout mismatch throws, so a silent no-op is impossible (mirrors the
 * {@code defaultRequire=1} philosophy). Non-player sources calling the bare command receive
 * the standard "must be a player" error.</p>
 */
@Mixin(ExperienceCommand.class)
public abstract class ExperienceCommandMixin {

    /**
     * Attaches the default executor to the root command builders just before they are
     * registered (both the {@code experience} literal and the {@code xp} alias), relaxes the
     * roots' gamemaster gate, moves the gate onto the {@code add}/{@code set} subcommands, and
     * retargets the points executors onto {@code totalExperience}.
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
        builder.requires(source -> true);
        if ("experience".equals(builder.getLiteral())) {
            MME$gateMutatingSubcommands(builder);
            MME$retargetPointsExecutors(builder);
        }
        return builder.executes(ExperienceCommandMixin::querySelfPoints);
    }

    /**
     * Writes the gamemaster requirement onto the frozen {@code add} and {@code set} child nodes
     * of the {@code experience} root. Throws if the vanilla subcommand layout changes, so the
     * permission gate can never be silently skipped (mirrors the {@code defaultRequire=1}
     * fail-fast philosophy).
     */
    @Unique
    private static void MME$gateMutatingSubcommands(LiteralArgumentBuilder<CommandSourceStack> builder) {
        int gated = 0;
        for (CommandNode<CommandSourceStack> child : builder.getArguments()) {
            String name = child.getName();
            if ("add".equals(name) || "set".equals(name)) {
                ((CommandNodeAccessor<CommandSourceStack>) child).MME$setRequirement(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));
                gated++;
            }
        }
        if (gated != 2) {
            throw new IllegalStateException("MME: unexpected /experience subcommand layout, the add/set permission gate was not applied");
        }
    }

    /**
     * Swaps the executors of the points branches — {@code add <target> <amount>} plus its
     * {@code points} literal, the same pair under {@code set}, and {@code query <target> points} —
     * so they operate on {@code totalExperience} instead of the within-level progress. The
     * {@code levels} branches keep vanilla behaviour.
     */
    @Unique
    private static void MME$retargetPointsExecutors(LiteralArgumentBuilder<CommandSourceStack> builder) {
        CommandNode<CommandSourceStack> add = MME$child(builder.getArguments(), "add");
        CommandNode<CommandSourceStack> addAmount = MME$child(MME$child(add, "target"), "amount");
        CommandNode<CommandSourceStack> set = MME$child(builder.getArguments(), "set");
        CommandNode<CommandSourceStack> setAmount = MME$child(MME$child(set, "target"), "amount");
        CommandNode<CommandSourceStack> queryTarget = MME$child(MME$child(builder.getArguments(), "query"), "target");

        MME$setCommand(addAmount, ExperienceCommandMixin::mme$addPoints);
        MME$setCommand(MME$child(addAmount, "points"), ExperienceCommandMixin::mme$addPoints);
        MME$setCommand(setAmount, ExperienceCommandMixin::mme$setPoints);
        MME$setCommand(MME$child(setAmount, "points"), ExperienceCommandMixin::mme$setPoints);
        MME$setCommand(MME$child(queryTarget, "points"), ExperienceCommandMixin::mme$queryPoints);
    }

    /**
     * Replaces a node's executor via the {@code command} field accessor (private in Brigadier,
     * with no public setter).
     */
    @Unique
    private static void MME$setCommand(CommandNode<CommandSourceStack> node, Command<CommandSourceStack> command) {
        ((CommandNodeAccessor<CommandSourceStack>) node).MME$setCommand(command);
    }

    /** Looks up a root-level child node by name, failing fast when the vanilla tree differs. */
    @Unique
    private static CommandNode<CommandSourceStack> MME$child(Iterable<CommandNode<CommandSourceStack>> nodes, String name) {
        for (CommandNode<CommandSourceStack> node : nodes) {
            if (name.equals(node.getName())) {
                return node;
            }
        }
        throw new IllegalStateException("MME: unexpected /experience tree, missing root child '" + name + "'");
    }

    /** Looks up a nested child node by name, failing fast when the vanilla tree differs. */
    @Unique
    private static CommandNode<CommandSourceStack> MME$child(CommandNode<CommandSourceStack> node, String name) {
        CommandNode<CommandSourceStack> child = node.getChild(name);
        if (child == null) {
            throw new IllegalStateException("MME: unexpected /experience tree, missing child '" + name + "' under '" + node.getName() + "'");
        }
        return child;
    }

    /** Adds the amount straight onto each target's {@code totalExperience}, clamped to [0, MAX_INT]. */
    @Unique
    private static int mme$addPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "target");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayer player : players) {
            player.totalExperience = (int) Math.clamp((long) player.totalExperience + amount, 0L, (long) Integer.MAX_VALUE);
        }
        MME$sendPlayerListFeedback(context.getSource(), "add", amount, players);
        return players.size();
    }

    /** Sets each target's {@code totalExperience} to the amount (the argument is already {@code >= 0}). */
    @Unique
    private static int mme$setPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "target");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayer player : players) {
            player.totalExperience = amount;
        }
        MME$sendPlayerListFeedback(context.getSource(), "set", amount, players);
        return players.size();
    }

    /**
     * Sends the vanilla {@code commands.experience.<op>.points.success.*} feedback, matching
     * vanilla's argument order (amount first) and admin broadcast behaviour.
     */
    @Unique
    private static void MME$sendPlayerListFeedback(CommandSourceStack source, String op, int amount, Collection<ServerPlayer> players) {
        source.sendSuccess(() -> players.size() == 1
                ? Component.translatable("commands.experience." + op + ".points.success.single", amount, players.iterator().next().getDisplayName())
                : Component.translatable("commands.experience." + op + ".points.success.multiple", amount, players.size()), true);
    }

    /** Reports the target's {@code totalExperience} (lifetime accumulated points). */
    @Unique
    private static int mme$queryPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(context, "target");
        int result = target.totalExperience;
        context.getSource().sendSuccess(() -> Component.translatable("commands.experience.query.points", target.getDisplayName(), result), false);
        return result;
    }

    /**
     * Re-dispatches {@code xp query @s points} so the retargeted query implementation runs
     * unchanged for the executor itself.
     */
    @Unique
    private static int querySelfPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        CommandDispatcher<CommandSourceStack> dispatcher = source.getServer().getCommands().getDispatcher();
        ParseResults<CommandSourceStack> results = dispatcher.parse("xp query @s points", source);
        return dispatcher.execute(results);
    }
}
