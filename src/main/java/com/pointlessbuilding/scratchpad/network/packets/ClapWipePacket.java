package com.pointlessbuilding.scratchpad.network.packets;

import java.util.function.Supplier;

import com.pointlessbuilding.scratchpad.player.IScratchpadState;
import com.pointlessbuilding.scratchpad.player.ScratchpadState;
import com.pointlessbuilding.scratchpad.tools.ScratchpadTools;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ClapWipePacket {

    public ClapWipePacket() {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static ClapWipePacket decode(FriendlyByteBuf buf) {
        return new ClapWipePacket();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            int slot = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getDimensionSlot).orElse(-1);
            if(player != null) ScratchpadTools.serverClapWipe(player.level(), slot);
        });
        ctx.get().setPacketHandled(true);
    }

}