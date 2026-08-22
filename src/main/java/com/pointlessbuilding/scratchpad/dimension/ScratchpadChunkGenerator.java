package com.pointlessbuilding.scratchpad.dimension;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pointlessbuilding.scratchpad.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ScratchpadChunkGenerator extends ChunkGenerator{

    private final int shellSize;

    public static final Codec<ScratchpadChunkGenerator> CODEC =
    RecordCodecBuilder.create(instance -> instance.group(
        BiomeSource.CODEC.fieldOf("biome_source")
            .forGetter(ScratchpadChunkGenerator::getBiomeSource),
        Codec.INT.fieldOf("shell_size")
            .forGetter(ScratchpadChunkGenerator::getShellSize)
    ).apply(instance, ScratchpadChunkGenerator::new));

    public ScratchpadChunkGenerator(BiomeSource pBiomeSource, int shellSize) {
        super(pBiomeSource);
        this.shellSize = shellSize;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk) {
         return CompletableFuture.supplyAsync(() -> {
            int spacing = shellSize + 23;
            BlockState shell = Registration.BLANK.get().defaultBlockState();

            int minY = pChunk.getMinBuildHeight();
            int maxY = minY + pChunk.getHeight();

            for (int x = 0; x < 16; x++) {
                int worldX = pChunk.getPos().getMinBlockX() + x;

                for (int z = 0; z < 16; z++) {
                    int worldZ = pChunk.getPos().getMinBlockZ() + z;

                    for (int y = minY; y < maxY; y++) {
                        
                        int modX = Math.floorMod(worldX, spacing);
                        int modZ = Math.floorMod(worldZ, spacing);

                        if(modX < shellSize && modZ < shellSize && (y > -1 && y < shellSize)) continue;

                        pChunk.setBlockState(new BlockPos(worldX, y, worldZ), shell, false);

                    }
                }
            }

            return pChunk;
        }, pExecutor);
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, RandomState pRandom, BiomeManager pBiomeManager, StructureManager pStructureManager, ChunkAccess pChunk, Carving pStep) {
    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {
    }

    @Override
    public int getGenDepth() {
        return Math.max(shellSize + 16, 256) + 16;
    }

    @Override
    public int getSeaLevel() {
        return -63;
    }

    @Override
    public int getMinY() {
        return -16;
    }

    public int getShellSize() {
        return shellSize;
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Types pType, LevelHeightAccessor pLevel, RandomState pRandom) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pHeight, RandomState pRandom) {
        int spacing = shellSize + 23;
        int modX = Math.floorMod(pX, spacing);
        int modZ = Math.floorMod(pZ, spacing);

        boolean shellX = modX >= shellSize;
        boolean shellZ = modZ >= shellSize;

        BlockState[] states = new BlockState[pHeight.getMaxBuildHeight() - pHeight.getMinBuildHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = pHeight.getMinBuildHeight() + i;

            boolean shellY = y < 0 || y >= shellSize;

            states[i] = (shellX || shellZ || shellY) ? Registration.BLANK.get().defaultBlockState() : Blocks.AIR.defaultBlockState();
        }

        return new NoiseColumn(pHeight.getMinBuildHeight(), states);
    }

    @Override
    public void addDebugScreenInfo(List<String> pInfo, RandomState pRandom, BlockPos pPos) {
        pInfo.add("Scratchpad shell size: " + shellSize);
    }
    
}
