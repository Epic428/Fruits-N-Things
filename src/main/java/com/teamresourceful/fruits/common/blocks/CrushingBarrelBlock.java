package com.teamresourceful.fruits.common.blocks;

import com.teamresourceful.fruits.common.blockentities.CrushingBarrelBlockEntity;
import com.teamresourceful.fruits.common.recipes.CrushingRecipe;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import com.teamresourceful.fruits.common.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrushingBarrelBlock extends Block implements EntityBlock {

    protected static final VoxelShape SHAPE = createShape();

    public CrushingBarrelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    private static VoxelShape createShape() {
        VoxelShape shape = Block.box(1, 4, 0, 15, 11, 1);
        shape = Shapes.or(shape, Block.box(1, 4, 15, 15, 11, 16));
        shape = Shapes.or(shape, Block.box(15, 4, 1, 16, 11, 15));
        shape = Shapes.or(shape, Block.box(0, 4, 1, 1, 11, 15));
        shape = Shapes.or(shape, Block.box(1, 3, 1, 15, 4, 15));
        shape = Shapes.or(shape, Block.box(12, 1, 2, 14, 3, 4));
        shape = Shapes.or(shape, Block.box(11, 0, 1, 15, 1, 5));
        shape = Shapes.or(shape, Block.box(12, 1, 12, 14, 3, 14));
        shape = Shapes.or(shape, Block.box(11, 0, 11, 15, 1, 15));
        shape = Shapes.or(shape, Block.box(1, 0, 11, 5, 1, 15));
        shape = Shapes.or(shape, Block.box(2, 1, 12, 4, 3, 14));
        shape = Shapes.or(shape, Block.box(1, 0, 1, 5, 1, 5));
        shape = Shapes.or(shape, Block.box(2, 1, 2, 4, 3, 4));
        return shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level == null) return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(hand);

        if (level.getBlockEntity(pos) instanceof CrushingBarrelBlockEntity blockEntity){
            if (!blockEntity.getFluid().isSame(Fluids.EMPTY) || !blockEntity.getItem(1).isEmpty()){
                if (stack.is(Items.BUCKET) && !blockEntity.getFluid().isSame(Fluids.EMPTY)){
                    ItemStack fluidBucket = blockEntity.getFluid().getBucket().getDefaultInstance();
                    if (stack.getCount() > 1) {
                        stack.shrink(1);
                        player.addItem(fluidBucket);
                    }
                    else {
                        player.setItemInHand(hand, fluidBucket);
                    }
                    blockEntity.removeFluid();
                }else {
                    player.addItem(blockEntity.getItem(1));
                    blockEntity.clearContent();
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        if (level.getBlockEntity(pos) instanceof CrushingBarrelBlockEntity blockEntity){
            if (!blockEntity.getFluid().isSame(Fluids.EMPTY) || !blockEntity.getItem(1).isEmpty()) return InteractionResult.PASS;
            if (!blockEntity.getItem(0).isEmpty() && !blockEntity.getItem(0).is(stack.getItem())) return InteractionResult.PASS;
        }

        CrushingRecipe recipe = level.getRecipeManager().getRecipeFor(ModRecipes.CRUSHING_RECIPE_TYPE, new SimpleContainer(stack), level).orElse(null);
        if (recipe == null) return InteractionResult.PASS;
        if (level.getBlockEntity(pos) instanceof CrushingBarrelBlockEntity blockEntity){
            if (blockEntity.getItem(0).getCount() >= recipe.getIngredient().getItems()[0].getCount()) return InteractionResult.PASS;

            ItemStack copy = stack.copy();
            copy.setCount(1);
            if (blockEntity.getItem(0).isEmpty()) blockEntity.setItem(0, copy);
            else blockEntity.getItem(0).grow(1);
            stack.shrink(1);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, result);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrushingBarrelBlockEntity(blockPos, blockState);
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
        Optional<CrushingBarrelBlockEntity> blockEntity = level.getBlockEntity(blockPos, ModBlockEntities.CRUSHING_BARREL_ENTITY);
        blockEntity.ifPresent(CrushingBarrelBlockEntity::increasePresses);
        super.fallOn(level, blockState, blockPos, entity, f);
    }
}
