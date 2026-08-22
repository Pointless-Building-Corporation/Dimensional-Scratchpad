package com.pointlessbuilding.scratchpad.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class ScratchpadData extends SavedData{

    private int nextSlot = 0;

    public ScratchpadData() {}

    public static ScratchpadData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
            ScratchpadData::load,
            ScratchpadData::new,
            "scratchpad_data"
        );
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        return tag;
    }

    public static ScratchpadData load(CompoundTag tag) {
        ScratchpadData data = new ScratchpadData();
        data.nextSlot = tag.getInt("nextSlot");
        return data;
    }

    public int allocateSlot() {
        int slot = nextSlot++;
        setDirty();
        return slot;
    }

}