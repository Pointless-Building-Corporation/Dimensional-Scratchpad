package com.pointlessbuilding.scratchpad.tools;

import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;
import com.pointlessbuilding.scratchpad.network.Network;
import com.pointlessbuilding.scratchpad.network.packets.ClapWipePacket;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class ScratchpadTools {
    
    public static void clapWipe(LocalPlayer player) {
        if(player != null) {
            player.sendSystemMessage(Component.literal("Clap Wipe clicked"));
            Network.sendToServer(new ClapWipePacket());
        }
    }

    public static void serverClapWipe(Level level, int slot) {
        if(level.isClientSide()) return;
        if(slot == -1) return;

        Vec3 slotCoord = ScratchpadDimension.calculateNewScratchpadSlotPos(slot);

        int shellSize = ScratchpadDimension.SHELL_SIZE;
        BlockPos first = new BlockPos((int)slotCoord.x, 0, (int)slotCoord.z);
        BlockPos second = new BlockPos((int)slotCoord.x + shellSize - 1, 63, (int)slotCoord.z + shellSize - 1);

        for (BlockPos pos: BlockPos.betweenClosed(first, second)) {
            if(!level.getBlockState(pos).isAir()) {
                BlockEntity entity = level.getBlockEntity(pos);
                if(entity != null) Clearable.tryClear(entity);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS | Block.UPDATE_SUPPRESS_DROPS);
            }
        }

    }

    public static void saveBuild(LocalPlayer player) {

    }

    public static void loadBuild(LocalPlayer player) {
        
    }

}
