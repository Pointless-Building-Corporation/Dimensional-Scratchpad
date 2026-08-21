package com.pointlessbuilding.scratchpad.network.packets;

import java.util.function.Supplier;

import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class EnterScratchpadPacket {
    
    public EnterScratchpadPacket() {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static EnterScratchpadPacket decode(FriendlyByteBuf buf) {
        return new EnterScratchpadPacket();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player != null) ScratchpadDimension.TravelToDimension(player);
        });
        ctx.get().setPacketHandled(true);
    }

}
