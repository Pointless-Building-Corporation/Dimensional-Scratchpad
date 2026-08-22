package com.pointlessbuilding.scratchpad.datagen;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;
import com.pointlessbuilding.scratchpad.UI.ConfigUI;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ScratchpadLanguageProvider extends LanguageProvider{

    public ScratchpadLanguageProvider(PackOutput output, String locale) {
        super(output, DimensionalScratchpad.MODID, locale);
    }

    @Override
    protected void addTranslations() {

        // Config values
        add(ConfigUI.CONFIG_UI_TITLE, "Dimensional Scratchpad Configuration");
        add(ConfigUI.CONFIG_UI_COLOR_GRADIENT, "Scratchpad Dimension Color Gradient");
        add(ConfigUI.CONFIG_UI_COLOR_GRADIENT_DESC_S, "Starting Color of the Gradient");
        add(ConfigUI.CONFIG_UI_COLOR_GRADIENT_DESC_E, "Ending Color of the Gradient");

        // Blocks and Items
        add(Registration.BLANK.get(), "Blank");

        add("dimension.dimensionalscratchpad.not_safe", "Can't teleport now, there are monsters nearby");
    }
    
}
