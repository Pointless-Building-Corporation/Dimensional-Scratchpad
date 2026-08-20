package com.pointlessbuilding.scratchpad.datagen;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ScratchpadItemModels extends ItemModelProvider{

    public ScratchpadItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DimensionalScratchpad.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.BLANK.getId().getPath(), modLoc("block/blank"));
    }
    
}
