package com.pointlessbuilding.scratchpad.UI;

import com.pointlessbuilding.scratchpad.DimensionalScratchpadConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigUI extends Screen{

    public static final String CONFIG_UI_TITLE = "screen.dimensionalscratchpad.ui_title";

    public static final String CONFIG_UI_COLOR_GRADIENT = "screen.dimensionalscratchpad.color_gradient";
    public static final String CONFIG_UI_COLOR_GRADIENT_DESC_S = "screen.dimensionalscratchpad.color_gradient_desc_start";
    public static final String CONFIG_UI_COLOR_GRADIENT_DESC_E = "screen.dimensionalscratchpad.color_gradient_desc_end";

    private Screen parentScreen;

    private int vertical_padding = 2;
    private int starting_y = 8;

    private EditBox startingHexInput;
    private EditBox endingHexInput;
    private int startingHexValue, endingHexValue;

    public ConfigUI(Screen parentScreen) {
        super(Component.translatable(CONFIG_UI_TITLE));
        this.parentScreen = parentScreen;
    }

    @Override
    public void init() {
        this.addRenderableWidget(Button.builder(Component.literal("Back"), btn -> this.onClose())
        .bounds(width/2 - 100, height - 27, 200, 20)
        .build());

        startingHexInput = new EditBox(this.font, (int)(width * 0.9) - 100, starting_y + 2*(20 + vertical_padding), 100, 20, Component.literal("Hex"));
        startingHexInput.setMaxLength(6);
        startingHexInput.setFilter(s -> s.matches("^[0-9A-Fa-f]{0,6}$"));
        startingHexValue = DimensionalScratchpadConfig.COLOR_GRADIENT_START.get();
        startingHexInput.setValue(Integer.toHexString(startingHexValue));
        startingHexInput.setResponder(s -> {
            if (s.isEmpty()) startingHexValue = DimensionalScratchpadConfig.COLOR_GRADIENT_START.get();
            else startingHexValue = Integer.parseInt(s, 16);
        });
        this.addRenderableWidget(startingHexInput);

        endingHexInput = new EditBox(this.font, (int)(width * 0.9) - 100, starting_y + 3*(20 + vertical_padding), 100, 20, Component.literal("Hex"));
        endingHexInput.setMaxLength(6);
        endingHexInput.setFilter(s -> s.matches("^[0-9A-Fa-f]{0,6}$"));
        endingHexValue = DimensionalScratchpadConfig.COLOR_GRADIENT_END.get();
        endingHexInput.setValue(Integer.toHexString(endingHexValue));
        endingHexInput.setResponder(s -> {
            if (s.isEmpty()) endingHexValue = DimensionalScratchpadConfig.COLOR_GRADIENT_END.get();
            else endingHexValue = Integer.parseInt(s, 16);
        });
        this.addRenderableWidget(endingHexInput);
    }
    
    private int labelY(int row) {
        return starting_y + row*(20 + vertical_padding) + (20 - this.font.lineHeight) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        // Title
        guiGraphics.drawString(this.font, Component.translatable(CONFIG_UI_TITLE), (width - this.font.width(this.title.getString())) / 2, starting_y, 0xFFFFFF);
        // Bg
        guiGraphics.fill((int)(width * 0.08), starting_y + 10 + vertical_padding, (int)(width * 0.92), height - 27 - vertical_padding, 0x80000000);

        //
        // Config values
        //

        // Color Gradient
        guiGraphics.drawString(this.font, Component.translatable(CONFIG_UI_COLOR_GRADIENT), (int)(width * 0.1), labelY(1), 0xAAAAAA);
        guiGraphics.drawString(this.font, Component.translatable(CONFIG_UI_COLOR_GRADIENT_DESC_S), (int)(width * 0.1), labelY(2), 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.translatable(CONFIG_UI_COLOR_GRADIENT_DESC_E), (int)(width * 0.1), labelY(3), 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        // Set the config values
        DimensionalScratchpadConfig.COLOR_GRADIENT_START.set(startingHexValue);
        DimensionalScratchpadConfig.COLOR_GRADIENT_END.set(endingHexValue);

        DimensionalScratchpadConfig.SPEC.save();
        Minecraft.getInstance().setScreen(parentScreen);
    }

}
