package com.pointlessbuilding.scratchpad;

import com.pointlessbuilding.scratchpad.blocks.Blank;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DimensionalScratchpad.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DimensionalScratchpad.MODID);

    public static final RegistryObject<Blank> BLANK = BLOCKS.register("blank", Blank::new);
    public static final RegistryObject<Item> BLANK_ITEM = ITEMS.register("blank", () -> new BlockItem(BLANK.get(), new Item.Properties()));
    
    public static void init(IEventBus modEventBus) {
        // Register deffered register suppliers for blocks, block items, block entities
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }

}
