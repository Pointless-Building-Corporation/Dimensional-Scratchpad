package com.pointlessbuilding.scratchpad.network;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.network.packets.ScratchpadPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("removal")
public class Network {
    private static SimpleChannel CHANNEL;
    private static int ID = 0;

    public static void init() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DimensionalScratchpad.MODID, "main"),
            () -> DimensionalScratchpad.VERSION,
            DimensionalScratchpad.VERSION::equals,
            DimensionalScratchpad.VERSION::equals
        );

        CHANNEL.registerMessage(ID++, ScratchpadPacket.class, ScratchpadPacket::encode, ScratchpadPacket::decode, ScratchpadPacket::handle);
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }
}
