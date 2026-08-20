package com.pointlessbuilding.scratchpad.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ScratchpadState implements IScratchpadState, ICapabilityProvider, INBTSerializable<CompoundTag>{

    public static final String TAG_LAST_DIM = "LastDimension";
    public static final String TAG_LAST_POS = "LastPosition";
    public static final String TAG_LAST_ITEMS = "LastItems";
    public static final String TAG_LAST_ARMOR = "LastArmor";
    public static final String TAG_LAST_OFFHAND = "LastOffhand";
    public static final String TAG_LAST_HEALTH = "LastHealth";
    public static final String TAG_LAST_FOOD_LVL = "LastFoodLevel";
    public static final String TAG_LAST_SATURATION = "LastSaturation";
    public static final String TAG_LAST_EXHAUSTION = "LastExhaustion";
    public static final String TAG_LAST_FIRETICKS = "LastFireTicks";
    public static final String TAG_LAST_XP = "LastXp";
    public static final String TAG_LAST_EFFECTS = "LastEffects";
    public static final String TAG_LAST_AIR = "LastAirSupply";
    public static final String TAG_LAST_FALLDIST = "LastFallDistance";
    public static final String TAG_FLY = "MayFly";
    public static final String TAG_INVULN = "Invulnerable";
    public static final String TAG_INSTABUILD = "Instabuild";


    private ResourceKey<Level> lastDimension = Level.OVERWORLD;
    private Vec3 lastPosition = new Vec3(0, 100, 0);
    private NonNullList<ItemStack> lastItems = NonNullList.withSize(36, ItemStack.EMPTY);;
    private NonNullList<ItemStack> lastArmor = NonNullList.withSize(36, ItemStack.EMPTY);;
    private NonNullList<ItemStack> lastOffhand = NonNullList.withSize(36, ItemStack.EMPTY);;
    private float lastHealth = 20.0f;
    private int lastFoodLevel = 20;
    private float lastSaturation = 5.0f;
    private float lastExhaustion = 0.0f;
    private int lastFireTicks = 0;
    private int lastXp = 0;
    private Collection<MobEffectInstance> lastEffects = new ArrayList<>();
    private int lastAirSupply = 300;
    private float lastFallDistance = 0.0f;
    private boolean mayFly = false;
    private boolean invulnerable = false;
    private boolean instabuild = false;

    @Override
    public void savePlayerState(NonNullList<ItemStack> lastItems, NonNullList<ItemStack> lastArmor,
    NonNullList<ItemStack> lastOffhand, float lastHealth, int lastFoodLevel, float lastSaturation, float lastExhaustion, int lastFireTicks, int lastXp, Collection<MobEffectInstance> lastEffects,
    int lastAirSupply, float lastFallDistance, boolean mayFly, boolean invulnerable, boolean instabuild) {
        this.lastItems = NonNullList.withSize(lastItems.size(), ItemStack.EMPTY);
        for (int i = 0; i < lastItems.size(); i++)
            this.lastItems.set(i, lastItems.get(i).copy());

        this.lastArmor = NonNullList.withSize(lastArmor.size(), ItemStack.EMPTY);
        for (int i = 0; i < lastArmor.size(); i++)
            this.lastArmor.set(i, lastArmor.get(i).copy());

        this.lastOffhand = NonNullList.withSize(lastOffhand.size(), ItemStack.EMPTY);
        for (int i = 0; i < lastOffhand.size(); i++)
            this.lastOffhand.set(i, lastOffhand.get(i).copy());

        this.lastEffects = lastEffects.stream()
                .map(MobEffectInstance::new)
                .collect(Collectors.toCollection(ArrayList::new));

        this.lastHealth = lastHealth;
        this.lastFoodLevel = lastFoodLevel;
        this.lastSaturation = lastSaturation;
        this.lastExhaustion = lastExhaustion;
        this.lastFireTicks = lastFireTicks;
        this.lastXp = lastXp;
        this.lastAirSupply = lastAirSupply;
        this.lastFallDistance = lastFallDistance;
        this.mayFly = mayFly;
        this.invulnerable = invulnerable;
        this.instabuild = instabuild;
    
    }

    public void savePlayerPos(ResourceKey<Level> lastDimension, Vec3 lastPosition) {
        this.lastDimension = lastDimension;
        this.lastPosition = new Vec3(lastPosition.x, lastPosition.y, lastPosition.z);
    }

    @Override
    public ResourceKey<Level> getLastDimension() {
        return lastDimension;
    }

    @Override
    public Vec3 getLastPosition() {
        return lastPosition;
    }

    @Override
    public NonNullList<ItemStack> getLastItems() {
        return lastItems;
    }

    @Override
    public NonNullList<ItemStack> getLastArmor() {
        return lastArmor;
    }

    @Override
    public NonNullList<ItemStack> getLastOffhand() {
        return lastOffhand;
    }

    @Override
    public float getLastHealth() {
        return lastHealth;
    }

    @Override
    public int getLastFoodLevel() {
        return lastFoodLevel;
    }

    @Override
    public float getLastSaturation() {
        return lastSaturation;
    }

    @Override
    public float getLastExhaustion() {
        return lastExhaustion;
    }

    @Override
    public int getLastFireTicks() {
        return lastFireTicks;
    }

    @Override
    public int getLastXp() {
        return lastXp;
    }

    @Override
    public Collection<MobEffectInstance> getLastEffects() {
        return lastEffects;
    }

    @Override
    public int getLastAirSupply() {
        return lastAirSupply;
    }

    @Override
    public float getLastFallDistance() {
        return lastFallDistance;
    }

    @Override
    public boolean getMayFly() {
        return mayFly;
    }

    @Override
    public boolean getInvulnerable() {
        return invulnerable;
    }

    @Override
    public boolean getInstabuild() {
        return instabuild;
    }

    //Capability stuff

    public static final Capability<IScratchpadState> SCRATCHPAD_STATE = CapabilityManager.get(new CapabilityToken<>() {});

    private final LazyOptional<IScratchpadState> optional = LazyOptional.of(() -> this);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == SCRATCHPAD_STATE ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        
    }
    
}
