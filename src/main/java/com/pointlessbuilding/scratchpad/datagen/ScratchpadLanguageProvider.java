package com.pointlessbuilding.scratchpad.datagen;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ScratchpadLanguageProvider extends LanguageProvider{

    public ScratchpadLanguageProvider(PackOutput output, String locale) {
        super(output, DimensionalScratchpad.MODID, locale);
    }

    @Override
    protected void addTranslations() {
    }
    
}
