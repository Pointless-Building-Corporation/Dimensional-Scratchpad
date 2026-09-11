package com.pointlessbuilding.scratchpad.client;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.UI.ScratchpadOverlay;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;
import com.pointlessbuilding.scratchpad.network.Network;
import com.pointlessbuilding.scratchpad.network.packets.ScratchpadPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
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
            handleKeybinds();
            //callAllRenderTicks();
        }

    }

    private static void handleKeybinds() {
        while(ClientSetup.SCRATCHPAD_KEYMAP.get().consumeClick()) {
            Network.sendToServer(new ScratchpadPacket());
        }

        // ClientLevel curLevel = Minecraft.getInstance().level;
        // if(curLevel != null) {
        //     if(curLevel.dimension().equals(ScratchpadDimension.LEVEL)) {
        //         while(ClientSetup.SCRATCHPAD_TOOLS_KEYMAP.get().consumeClick()) {
        //             ScratchpadOverlay overlay = ScratchpadOverlay.getById();
        //             if(overlay != null) overlay.startOverlayOpeningAnimation();
        //         }
        //     }
        // }
    }

    // Not sure I like that I need to call counter stuff here.
    private static void callAllRenderTicks() {
        ScratchpadOverlay overlay = ScratchpadOverlay.getById();
        if(overlay != null) {
            overlay.tick();
        }
    }

}
