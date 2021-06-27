package com.teamresourceful.fruits.common.blockentities;

import com.teamresourceful.fruits.common.lib.Constants;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public class RainBarrelBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private int fluidAmount;

    public RainBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.RAIN_BARREL_ENTITY, blockPos, blockState);
    }

    public void fillBarrel(int amount) {
        if (fluidAmount < 4000) {
            fluidAmount += amount;
            if (this.level != null) this.level.setBlockAndUpdate(this.getBlockPos(), getBlockState().setValue(BlockStateProperties.WATERLOGGED, true));
            sync();
        }
    }

    public int getFluidAmount() {
        return Math.min(fluidAmount, 4000);
    }

    public float getPercentFilled() {
        return Math.min(fluidAmount / 4000f, 1.0f);
    }

    public void removeFluid(int amount) {
        fluidAmount = Math.max(fluidAmount - amount, 0);
        if (fluidAmount == 0) {
            getBlockState().setValue(BlockStateProperties.WATERLOGGED, false);
        }
    }

    public void spawnWater() {
        if (fluidAmount >= 1000 && this.level != null) {
            Direction.Plane.HORIZONTAL.forEach(direction -> {
                if (this.level.getBlockState(this.getBlockPos().relative(direction)).canBeReplaced(Fluids.WATER)) {
                    this.level.setBlockAndUpdate(this.getBlockPos().relative(direction), Fluids.WATER.defaultFluidState().createLegacyBlock().setValue(BlockStateProperties.LEVEL, 7));
                }
            });
            this.level.setBlockAndUpdate(this.getBlockPos(), Fluids.WATER.defaultFluidState().createLegacyBlock().setValue(BlockStateProperties.LEVEL, 8));
        }
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
