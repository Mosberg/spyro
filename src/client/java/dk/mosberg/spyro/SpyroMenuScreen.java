package dk.mosberg.spyro;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Main Spyro menu for accessing stats and setting.
 */
public class SpyroMenuScreen extends Screen {
    private final Screen parent;
    private final SpyroPlayerStats stats;

    public SpyroMenuScreen(Screen parent, SpyroPlayerStats stats) {
        super(Text.literal("Spyro Menu"));
        this.parent = parent;
        this.stats = stats;
    }

    @Override
    protected void init() {
        int buttonWidth = 120;
        int buttonHeight = 20;
        int centerX = this.width / 2 - buttonWidth / 2;

        this.addDrawableChild(ButtonWidget
                .builder(Text.literal("§cStats"),
                        button -> this.client.setScreen(new SpyroStatsScreen(this, stats)))
                .dimensions(centerX, 80, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget
                .builder(Text.literal("§6Abilities"),
                        button -> this.client.setScreen(
                                new SpyroAbilitiesScreen(this, stats.getAbilityUnlocks())))
                .dimensions(centerX, 110, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("§bSettings"), button -> {
            this.client.setScreen(new SpyroSettingsScreen(this));
        }).dimensions(centerX, 140, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.onClose())
                .dimensions(centerX, this.height - 40, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Avoid double-blur: let Screen render background internally.
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20,
                0xFFFFFF);
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
