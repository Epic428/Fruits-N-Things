package com.teamresourceful.fruits.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AdvancedLeavesBlock extends LeavesBlock {

    public static Set<BlockPos> offsets = new HashSet<>();
    static {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    if (x == 1 && y == 1 && z == 1) continue;
                    offsets.add(new BlockPos(x-1,y-1,z-1));
                }
            }
        }
    }


    public AdvancedLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        serverLevel.setBlock(blockPos, updateDistance(blockState, serverLevel, blockPos), 3);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        if (!(Boolean)blockState.getValue(PERSISTENT) && blockState.getValue(DISTANCE) == 7) {
            BlockState state = updateDistance(blockState, serverLevel, blockPos);
            if (state.getValue(DISTANCE) != 7) {
                serverLevel.setBlock(blockPos, state, 3);
                return;
            }
            dropResources(blockState, serverLevel, blockPos);
            serverLevel.removeBlock(blockPos, false);
        }

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, true), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    //This makes it so that leaves in all directions update including leaves not directly touching another leaf.
    //This allows for trees to not need to have leaves directly touch each other.
    //It does have performance costs but from the bit that ive looked there is no other easy ways to do it.

    @Override
    public void updateIndirectNeighbourShapes(BlockState blockState, LevelAccessor levelAccessor, BlockPos pos, int i, int j) {
        int distance = blockState.getValue(DISTANCE);
        if (distance == 7) return;

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (BlockPos offset : offsets) {
            mutableBlockPos.setWithOffset(pos, offset);
            BlockState state = levelAccessor.getBlockState(mutableBlockPos);
            if (!state.hasProperty(DISTANCE)) continue;
            if (levelAccessor.getBlockState(mutableBlockPos).getValue(DISTANCE) != distance){
                levelAccessor.setBlock(mutableBlockPos, updateDistance(levelAccessor.getBlockState(mutableBlockPos), levelAccessor, mutableBlockPos), 3);
            }
        }


    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        int i = getDistanceAt(blockState2) + 1;
        if (i != 1 || blockState.getValue(DISTANCE) != i) {
            levelAccessor.getBlockTicks().scheduleTick(blockPos, this, 1);
        }

        return blockState;
    }

    private static BlockState updateDistance(BlockState blockState, LevelAccessor levelAccessor, BlockPos pos) {
        int i = 7;

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (BlockPos offset : offsets) {
            mutableBlockPos.setWithOffset(pos, offset);
            i = Math.min(i, getDistanceAt(levelAccessor.getBlockState(mutableBlockPos)) + 1);
            if (i == 1) break;
        }

        return blockState.setValue(DISTANCE, i);
    }

    private static int getDistanceAt(BlockState blockState) {
        return blockState.is(BlockTags.LOGS) ? 0 : blockState.getBlock() instanceof LeavesBlock ? blockState.getValue(DISTANCE) : 7;
    }
}
