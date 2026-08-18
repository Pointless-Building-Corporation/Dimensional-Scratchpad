package com.pointlessbuilding.scratchpad.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ScratchpadBlockTags extends BlockTagsProvider{

    public ScratchpadBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DimensionalScratchpad.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider arg0) {
    }

}
