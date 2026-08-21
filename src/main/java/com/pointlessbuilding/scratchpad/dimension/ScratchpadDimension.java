package com.pointlessbuilding.scratchpad.dimension;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import com.pointlessbuilding.scratchpad.DimensionalScratchpad;
import com.pointlessbuilding.scratchpad.Registration;
import com.pointlessbuilding.scratchpad.player.IScratchpadState;
import com.pointlessbuilding.scratchpad.player.ScratchpadState;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
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
            0,
            32,
            32,
            BlockTags.INFINIBURN_OVERWORLD,
            new ResourceLocation(DimensionalScratchpad.MODID, "scratchpad"),
            1.0f,
            new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);

        FlatLevelGeneratorSettings settings = new FlatLevelGeneratorSettings(
            Optional.empty(),
            biomes.getOrThrow(Biomes.THE_VOID),
            List.of()
        ).withBiomeAndLayers(
            List.of(new FlatLayerInfo(5, Registration.BLANK.get())),
            Optional.of(HolderSet.direct()),
            biomes.getOrThrow(Biomes.THE_VOID)
        );

        FlatLevelSource flatGenerator = new FlatLevelSource(settings);

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(DIM_TYPE), flatGenerator);
        context.register(DIM_KEY, stem);
    }

    public static boolean TravelToDimension(ServerPlayer player) {
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
            .ifPresent(state -> state.savePlayerPos(player.level().dimension(), player.position()));
        
        player.teleportTo(targetLevel, 0.5, 10, 0.5, player.getYRot(), player.getXRot());
        return true;
    }

    public static boolean LeaveDimension(ServerPlayer player) {
        MinecraftServer server = player.getServer();

        if(!player.level().dimension().equals(ScratchpadDimension.LEVEL)) {
            return false;
        }

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
