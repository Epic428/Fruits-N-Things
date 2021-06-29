package com.teamresourceful.fruits.common.blockentities;

import com.teamresourceful.fruits.common.lib.Constants;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrushingBarrelBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private int fluidAmount;

    public CrushingBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.RAIN_BARREL_ENTITY, blockPos, blockState);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return save(tag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        if (compoundTag.contains(Constants.FLUID_AMOUNT)) {
            fluidAmount= compoundTag.getInt(Constants.FLUID_AMOUNT);
        }
        super.load(compoundTag);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putInt(Constants.FLUID_AMOUNT, fluidAmount);
        return super.save(compoundTag);
    }
}
