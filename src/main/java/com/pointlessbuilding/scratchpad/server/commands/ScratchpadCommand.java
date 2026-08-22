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

        return scratchpad;
    }

    private static int teleportToScratchpadCommand(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        boolean success = ScratchpadDimension.TravelToOrFromDimension(player);
        if(!success) {
            ctx.getSource().sendFailure(Component.literal("There was some issue with the travel."));
            return 0;
        }

        ctx.getSource().sendSuccess(() -> Component.literal("Teleported!"), true);
        return 1;
    }

}

