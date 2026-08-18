package com.pointlessbuilding.scratchpad.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class ScratchpadWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.DIMENSION_TYPE, ScratchpadDimension::bootstrapType)
        .add(Registries.LEVEL_STEM, ScratchpadDimension::bootstrapStem);

    public ScratchpadWorldGenProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Set.of(DimensionalScratchpad.MODID));
    }
    
}
