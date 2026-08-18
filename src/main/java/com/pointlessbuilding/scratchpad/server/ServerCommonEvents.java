package com.pointlessbuilding.scratchpad.server;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.server.commands.ScratchpadCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerCommonEvents {
    
    @SubscribeEvent
    public static void OnCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(ScratchpadCommand.register());
    }

}
