package dk.mosberg.spyro;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Displays available Spyro abilities and their mechanics.
 */
public class SpyroAbilitiesScreen extends Screen {
    private final Screen parent;
    private final SpyroAbilityUnlocks unlocks;

    public SpyroAbilitiesScreen(Screen parent, SpyroAbilityUnlocks unlocks) {
        super(Text.literal("Spyro Abilities"));
        this.parent = parent;
        this.unlocks = unlocks;
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> this.onClose())
                .dimensions(this.width / 2 - 50, this.height - 40, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Avoid double-blur: let Screen render background internally.
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20,
                0xFFFFFF);

        int x = this.width / 2 - 140;
        int y = 40;

        drawAbility(context, "Fire Breath", "Key: R",
                "Breathe fire in a short cone to damage enemies.", x, y);
        y += 48;

        drawAbility(context, "Charge", "Hold: V", "Charge forward to bash enemies with knockback.",
                x, y);
        y += 48;

        drawAbility(context, "Glide", "Hold: Space", "Reduce fall speed and glide forward.", x, y);
    }

    private void drawAbility(DrawContext context, String name, String keyInfo, String description,
            int x, int y) {
        context.drawTextWithShadow(this.textRenderer, name, x, y, 0xFFD86B);
        context.drawTextWithShadow(this.textRenderer, keyInfo, x + 140, y, 0xAAAAAA);

        int lineY = y + 12;
        for (String line : splitDescription(description, 260)) {
            context.drawTextWithShadow(this.textRenderer, line, x, lineY, 0xFFFFFF);
            lineY += 10;
        }
    }

    private java.util.List<String> splitDescription(String description, int width) {
        java.util.List<String> lines = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (String word : description.split(" ")) {
            String test = current.isEmpty() ? word : current + " " + word;
            if (this.textRenderer.getWidth(test) > width) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                current = new StringBuilder(test);
            }
        }
        if (!current.isEmpty()) {
            lines.add(current.toString());
        }
        return lines;
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
