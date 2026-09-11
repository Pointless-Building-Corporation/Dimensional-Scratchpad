package com.pointlessbuilding.scratchpad.UI;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterOverlay {
    
    @SubscribeEvent
    public static void registerScratchpadOverlay(RegisterGuiOverlaysEvent event) {
        //event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), ScratchpadOverlay.OVERLAY_ID, new ScratchpadOverlay());
    }

}
