package com.pointlessbuilding.scratchpad.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.pointlessbuilding.scratchpad.DimensionalScratchpad;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = DimensionalScratchpad.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    
    public static final String ENTER_SCRATCHPAD_STRING = "key.dimensionalscratchpad.enter";
    public static final String LEAVE_SCRATCHPAD_STRING = "key.dimensionalscratchpad.leave";

    public static final Lazy<EnterScratchpadKeymap> ENTER_SCRATCHPAD_KEYMAP = Lazy.of(() ->
        new EnterScratchpadKeymap(ENTER_SCRATCHPAD_STRING, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_BRACKET, KeyMapping.CATEGORY_MISC)
    );

    public static final Lazy<LeaveScratchpadKeymap> LEAVE_SCRATCHPAD_KEYMAP = Lazy.of(() ->
        new LeaveScratchpadKeymap(LEAVE_SCRATCHPAD_STRING, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_BRACKET, KeyMapping.CATEGORY_MISC)
    );

    // @SubscribeEvent
    // public static void init(FMLClientSetupEvent event) {
    //     event.enqueueWork(() -> {
    //         ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
    //             () -> new ConfigScreenHandler.ConfigScreenFactory(
    //                 (mc, screen) -> new ConfigUI(screen)
    //             ));
    //     });
    // }

    @SubscribeEvent
    public static void registerKeybindings(RegisterKeyMappingsEvent event) {
        event.register(ENTER_SCRATCHPAD_KEYMAP.get());
        event.register(LEAVE_SCRATCHPAD_KEYMAP.get());
    }

}
