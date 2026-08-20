package com.pointlessbuilding.scratchpad.blocks;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;

import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScratchpadColors {
    
    @SubscribeEvent
    public static void register(RegisterColorHandlersEvent.Block event) {
        event.register(
            (state, level, pos, tintIndex) -> {
                if(pos == null) return 0xFFFFFF;
                return calculateColor(pos);
            },
            Registration.BLANK.get()
        );
    }

    private static int calculateColor(BlockPos pos) {
        double phase = (pos.getX() - pos.getZ()) / 32.0;
        double t = (Math.sin(phase * 2.0 * Math.PI) + 1.0) * 0.5;

        int blue = 0xA2BFFE;
        int pink = 0xFFC5D3;

        int rB = (blue>>16) & 0xFF, rP = (pink>>16) & 0xFF;
        int gB = (blue>>8) & 0xFF, gP = (pink>>8) & 0xFF;
        int bB = blue & 0xFF, bP = pink & 0xFF;

        int r = (int)(rB + (rP-rB) * t);
        int g = (int)(gB + (gP-gB) * t);
        int b = (int)(bB + (bP-bB) * t);
        return (r<<16) | (g<<8) | b;
    }

}
