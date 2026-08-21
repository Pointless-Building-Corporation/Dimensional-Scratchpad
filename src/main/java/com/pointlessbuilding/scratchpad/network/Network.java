package com.pointlessbuilding.scratchpad.network;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.network.packets.EnterScratchpadPacket;
import com.pointlessbuilding.scratchpad.network.packets.LeaveScratchpadPacket;

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

        CHANNEL.registerMessage(ID++, EnterScratchpadPacket.class, EnterScratchpadPacket::encode, EnterScratchpadPacket::decode, EnterScratchpadPacket::handle);
        CHANNEL.registerMessage(ID++, LeaveScratchpadPacket.class, LeaveScratchpadPacket::encode, LeaveScratchpadPacket::decode, LeaveScratchpadPacket::handle);
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }
}
