package dk.mosberg;

import java.util.Objects;
import org.lwjgl.glfw.GLFW;
import dk.mosberg.spyro.SpyroClientNetworking;
import dk.mosberg.spyro.SpyroCollectibles;
import dk.mosberg.spyro.SpyroConfig;
import dk.mosberg.spyro.SpyroMenuScreen;
import dk.mosberg.spyro.SpyroNetworking;
import dk.mosberg.spyro.SpyroPlayerStats;
import dk.mosberg.spyro.SpyroStatsAttachment;
import dk.mosberg.spyro.SpyroStatsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpyroClient implements ClientModInitializer {
    private static final Identifier SPYRO_ICON =
            Identifier.of(Spyro.MOD_ID, "textures/gui/spyro_icon.png");
    private static final KeyBinding.Category SPYRO_CATEGORY =
            KeyBinding.Category.create(Identifier.of(Spyro.MOD_ID, "spyro"));
    private static KeyBinding fireKey;
    private static KeyBinding chargeKey;
    private static KeyBinding glideKey;
    private static KeyBinding menuKey;
    private static KeyBinding statsKey;
    private boolean lastCharge;
    private boolean lastGlide;

    @Override
    public void onInitializeClient() {
        SpyroNetworking.registerPayloads();
        fireKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spyro.fire",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, SPYRO_CATEGORY));
        chargeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spyro.charge",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, SPYRO_CATEGORY));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spyro.menu",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, SPYRO_CATEGORY));
        statsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spyro.stats",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_L, SPYRO_CATEGORY));
        glideKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spyro.glide",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, SPYRO_CATEGORY));


        ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTick);
        HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS,
                Objects.requireNonNull(Identifier.of(Spyro.MOD_ID, "spyro_hud")), this::renderHud);
    }

    private void handleClientTick(MinecraftClient client) {

        // Menu keybinds
        while (menuKey.wasPressed()) {
            SpyroPlayerStats stats = SpyroStatsAttachment.get(client.player);
            client.setScreen(new SpyroMenuScreen(client.currentScreen, stats));
        }

        while (statsKey.wasPressed()) {
            SpyroPlayerStats stats = SpyroStatsAttachment.get(client.player);
            client.setScreen(new SpyroStatsScreen(client.currentScreen, stats));
        }

        // Ability keybinds
        if (client.player == null) {
            return;
        }
        boolean charge = chargeKey.isPressed();
        boolean glide = glideKey.isPressed();
        if (charge != lastCharge || glide != lastGlide) {
            SpyroClientNetworking.sendAbilityState(charge, glide);
            lastCharge = charge;
            lastGlide = glide;
        }
        while (fireKey.wasPressed()) {
            SpyroClientNetworking.sendFireRequest();
        }
    }

    private void renderHud(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer == null) {
            return;
        }
        var player = client.player;
        if (player == null) {
            return;
        }
        var world = client.world;
        if (world == null) {
            return;
        }
        if (!player.getCommandTags().contains(Spyro.SPYRO_TAG)) {
            return;
        }
        SpyroConfig config = SpyroConfig.get();
        if (!config.enableHudDisplay) {
            return;
        }

        var scoreboard = world.getScoreboard();
        int gems = SpyroCollectibles.getGemCount(scoreboard, player);
        int talismans = SpyroCollectibles.getTalismanCount(scoreboard, player);
        int orbs = SpyroCollectibles.getOrbCount(scoreboard, player);
        int totalScore = SpyroCollectibles.getTotalScore(scoreboard, player);

        int x = config.hudOffsetX;
        int y = config.hudOffsetY;
        drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, SPYRO_ICON, x, y, 0, 0, 16, 16, 16,
                16);
        drawContext.drawTextWithShadow(client.textRenderer, Text.literal("Spyro Mode"), x + 20,
                y + 4, 0xFFFA983A);
        if (config.hudShowAbilityInfo) {
            drawContext.drawTextWithShadow(client.textRenderer,
                    Text.literal("Fire: R  Charge: V  Glide: G"), x, y + 20, 0xFFE0E0E0);
        }

        int collectiblesY = config.hudShowAbilityInfo ? y + 34 : y + 20;
        // Collectibles display
        drawContext.drawTextWithShadow(client.textRenderer,
                Text.translatable("text.spyro.gems", gems), x, collectiblesY, 0xFF8FE3FF);
        drawContext.drawTextWithShadow(client.textRenderer,
                Text.translatable("text.spyro.talismans", talismans), x, collectiblesY + 12,
                0xFFD4A5FF);
        drawContext.drawTextWithShadow(client.textRenderer,
                Text.translatable("text.spyro.orbs", orbs), x, collectiblesY + 24, 0xFF00D4FF);

        // Total score
        int scoreY = collectiblesY + 38;
        drawContext.drawTextWithShadow(client.textRenderer,
                Text.translatable("text.spyro.score", totalScore), x, scoreY, 0xFFFFD700);

        if (config.hudShowLevelName) {
            String dimension = world.getRegistryKey().getValue().getPath();
            drawContext.drawTextWithShadow(client.textRenderer, Text.literal("Realm: " + dimension),
                    x, scoreY + 12, 0xFFBFBFBF);
        }
    }
}
