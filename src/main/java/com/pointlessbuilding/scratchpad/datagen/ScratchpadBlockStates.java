package com.pointlessbuilding.scratchpad.datagen;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ScratchpadBlockStates extends BlockStateProvider {
    
    public ScratchpadBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DimensionalScratchpad.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.BLANK.get(), models().getExistingFile(modLoc("block/blank")));
    }

}
