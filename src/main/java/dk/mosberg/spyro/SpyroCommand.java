package dk.mosberg.spyro;

import com.mojang.brigadier.CommandDispatcher;
import dk.mosberg.Spyro;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SpyroCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> registerCommands(dispatcher));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("spyro").executes(context -> {
            ServerCommandSource source = context.getSource();
            var player = source.getPlayerOrThrow();
            boolean enabled = player.getCommandTags().contains(Spyro.SPYRO_TAG);
            setSpyroState(source, player, !enabled);
            return 1;
        }).then(CommandManager.literal("on").executes(context -> {
            ServerCommandSource source = context.getSource();
            var player = source.getPlayerOrThrow();
            setSpyroState(source, player, true);
            return 1;
        })).then(CommandManager.literal("off").executes(context -> {
            ServerCommandSource source = context.getSource();
            var player = source.getPlayerOrThrow();
            setSpyroState(source, player, false);
            return 1;
        })).then(CommandManager.literal("toggle").executes(context -> {
            ServerCommandSource source = context.getSource();
            var player = source.getPlayerOrThrow();
            boolean enabled = player.getCommandTags().contains(Spyro.SPYRO_TAG);
            setSpyroState(source, player, !enabled);
            return 1;
        })));
    }

    private static void setSpyroState(ServerCommandSource source,
            net.minecraft.server.network.ServerPlayerEntity player, boolean enabled) {
        if (enabled) {
            player.addCommandTag(Spyro.SPYRO_TAG);
            source.sendFeedback(() -> Text.literal("Spyro mode enabled."), false);
        } else {
            player.removeCommandTag(Spyro.SPYRO_TAG);
            source.sendFeedback(() -> Text.literal("Spyro mode disabled."), false);
        }
    }
}
