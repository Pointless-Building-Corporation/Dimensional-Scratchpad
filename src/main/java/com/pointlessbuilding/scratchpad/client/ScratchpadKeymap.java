package com.pointlessbuilding.scratchpad.client;

import com.mojang.blaze3d.platform.InputConstants.Type;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;

public class ScratchpadKeymap extends KeyMapping{
    
    public ScratchpadKeymap(String name, IKeyConflictContext conflictContext, Type type, int keyCode, String category) {
        super(name, conflictContext, type, keyCode, category);
    }

}
