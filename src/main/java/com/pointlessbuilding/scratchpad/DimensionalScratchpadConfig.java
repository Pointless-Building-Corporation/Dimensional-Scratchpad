package com.pointlessbuilding.scratchpad;

import net.minecraftforge.common.ForgeConfigSpec;

public class DimensionalScratchpadConfig {
    
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue COLOR_GRADIENT_START;
    public static final ForgeConfigSpec.IntValue COLOR_GRADIENT_END;
    public static final ForgeConfigSpec.IntValue SHELL_SIZE;

    static {
        BUILDER.comment("Dimensional Scratchpad Settings");
        COLOR_GRADIENT_START = BUILDER.comment("Maximum number of stored boundaries").defineInRange("color_gradient_start",0xA2BFFE,0x000000,0xFFFFFF);
        COLOR_GRADIENT_END = BUILDER.comment("Maximum boundary size in any direction").defineInRange("color_gradient_end",0xFFC5D3,0x000000,0xFFFFFF);
        SHELL_SIZE = BUILDER.comment("Size of cubic shell per player in dimension").defineInRange("shell_size", 64, 4, 256);
        SPEC = BUILDER.build();
    }

}
