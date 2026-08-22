package com.pointlessbuilding.scratchpad.player;

import java.util.Collection;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IScratchpadState {

    public void savePlayerState(NonNullList<ItemStack> lastItems, NonNullList<ItemStack> lastArmor,
    NonNullList<ItemStack> lastOffhand, float lastHealth, int lastFoodLevel, float lastSaturation, float lastExhaustion, int lastFireTicks, int lastXp, Collection<MobEffectInstance> lastEffects,
    int lastAirSupply, float lastFallDistance, GameType lastGameType);
    public void savePlayerLastPos(ResourceKey<Level> lastDimension, Vec3 lastPosition);
    public void saveScratchpadPos(Vec3 scratchpadPosition);

    public ResourceKey<Level> getLastDimension();
    public Vec3 getLastPosition();
    public Vec3 getScratchpadPosition();
    public NonNullList<ItemStack> getLastItems();
    public NonNullList<ItemStack> getLastArmor();
    public NonNullList<ItemStack> getLastOffhand();
    public float getLastHealth();
    public int getLastFoodLevel();
    public float getLastSaturation();
    public float getLastExhaustion();
    public int getLastFireTicks();
    public int getLastXp();
    public Collection<MobEffectInstance> getLastEffects();
    public int getLastAirSupply();
    public float getLastFallDistance();
    public GameType getLastGameType();

    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag tag);
}
