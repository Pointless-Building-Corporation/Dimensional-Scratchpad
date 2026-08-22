package com.pointlessbuilding.scratchpad.client;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.network.Network;
import com.pointlessbuilding.scratchpad.network.packets.ScratchpadPacket;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientTickEvents {
    
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            while(ClientSetup.SCRATCHPAD_KEYMAP.get().consumeClick()) {
                Network.sendToServer(new ScratchpadPacket());
            }
        }
    }

}
