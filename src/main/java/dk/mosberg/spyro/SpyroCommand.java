package dk.mosberg.spyro;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dk.mosberg.Spyro;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
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
        })).then(CommandManager.literal("reload").executes(context -> {
            SpyroConfig.load();
            context.getSource().sendFeedback(() -> Text.literal("Spyro config reloaded."), false);
            return 1;
        })).then(
                CommandManager
                        .literal(
                                "give")
                        .then(CommandManager.literal("gems")
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer(1))
                                        .executes(context -> {
                                            var player = context.getSource().getPlayerOrThrow();
                                            return giveCollectible(context.getSource(), player,
                                                    SpyroCollectibles.GEMS_OBJECTIVE_ID,
                                                    IntegerArgumentType.getInteger(context,
                                                            "amount"));
                                        })))
                        .then(CommandManager.literal("talismans")
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer(1))
                                        .executes(context -> {
                                            var player = context.getSource().getPlayerOrThrow();
                                            return giveCollectible(context.getSource(), player,
                                                    SpyroCollectibles.TALISMANS_OBJECTIVE_ID,
                                                    IntegerArgumentType.getInteger(context,
                                                            "amount"));
                                        })))
                        .then(CommandManager.literal("orbs")
                                .then(CommandManager
                                        .argument("amount", IntegerArgumentType.integer(1))
                                        .executes(context -> {
                                            var player = context.getSource().getPlayerOrThrow();
                                            return giveCollectible(context.getSource(), player,
                                                    SpyroCollectibles.ORBS_OBJECTIVE_ID,
                                                    IntegerArgumentType.getInteger(context,
                                                            "amount"));
                                        }))))
                .then(CommandManager.literal("unlock")
                        .then(CommandManager.literal("fire").executes(context -> {
                            var player = context.getSource().getPlayerOrThrow();
                            return unlockAbility(context.getSource(), player, "fire");
                        })).then(CommandManager.literal("charge").executes(context -> {
                            var player = context.getSource().getPlayerOrThrow();
                            return unlockAbility(context.getSource(), player, "charge");
                        })).then(CommandManager.literal("glide").executes(context -> {
                            var player = context.getSource().getPlayerOrThrow();
                            return unlockAbility(context.getSource(), player, "glide");
                        })))
                .then(CommandManager.literal("reset").executes(context -> {
                    ServerCommandSource source = context.getSource();
                    var player = source.getPlayerOrThrow();
                    SpyroPlayerStats stats = SpyroStatsAttachment.get(player);
                    stats.resetToDefaults();
                    clearScores(player);
                    source.sendFeedback(() -> Text.literal("Spyro progress reset."), false);
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

    private static int giveCollectible(ServerCommandSource source,
            net.minecraft.server.network.ServerPlayerEntity player, String objectiveId,
            int amount) {
        SpyroCollectibles.addCollectible(player, objectiveId, amount);
        source.sendFeedback(() -> Text.literal("Added " + amount + " to " + objectiveId + "."),
                false);
        return 1;
    }

    private static int unlockAbility(ServerCommandSource source,
            net.minecraft.server.network.ServerPlayerEntity player, String ability) {
        SpyroPlayerStats stats = SpyroStatsAttachment.get(player);
        SpyroAbilityUnlocks unlocks = stats.getAbilityUnlocks();
        switch (ability) {
            case "fire" -> unlocks.unlockFireBreath();
            case "charge" -> unlocks.unlockCharge();
            case "glide" -> unlocks.unlockGlide();
            default -> {
                return 0;
            }
        }
        source.sendFeedback(() -> Text.literal("Unlocked Spyro ability: " + ability + "."), false);
        return 1;
    }

    private static void clearScores(net.minecraft.server.network.ServerPlayerEntity player) {
        ServerWorld world = (ServerWorld) player.getEntityWorld();
        var server = world.getServer();
        if (server == null) {
            return;
        }
        var scoreboard = server.getScoreboard();
        var gems = scoreboard.getNullableObjective(SpyroCollectibles.GEMS_OBJECTIVE_ID);
        var talismans = scoreboard.getNullableObjective(SpyroCollectibles.TALISMANS_OBJECTIVE_ID);
        var orbs = scoreboard.getNullableObjective(SpyroCollectibles.ORBS_OBJECTIVE_ID);
        if (gems != null) {
            scoreboard.getOrCreateScore(player, gems).setScore(0);
        }
        if (talismans != null) {
            scoreboard.getOrCreateScore(player, talismans).setScore(0);
        }
        if (orbs != null) {
            scoreboard.getOrCreateScore(player, orbs).setScore(0);
        }
    }
}
