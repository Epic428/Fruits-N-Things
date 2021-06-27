package com.teamresourceful.fruits.common.blocks;

import com.teamresourceful.fruits.common.blockentities.RainBarrelBlockEntity;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

@SuppressWarnings("deprecation")
public class RainBarrelBlock extends Block implements EntityBlock {

    protected static final VoxelShape SHAPE = createShape();

    public RainBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
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

    @Override
    public void handlePrecipitation(BlockState blockState, Level level, BlockPos blockPos, Biome.Precipitation precipitation) {
        if (precipitation != Biome.Precipitation.RAIN) return;
        Optional<RainBarrelBlockEntity> blockEntity = level.getBlockEntity(blockPos, ModBlockEntities.RAIN_BARREL_ENTITY);
        blockEntity.ifPresent(barrelBlockEntity -> barrelBlockEntity.fillBarrel(250));
    }

    private void receiveStalactiteDrip(BlockState blockState, Level level, BlockPos blockPos, Fluid fluid) {
        if (fluid == Fluids.WATER) {
            level.levelEvent(1047, blockPos, 0);
            level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
            Optional<RainBarrelBlockEntity> blockEntity = level.getBlockEntity(blockPos, ModBlockEntities.RAIN_BARREL_ENTITY);
            blockEntity.ifPresent(barrelBlockEntity -> barrelBlockEntity.fillBarrel(10));
        } else if (fluid == Fluids.LAVA && Boolean.FALSE.equals(blockState.getValue(BlockStateProperties.WATERLOGGED))) {
            level.setBlockAndUpdate(blockPos.above(), Blocks.FIRE.defaultBlockState());
            level.levelEvent(1046, blockPos, 0);
            level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
        }

    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        var blockPos2 = PointedDripstoneBlock.findStalactiteTipAboveCauldron(serverLevel, blockPos);
        if (blockPos2 != null) {
            var fluid = PointedDripstoneBlock.getCauldronFillFluidType(serverLevel, blockPos2);
            if (fluid != Fluids.EMPTY) {
                this.receiveStalactiteDrip(blockState, serverLevel, blockPos, fluid);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        // cache the fluid amount before removing the block so the item drops and then the fluid spawns
        Optional<RainBarrelBlockEntity> blockEntity = level.getBlockEntity(blockPos, ModBlockEntities.RAIN_BARREL_ENTITY);
        super.onRemove(blockState, level, blockPos, blockState2, bl);
        blockEntity.ifPresent(RainBarrelBlockEntity::spawnWater);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RainBarrelBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack bucket = player.getItemInHand(interactionHand);
        if ((!bucket.is(Items.BUCKET) && !bucket.is(Items.GLASS_BOTTLE)) || level == null) return InteractionResult.PASS;
        if (level.getBlockEntity(blockPos) instanceof RainBarrelBlockEntity blockEntity) {
            boolean isBucket = bucket.is(Items.BUCKET);
            int fluidAmount = blockEntity.getFluidAmount();
            if ((isBucket && fluidAmount >= 1000) || (!isBucket && fluidAmount >= 250)) {
                if (isBucket) {
                    fillContainer(player, interactionHand, bucket, blockEntity, Items.WATER_BUCKET.getDefaultInstance(), 1000);
                    level.playSound(null, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    fillContainer(player, interactionHand, bucket, blockEntity, PotionUtils.setPotion(Items.POTION.getDefaultInstance(), Potions.WATER), 250);
                    level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    private void fillContainer(Player player, InteractionHand interactionHand, ItemStack bucket, RainBarrelBlockEntity blockEntity, ItemStack itemStack, int i) {
        if (bucket.getCount() > 1) {
            player.addItem(itemStack);
            bucket.shrink(1);
        } else {
            player.setItemInHand(interactionHand, itemStack);
        }
        blockEntity.removeFluid(i);
    }
}
