package com.pointlessbuilding.scratchpad;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.pointlessbuilding.scratchpad.datagen.DataGeneration;
import com.pointlessbuilding.scratchpad.network.Network;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("removal")
@Mod(DimensionalScratchpad.MODID)
public class DimensionalScratchpad {
    
    public static final String MODID = "dimensionalscratchpad";
    public static final String VERSION = "1.20.1-0.1.0";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DimensionalScratchpad() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,  DimensionalScratchpadConfig.SPEC);
    
        Registration.init(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(DataGeneration::generate);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.init();

        LOGGER.info("Loaded Dimensional Scratchpad!");
    }

}
