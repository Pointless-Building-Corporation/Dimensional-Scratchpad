package com.pointlessbuilding.scratchpad.server.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

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

        ServerLevel targetLevel = server.getLevel(ScratchpadDimension.LEVEL);
        if(targetLevel == null) {
            ctx.getSource().sendFailure(Component.literal("Scratchpad not found"));
            return 0;
        }

        player.teleportTo(targetLevel, 0.5, 1, 0.5, player.getYRot(), player.getXRot());

        ctx.getSource().sendSuccess(() -> Component.literal("Teleported!"), true);
        return 1;
    }

    private static int leaveScratchpadCommand(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        MinecraftServer server = player.getServer();

        ServerLevel targetLevel = server.getLevel(Level.OVERWORLD);
        if(targetLevel == null) {
            ctx.getSource().sendFailure(Component.literal("Overworld not found"));
            return 0;
        }

        player.teleportTo(targetLevel, 0.5, 1, 0.5, player.getYRot(), player.getXRot());

        ctx.getSource().sendSuccess(() -> Component.literal("Teleported!"), true);
        return 1;
    }

}

