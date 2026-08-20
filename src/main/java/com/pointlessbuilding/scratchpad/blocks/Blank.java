package com.pointlessbuilding.scratchpad.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Blank extends Block{

    public Blank() {
        super(BlockBehaviour.Properties.of()
        .strength(-1.0F, 3600000.0F)
        .noLootTable()
        );
    }
    
}
