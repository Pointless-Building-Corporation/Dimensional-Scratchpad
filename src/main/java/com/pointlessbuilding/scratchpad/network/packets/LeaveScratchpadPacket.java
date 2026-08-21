package com.pointlessbuilding.scratchpad.network.packets;

import java.util.function.Supplier;

import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class LeaveScratchpadPacket {
    
    public LeaveScratchpadPacket() {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static LeaveScratchpadPacket decode(FriendlyByteBuf buf) {
        return new LeaveScratchpadPacket();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player != null) ScratchpadDimension.LeaveDimension(player);
        });
        ctx.get().setPacketHandled(true);
    }

}
