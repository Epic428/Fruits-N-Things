package com.teamresourceful.fruits.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.SweetBerryBushBlock.AGE;
import static net.minecraft.world.level.block.SweetBerryBushBlock.MAX_AGE;

public class PickablePlantBlock extends BushBlock implements BonemealableBlock {

    public static final VoxelShape SAPLING_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    public static final VoxelShape FULL_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    private final Supplier<Item> plant;
    private int max;

    public PickablePlantBlock(Properties properties, Supplier<Item> plant) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        this.plant = plant;
    }

    public PickablePlantBlock(Properties properties, Supplier<Item> plant, int max) {
        this(properties, plant);
        this.max = max;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return state.getValue(AGE) == 0 ? SAPLING_SHAPE : state.getValue(AGE) < MAX_AGE ? FULL_SHAPE : super.getShape(state,blockGetter,pos,context);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue(AGE) < MAX_AGE && random.nextInt(5) == 0 && level.getRawBrightness(pos.above(), 0) >= 9) {
            level.setBlock(pos, state.cycle(AGE), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        boolean maxAged = state.getValue(AGE) == MAX_AGE;
        if (!maxAged && player.getItemInHand(hand).is(Items.BONE_MEAL)) return InteractionResult.PASS;
        if (state.getValue(AGE) < 2) return super.use(state,level,pos,player,hand,hitResult);

        if (this.max == 0) popResource(level, pos, new ItemStack(plant.get(), maxAged ? 2 + level.random.nextInt(2) : 1));
        else popResource(level, pos, new ItemStack(plant.get(), maxAged ? this.max : 1));
        level.setBlock(pos, state.setValue(AGE, 1), 2);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean bl) {
        return state.getValue(AGE) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.cycle(AGE), 2);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity && !(entity instanceof Fox) && !(entity instanceof Bee)) {
            entity.makeStuckInBlock(blockState, new Vec3(0.8, 0.75, 0.8));
            if (!level.isClientSide && blockState.getValue(AGE) > 0) entity.hurt(DamageSource.SWEET_BERRY_BUSH, 0.5F);
        }
    }

    public BlockState getMaxAgeState() {
        return this.defaultBlockState().setValue(AGE, MAX_AGE);
    }
}
