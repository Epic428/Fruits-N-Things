package com.teamresourceful.fruits.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PickableTreePlantBlock extends PickablePlantBlock{

    private final Supplier<Block> leafBlock;

    public PickableTreePlantBlock(Properties properties, Supplier<Item> plant, Supplier<Block> leafBlock) {
        super(properties, plant);
        this.leafBlock = leafBlock;
    }

    public PickableTreePlantBlock(Properties properties, int max, Supplier<Item> plant, Supplier<Block> leafBlock) {
        super(properties, plant, max);
        this.leafBlock = leafBlock;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {}

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    public boolean canPlaceUnder(BlockState state){
        return state.is(leafBlock.get());
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canPlaceUnder(levelReader.getBlockState(blockPos.above()));
    }
}
