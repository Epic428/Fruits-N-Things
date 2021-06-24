package com.teamresourceful.fruits.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.SweetBerryBushBlock.AGE;
import static net.minecraft.world.level.block.SweetBerryBushBlock.MAX_AGE;

public class PickableCactusPlantBlock extends PickablePlantBlock {

    public static final VoxelShape MID_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    public PickableCactusPlantBlock(Properties properties, Supplier<Item> plant) {
        super(properties, plant);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(BlockTags.SAND);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (!level.isClientSide && blockState.getValue(AGE) > 0) entity.hurt(DamageSource.CACTUS, 0.5F);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return state.getValue(AGE) == 0 ? SAPLING_SHAPE : state.getValue(AGE) < MAX_AGE ? MID_SHAPE : FULL_SHAPE;
    }
}
