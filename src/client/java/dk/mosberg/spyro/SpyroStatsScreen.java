package dk.mosberg.spyro;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Displays Spyro player stats and achievements.
 */
public class SpyroStatsScreen extends Screen {
    private final Screen parent;
    private final SpyroPlayerStats stats;

    public SpyroStatsScreen(Screen parent, SpyroPlayerStats stats) {
        super(Text.literal("Spyro Stats"));
        this.parent = parent;
        this.stats = stats;
    }

    @Override
    protected void init() {
        // Add back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.onClose())
                .dimensions(this.width / 2 - 50, this.height - 40, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Avoid double-blur: let Screen render background internally.
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20,
                0xFFFFFF);

        int x = this.width / 2 - 110;
        int y = 40;
        int line = 12;

        context.drawTextWithShadow(this.textRenderer, "Gems: " + stats.getTotalGemsCollected(), x,
                y, 0xFFFFFF);
        y += line;
        context.drawTextWithShadow(this.textRenderer,
                "Talismans: " + stats.getTotalTalismansCollected(), x, y, 0xFFFFFF);
        y += line;
        context.drawTextWithShadow(this.textRenderer, "Orbs: " + stats.getTotalOrbsCollected(), x,
                y, 0xFFFFFF);
        y += line;
        context.drawTextWithShadow(this.textRenderer,
                "Levels Completed: " + stats.getLevelsCompleted(), x, y, 0xFFFFFF);
        y += line;
        context.drawTextWithShadow(this.textRenderer,
                "Enemies Defeated: " + stats.getEnemiesDefeated(), x, y, 0xFFFFFF);
        y += line * 2;

        context.drawTextWithShadow(this.textRenderer, stats.getAbilityUnlocks().getAbilityStatus(),
                x, y, 0xFFD86B);
        y += line;

        drawAbilityStatus(context, "Fire Breath", stats.getAbilityUnlocks().fireBreathUnlocked, x,
                y);
        y += line;
        drawAbilityStatus(context, "Charge", stats.getAbilityUnlocks().chargeUnlocked, x, y);
        y += line;
        drawAbilityStatus(context, "Glide", stats.getAbilityUnlocks().glideUnlocked, x, y);
        y += line;
        drawAbilityStatus(context, "Flight", stats.getAbilityUnlocks().flightUnlocked, x, y);
        y += line;
        drawAbilityStatus(context, "Time Slow", stats.getAbilityUnlocks().timeSlowUnlocked, x, y);
        y += line;
        drawAbilityStatus(context, "Super Charge", stats.getAbilityUnlocks().superChargeUnlocked, x,
                y);
    }

    private void drawAbilityStatus(DrawContext context, String name, boolean unlocked, int x,
            int y) {
        int color = unlocked ? 0x55FF55 : 0xFF5555;
        String status = unlocked ? "Unlocked" : "Locked";
        context.drawTextWithShadow(this.textRenderer, name + ": " + status, x, y, color);
    }

    public void onClose() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
