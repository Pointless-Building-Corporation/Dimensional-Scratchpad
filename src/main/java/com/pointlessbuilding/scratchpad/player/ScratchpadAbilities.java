package com.pointlessbuilding.scratchpad.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.level.GameType;

public class ScratchpadAbilities {
    
    public static void applyScratchpadAbilities(ServerPlayer player) {
        GameType type = player.gameMode.getGameModeForPlayer();

        if(type == GameType.SURVIVAL || type == GameType.ADVENTURE) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = true;
            abilities.invulnerable = true;
            abilities.instabuild = true;
            player.onUpdateAbilities();
        }
    }

    public static void restoreScratchpadAbilities(ServerPlayer player, boolean mayfly, boolean invulnerable, boolean instabuild) {
        GameType type = player.gameMode.getGameModeForPlayer();

        if(type == GameType.SURVIVAL || type == GameType.ADVENTURE) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = mayfly;
            abilities.invulnerable = invulnerable;
            abilities.instabuild = instabuild;
            player.onUpdateAbilities();
        }
    }

}
