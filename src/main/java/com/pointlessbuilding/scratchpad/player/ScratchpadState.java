package com.pointlessbuilding.scratchpad.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
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
    public static final String TAG_LAST_POSX = "LastX";
    public static final String TAG_LAST_POSY = "LastY";
    public static final String TAG_LAST_POSZ = "LastZ";
    public static final String TAG_SCRATCH_POSX = "ScratchpadX";
    public static final String TAG_SCRATCH_POSY = "ScratchpadY";
    public static final String TAG_SCRATCH_POSZ = "ScratchpadZ";
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
    public static final String TAG_GAMETYPE = "LastGameType";


    private ResourceKey<Level> lastDimension = Level.OVERWORLD;
    private Vec3 lastPosition = new Vec3(0, 100, 0);
    private Vec3 scratchpadPosition = new Vec3(0, 5, 0);
    private NonNullList<ItemStack> lastItems = NonNullList.withSize(36, ItemStack.EMPTY);;
    private NonNullList<ItemStack> lastArmor = NonNullList.withSize(4, ItemStack.EMPTY);;
    private NonNullList<ItemStack> lastOffhand = NonNullList.withSize(1, ItemStack.EMPTY);;
    private float lastHealth = 20.0f;
    private int lastFoodLevel = 20;
    private float lastSaturation = 5.0f;
    private float lastExhaustion = 0.0f;
    private int lastFireTicks = 0;
    private int lastXp = 0;
    private Collection<MobEffectInstance> lastEffects = new ArrayList<>();
    private int lastAirSupply = 300;
    private float lastFallDistance = 0.0f;
    private GameType lastGameType = GameType.SURVIVAL;

    @Override
    public void savePlayerState(NonNullList<ItemStack> lastItems, NonNullList<ItemStack> lastArmor,
    NonNullList<ItemStack> lastOffhand, float lastHealth, int lastFoodLevel, float lastSaturation, float lastExhaustion, int lastFireTicks, int lastXp, Collection<MobEffectInstance> lastEffects,
    int lastAirSupply, float lastFallDistance, GameType lastGameType) {
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
        this.lastGameType = lastGameType;
    }

    @Override
    public void savePlayerLastPos(ResourceKey<Level> lastDimension, Vec3 lastPosition) {
        this.lastDimension = lastDimension;
        this.lastPosition = new Vec3(lastPosition.x, lastPosition.y, lastPosition.z);
    }

    @Override
    public void saveScratchpadPos(Vec3 scratchpadPosition) {
        this.scratchpadPosition = new Vec3(scratchpadPosition.x, scratchpadPosition.y, scratchpadPosition.z);
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
    public Vec3 getScratchpadPosition() {
        return scratchpadPosition;
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
    public GameType getLastGameType() {
        return lastGameType;
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

        tag.putString(TAG_LAST_DIM, lastDimension.location().toString());

        tag.putDouble(TAG_LAST_POSX, lastPosition.x);
        tag.putDouble(TAG_LAST_POSY, lastPosition.y);
        tag.putDouble(TAG_LAST_POSZ, lastPosition.z);

        tag.putDouble(TAG_SCRATCH_POSX, scratchpadPosition.x);
        tag.putDouble(TAG_SCRATCH_POSY, scratchpadPosition.y);
        tag.putDouble(TAG_SCRATCH_POSZ, scratchpadPosition.z);

        ListTag items = new ListTag();
        for(ItemStack stack : lastItems) items.add(stack.save(new CompoundTag()));
        tag.put(TAG_LAST_ITEMS, items);

        ListTag armor = new ListTag();
        for(ItemStack stack : lastArmor) armor.add(stack.save(new CompoundTag()));
        tag.put(TAG_LAST_ARMOR, armor);

        ListTag offhand = new ListTag();
        for(ItemStack stack : lastOffhand) offhand.add(stack.save(new CompoundTag()));
        tag.put(TAG_LAST_OFFHAND, offhand);

        ListTag effects = new ListTag();
        for(MobEffectInstance effect : lastEffects) effects.add(effect.save(new CompoundTag()));
        tag.put(TAG_LAST_EFFECTS, effects);

        tag.putFloat(TAG_LAST_HEALTH, lastHealth);
        tag.putInt(TAG_LAST_FOOD_LVL, lastFoodLevel);
        tag.putFloat(TAG_LAST_SATURATION, lastSaturation);
        tag.putFloat(TAG_LAST_EXHAUSTION, lastExhaustion);
        tag.putInt(TAG_LAST_FIRETICKS, lastFireTicks);
        tag.putInt(TAG_LAST_XP, lastXp);
        tag.putInt(TAG_LAST_AIR, lastAirSupply);
        tag.putFloat(TAG_LAST_FALLDIST, lastFallDistance);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        lastDimension = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.parse(tag.getString(TAG_LAST_DIM))
        );

        lastPosition = new Vec3(
            tag.getDouble(TAG_LAST_POSX),
            tag.getDouble(TAG_LAST_POSY),
            tag.getDouble(TAG_LAST_POSZ)
        );

        scratchpadPosition = new Vec3(
            tag.getDouble(TAG_SCRATCH_POSX),
            tag.getDouble(TAG_SCRATCH_POSY),
            tag.getDouble(TAG_SCRATCH_POSZ)
        );

        lastItems = NonNullList.withSize(36, ItemStack.EMPTY);
        ListTag items = tag.getList(TAG_LAST_ITEMS, Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(items.size(), lastItems.size()); i++)
            lastItems.set(i, ItemStack.of(items.getCompound(i)));

        lastArmor = NonNullList.withSize(4, ItemStack.EMPTY);
        ListTag armor = tag.getList(TAG_LAST_ARMOR, Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(armor.size(), lastArmor.size()); i++)
            lastArmor.set(i, ItemStack.of(armor.getCompound(i)));

        lastOffhand = NonNullList.withSize(1, ItemStack.EMPTY);
        ListTag offhand = tag.getList(TAG_LAST_OFFHAND, Tag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(offhand.size(), lastOffhand.size()); i++)
            lastOffhand.set(i, ItemStack.of(offhand.getCompound(i)));

        lastEffects = new ArrayList<>();
        ListTag effects = tag.getList(TAG_LAST_EFFECTS, Tag.TAG_COMPOUND);
        for (int i = 0; i < effects.size(); i++)
            lastEffects.add(MobEffectInstance.load(effects.getCompound(i)));

        lastHealth = tag.getFloat(TAG_LAST_HEALTH);
        lastFoodLevel = tag.getInt(TAG_LAST_FOOD_LVL);
        lastSaturation = tag.getFloat(TAG_LAST_SATURATION);
        lastExhaustion = tag.getFloat(TAG_LAST_EXHAUSTION);
        lastFireTicks = tag.getInt(TAG_LAST_FIRETICKS);
        lastXp = tag.getInt(TAG_LAST_XP);
        lastAirSupply = tag.getInt(TAG_LAST_AIR);
        lastFallDistance = tag.getFloat(TAG_LAST_FALLDIST);
    }
    
}
