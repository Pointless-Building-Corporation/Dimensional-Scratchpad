package com.pointlessbuilding.scratchpad.server.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;
import com.pointlessbuilding.scratchpad.player.IScratchpadState;
import com.pointlessbuilding.scratchpad.player.ScratchpadState;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScratchpadCommand {
    
    public static LiteralArgumentBuilder<CommandSourceStack> register() {

        LiteralArgumentBuilder<CommandSourceStack> scratchpad = Commands.literal("scratchpad").requires(cs -> cs.hasPermission(0));

        scratchpad.then(Commands.literal("tp")
            .executes(ctx -> teleportToScratchpadCommand(ctx, ctx.getSource().getPlayerOrException()))
        );

        scratchpad.then(Commands.literal("leave")
            .executes(ctx -> leaveScratchpadCommand(ctx, ctx.getSource().getPlayerOrException()))
        );

        return scratchpad;
    }

    private static int teleportToScratchpadCommand(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if(player.level().dimension().equals(ScratchpadDimension.LEVEL)) {
            ctx.getSource().sendFailure(Component.literal("Already at the Scratchpad!"));
            return 0;
        }

        ServerLevel targetLevel = server.getLevel(ScratchpadDimension.LEVEL);
        if(targetLevel == null) {
            ctx.getSource().sendFailure(Component.literal("Scratchpad not found"));
            return 0;
        }

        player.getCapability(ScratchpadState.SCRATCHPAD_STATE)
            .ifPresent(state -> state.savePlayerPos(player.level().dimension(), player.position()));
        
        player.teleportTo(targetLevel, 0.5, 10, 0.5, player.getYRot(), player.getXRot());

        ctx.getSource().sendSuccess(() -> Component.literal("Teleported!"), true);
        return 1;
    }

    private static int leaveScratchpadCommand(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if(!player.level().dimension().equals(ScratchpadDimension.LEVEL)) {
            ctx.getSource().sendFailure(Component.literal("Can't leave when not in the Scratchpad!"));
            return 0;
        }

        ResourceKey<Level> prevDimension = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastDimension).orElse(Level.OVERWORLD);
        Vec3 lastPos = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastPosition).orElse(new Vec3(0,100,0));

        ServerLevel targetLevel = server.getLevel(prevDimension);
        if(targetLevel == null) {
            ctx.getSource().sendFailure(Component.literal("Overworld not found"));
            return 0;
        }

        player.teleportTo(targetLevel, lastPos.x, lastPos.y, lastPos.z, player.getYRot(), player.getXRot());

        ctx.getSource().sendSuccess(() -> Component.literal("Left!"), true);
        return 1;
    }

}

