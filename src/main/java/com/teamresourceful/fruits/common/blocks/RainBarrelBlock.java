package com.teamresourceful.fruits.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RainBarrelBlock extends Block {

    protected static final VoxelShape SHAPE = createShape();

    public RainBarrelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    private static VoxelShape createShape() {
        VoxelShape shape = Block.box(1, 2, 0, 15, 16, 1);
        shape = Shapes.or(shape, Block.box(1, 2, 15, 15, 16, 16));
        shape = Shapes.or(shape, Block.box(15, 2, 1, 16, 16, 15));
        shape = Shapes.or(shape, Block.box(0, 2, 1, 1, 16, 15));
        shape = Shapes.or(shape, Block.box(1, 2, 1, 15, 3, 15));
        shape = Shapes.or(shape, Block.box(13, 0, 1, 15, 2, 3));
        shape = Shapes.or(shape, Block.box(1, 0, 1, 3, 2, 3));
        shape = Shapes.or(shape, Block.box(1, 0, 13, 3, 2, 15));
        shape = Shapes.or(shape, Block.box(13, 0, 13, 15, 2, 15));

        return shape;
    }
}
