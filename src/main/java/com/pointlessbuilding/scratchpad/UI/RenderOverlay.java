package com.pointlessbuilding.scratchpad.UI;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderOverlay {
    
    private static final ResourceLocation SCRATCHPAD_ID = ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, ScratchpadOverlay.OVERLAY_ID);

    @SubscribeEvent
    public static void OnPreRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if(event.getOverlay().id().equals(SCRATCHPAD_ID)) {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player == null | mc.level == null) return;
            if(mc.level.dimension() != ScratchpadDimension.LEVEL) {
                event.setCanceled(true);
            }
        }
    }

}
