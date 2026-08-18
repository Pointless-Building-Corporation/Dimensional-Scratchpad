package com.pointlessbuilding.scratchpad.datagen;

import java.util.Map;
import java.util.stream.Collectors;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ScratchpadLootTables extends VanillaBlockLoot{
    
    @Override
    protected void generate() {
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(DimensionalScratchpad.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}
