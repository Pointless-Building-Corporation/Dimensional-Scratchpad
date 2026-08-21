package com.pointlessbuilding.scratchpad.player;

import java.util.ArrayList;
import java.util.Collection;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;
import com.pointlessbuilding.scratchpad.dimension.ScratchpadDimension;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    
    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity().level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        ResourceKey<Level> from = event.getFrom();
        ResourceKey<Level> to = event.getTo();

        // For some reason this is not tested in vanilla code
        if(from.equals(to)) return;

        // Entering Scratchpad
        if(to.equals(ScratchpadDimension.LEVEL)) {

            player.getCapability(ScratchpadState.SCRATCHPAD_STATE)
                .ifPresent(state -> state.savePlayerState(
                    player.getInventory().items,
                    player.getInventory().armor,
                    player.getInventory().offhand,
                    player.getHealth(),
                    player.getFoodData().getFoodLevel(),
                    player.getFoodData().getSaturationLevel(),
                    player.getFoodData().getExhaustionLevel(),
                    player.getRemainingFireTicks(),
                    player.totalExperience,
                    player.getActiveEffects(),
                    player.getAirSupply(),
                    player.fallDistance,
                    player.gameMode.getGameModeForPlayer()
                ));

            player.setGameMode(GameType.CREATIVE);
        }

        // Leaving Scratchpad
        else if(from.equals(ScratchpadDimension.LEVEL)) {

            NonNullList<ItemStack> lastItems = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastItems).orElse(NonNullList.withSize(36, ItemStack.EMPTY));
            NonNullList<ItemStack> lastArmor = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastArmor).orElse(NonNullList.withSize(4, ItemStack.EMPTY));
            NonNullList<ItemStack> lastOffhand = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastOffhand).orElse(NonNullList.withSize(1, ItemStack.EMPTY));
            float lastHealth = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastHealth).orElse(player.getMaxHealth());
            int lastFoodLevel = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastFoodLevel).orElse(20);
            float lastSaturation = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastSaturation).orElse(5.0f);
            float lastExhaustion = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastExhaustion).orElse(0.0f);
            int lastFireTicks = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastFireTicks).orElse(0);
            int lastXp = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastXp).orElse(0);
            Collection<MobEffectInstance> lastEffects = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastEffects).orElse(new ArrayList<>());
            int lastAir = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastAirSupply).orElse(player.getMaxAirSupply());
            float lastFallDist = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastFallDistance).orElse(0.0f);
            GameType lastGameType = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastGameType).orElse(GameType.SURVIVAL);

            player.getInventory().items.clear();
            player.getInventory().armor.clear();
            player.getInventory().offhand.clear();

            for (int i = 0; i < lastItems.size(); i++)
                player.getInventory().items.set(i, lastItems.get(i));

            for (int i = 0; i < lastArmor.size(); i++)
                player.getInventory().armor.set(i, lastArmor.get(i));

            for (int i = 0; i < lastOffhand.size(); i++)
                player.getInventory().offhand.set(i, lastOffhand.get(i));

            player.setHealth(lastHealth);
            FoodData food = player.getFoodData();
            food.setFoodLevel(lastFoodLevel);
            food.setExhaustion(lastExhaustion);
            food.setSaturation(lastSaturation);
            player.setRemainingFireTicks(lastFireTicks);
            player.setExperiencePoints(lastXp);

            player.removeAllEffects();
            for(MobEffectInstance effect: lastEffects) player.addEffect(new MobEffectInstance(effect));

            player.setAirSupply(lastAir);
            player.fallDistance = lastFallDist;

            player.setGameMode(lastGameType);
        }

    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());

        if(state.is(Registration.BLANK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().dimension().equals(ScratchpadDimension.LEVEL)) return;
        BlockState state = event.getLevel().getBlockState(event.getPos());

        if(state.is(Blocks.ENDER_CHEST)) {
            event.setCanceled(true);
        }
    }

}
