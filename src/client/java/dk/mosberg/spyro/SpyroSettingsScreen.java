package dk.mosberg.spyro;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Settings screen for Spyro config options. Displays toggles for features, HUD options, and ability
 * tuning.
 */
public class SpyroSettingsScreen extends Screen {
    private final Screen parent;
    private int scrollOffset = 0;
    private static final int ITEM_HEIGHT = 24;
    private static final int MAX_VISIBLE_ITEMS = 10;

    public SpyroSettingsScreen(Screen parent) {
        super(Text.literal("Spyro Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int buttonY = this.height - 40;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.onClose())
                .dimensions(centerX - 50, buttonY, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Reset to Defaults"), button -> {
            SpyroConfig.load();
            SpyroConfig.save();
        }).dimensions(centerX - 75, buttonY - 30, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20,
                0xFFFFFF);

        int x = this.width / 2 - 180;
        int y = 40;
        int lineHeight = ITEM_HEIGHT;

        // Title sections and settings
        drawSettingSection(context, x, y, "⚙ HUD & Display");
        y += lineHeight;

        drawToggleSetting(context, x, y, "HUD Display", "enableHudDisplay", true);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Show Ability Info", "hudShowAbilityInfo", true);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Show Level Name", "hudShowLevelName", true);
        y += lineHeight;

        drawSettingSection(context, x, y, "🔥 Gameplay");
        y += lineHeight;

        drawToggleSetting(context, x, y, "Auto-Collect Items", "enableAutoCollect", true);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Particles", "enableParticles", true);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Progress Tracking", "enableProgressTracking", true);
        y += lineHeight;

        drawSettingSection(context, x, y, "🌟 Planned Content");
        y += lineHeight;

        drawToggleSetting(context, x, y, "Spyro Worlds (WIP)", "enableSpyroWorlds", false);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Spyro Mobs (WIP)", "enableSpyroMobs", false);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Portals (WIP)", "enablePortals", false);
        y += lineHeight;
        drawToggleSetting(context, x, y, "Flight Levels (WIP)", "enableFlightLevels", false);
        y += lineHeight;
    }

    private void drawSettingSection(DrawContext context, int x, int y, String label) {
        context.drawTextWithShadow(this.textRenderer, label, x, y + 4, 0xFFD86B);
    }

    private void drawToggleSetting(DrawContext context, int x, int y, String label,
            String configKey, boolean isReadOnly) {
        SpyroConfig config = SpyroConfig.get();
        boolean value;

        try {
            value = (boolean) config.getClass().getField(configKey).get(config);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            value = false;
        }

        int statusColor = value ? 0x55FF55 : 0xFF5555;
        String status = value ? "ON" : "OFF";
        String readOnlyNote = isReadOnly ? "" : " (WIP)";

        context.drawTextWithShadow(this.textRenderer, label + ": [" + status + "]" + readOnlyNote,
                x + 10, y + 4, statusColor);
    }

    public void onClose() {
        if (this.client != null) {
            SpyroConfig.save();
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
