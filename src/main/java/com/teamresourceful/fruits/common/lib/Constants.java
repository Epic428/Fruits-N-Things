package com.teamresourceful.fruits.common.lib;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Constants {
    public static final String UTILITY_CLASS = "utility class!";
    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    //Constants below this line

    public static final String FLUID_AMOUNT = "fluidAmount";

    public static class BlockFlags {
        /**
         * Calls
         * {@link Block#neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)
         * neighborChanged} on surrounding blocks (with isMoving as false). Also updates comparator output state.
         */
        public static final int NOTIFY_NEIGHBORS     = 0b001;
        /**
         * Calls {@link Level#notifyBlockUpdate(BlockPos, BlockState, BlockState, int)}.<br>
         * Server-side, this updates all the path-finding navigators.
         */
        public static final int BLOCK_UPDATE         = 0b010;
        /**
         * Stops the blocks from being marked for a render update
         */
        public static final int NO_RERENDER          = 0b100;
        /**
         * Makes the block be re-rendered immediately, on the main thread.
         * If NO_RERENDER is set, then this will be ignored
         */
        public static final int RERENDER_MAIN_THREAD = 0b1000;
        /**
         * Causes neighbor updates to be sent to all surrounding blocks (including
         * diagonals). This in turn will call
         * {@link Block#updateDiagonalNeighbors(BlockState, IWorld, BlockPos, int)
         * updateDiagonalNeighbors} on both old and new states, and
         * {@link Block#updateNeighbors(BlockState, IWorld, BlockPos, int)
         * updateNeighbors} on the new state.
         */
        public static final int UPDATE_NEIGHBORS     = 0b10000;

        /**
         * Prevents neighbor changes from spawning item drops, used by
         * {@link Block#replaceBlock(BlockState, BlockState, IWorld, BlockPos, int)}.
         */
        public static final int NO_NEIGHBOR_DROPS    = 0b100000;

        /**
         * Tell the block being changed that it was moved, rather than removed/replaced,
         * the boolean value is eventually passed to
         * {@link Block#onReplaced(BlockState, World, BlockPos, BlockState, boolean)}
         * as the last parameter.
         */
        public static final int IS_MOVING            = 0b1000000;

        public static final int DEFAULT = NOTIFY_NEIGHBORS | BLOCK_UPDATE;
        public static final int DEFAULT_AND_RERENDER = DEFAULT | RERENDER_MAIN_THREAD;
    }
}
