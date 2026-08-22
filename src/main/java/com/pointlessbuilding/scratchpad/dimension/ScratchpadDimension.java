package com.pointlessbuilding.scratchpad.dimension;

import java.util.List;
import java.util.OptionalLong;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.player.IScratchpadState;
import com.pointlessbuilding.scratchpad.player.ScratchpadState;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("removal")
public class ScratchpadDimension {
    
    public static final ResourceKey<LevelStem> DIM_KEY = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(DimensionalScratchpad.MODID, "scratchpad_dimension"));
    public static final ResourceKey<Level> LEVEL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(DimensionalScratchpad.MODID, "scratchpad_dimension"));
    public static final ResourceKey<DimensionType> DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(DimensionalScratchpad.MODID, "scratchpad_dimension"));
    
    // Bootstap is factually a misspelling in Mojang's og source code. That's hilarious
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(DIM_TYPE, new DimensionType(
            OptionalLong.of(12000),
            false,
            false,
            false,
            false,
            1.0,
            false,
            false,
            -16,
            320,
            320,
            BlockTags.INFINIBURN_OVERWORLD,
            new ResourceLocation(DimensionalScratchpad.MODID, "scratchpad"),
            1.0f,
            new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);

        FixedBiomeSource voidSource = new FixedBiomeSource(biomes.getOrThrow(Biomes.THE_VOID));

        ScratchpadChunkGenerator scratchpadGenerator = new ScratchpadChunkGenerator(voidSource, 64);

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(DIM_TYPE), scratchpadGenerator);
        context.register(DIM_KEY, stem);
    }

    public static boolean TravelToOrFromDimension(ServerPlayer player) {
        if(player.level().dimension().equals(ScratchpadDimension.LEVEL))
            return LeaveDimension(player);
        else
            return TravelToDimension(player);
    }

    private static boolean TravelToDimension(ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if(player.level().dimension().equals(ScratchpadDimension.LEVEL)) {
            return false;
        }

        ServerLevel targetLevel = server.getLevel(ScratchpadDimension.LEVEL);
        if(targetLevel == null) {
            return false;
        }

        if(!player.isCreative()) {
            double d0 = 8.0, d1 = 5.0;
            Vec3 vec3 = Vec3.atBottomCenterOf(player.blockPosition());
            List<Monster> list = player.level().getEntitiesOfClass(Monster.class, new AABB(vec3.x() - d0, vec3.y() - d1, vec3.z() - d0, vec3.x() + d0, vec3.y() + d1, vec3.z() + d0), (monster) -> {
                return monster.isPreventingPlayerRest(player);
            });
            if (!list.isEmpty()) {
                DimensionalScratchpad.LOGGER.info("Cannot teleport; monsters nearby");
                player.displayClientMessage(Component.translatable("dimension.dimensionalscratchpad.not_safe"), true);
                return false;
            }
        }

        player.getCapability(ScratchpadState.SCRATCHPAD_STATE)
            .ifPresent(state -> state.savePlayerLastPos(player.level().dimension(), player.position()));

        Vec3 scratchpadPos = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getScratchpadPosition).orElse(new Vec3(0, 5, 0));
        
        player.teleportTo(targetLevel, scratchpadPos.x, scratchpadPos.y, scratchpadPos.z, player.getYRot(), player.getXRot());
        return true;
    }

    private static boolean LeaveDimension(ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if(!player.level().dimension().equals(ScratchpadDimension.LEVEL)) {
            return false;
        }

        player.getCapability(ScratchpadState.SCRATCHPAD_STATE)
            .ifPresent(state -> state.saveScratchpadPos(player.position()));

        ResourceKey<Level> prevDimension = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastDimension).orElse(Level.OVERWORLD);
        Vec3 lastPos = player.getCapability(ScratchpadState.SCRATCHPAD_STATE).map(IScratchpadState::getLastPosition).orElse(new Vec3(0,100,0));

        ServerLevel targetLevel = server.getLevel(prevDimension);
        if(targetLevel == null) {
            return false;
        }

        player.teleportTo(targetLevel, lastPos.x, lastPos.y, lastPos.z, player.getYRot(), player.getXRot());
        return true;
    }

}
