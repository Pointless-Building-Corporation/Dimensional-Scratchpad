package com.pointlessbuilding.scratchpad.blocks;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OverridePickBlock {
 
    @SubscribeEvent
    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isPickBlock()) return;

        if (Minecraft.getInstance().hitResult instanceof BlockHitResult hit) {
            BlockState state = Minecraft.getInstance().level.getBlockState(hit.getBlockPos());
            if (state.is(Registration.BLANK.get())) {
                event.setCanceled(true);
            }
        }
    }

}
