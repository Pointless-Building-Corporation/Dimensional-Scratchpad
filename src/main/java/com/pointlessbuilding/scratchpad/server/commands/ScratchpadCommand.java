package com.pointlessbuilding.scratchpad.server.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

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
        boolean success = ScratchpadDimension.TravelToDimension(player);
        if(!success) {
            ctx.getSource().sendFailure(Component.literal("Did not travel to the Scratchpad."));
            return 0;
        }

        ctx.getSource().sendSuccess(() -> Component.literal("Teleported!"), true);
        return 1;
    }

    private static int leaveScratchpadCommand(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        boolean success = ScratchpadDimension.LeaveDimension(player);
        if(!success) {
            ctx.getSource().sendFailure(Component.literal("There was a problem leaving the Scratchpad."));
            return 0;
        }

        ctx.getSource().sendSuccess(() -> Component.literal("Left!"), true);
        return 1;
    }

}

