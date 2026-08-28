package com.pointlessbuilding.scratchpad.server;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.player.ScratchpadState;
import com.pointlessbuilding.scratchpad.server.commands.ScratchpadCommand;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerCommonEvents {
    
    @SubscribeEvent
    public static void OnCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(ScratchpadCommand.register());
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(DimensionalScratchpad.MODID, "scratchpad_state"), new ScratchpadState());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
        oldPlayer.reviveCaps();

        oldPlayer.getCapability(ScratchpadState.SCRATCHPAD_STATE).ifPresent(oldCap -> {
            event.getEntity().getCapability(ScratchpadState.SCRATCHPAD_STATE).ifPresent(newCap -> {
                newCap.deserializeNBT(oldCap.serializeNBT());
            });
        });

        oldPlayer.invalidateCaps();
    }

}
