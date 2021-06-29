package com.teamresourceful.fruits.common.blocks;

import com.teamresourceful.fruits.common.blockentities.CrushingBarrelBlockEntity;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrushingBarrelBlock extends Block implements EntityBlock {
    public CrushingBarrelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrushingBarrelBlockEntity(blockPos, blockState);
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
        Optional<CrushingBarrelBlockEntity> blockEntity = level.getBlockEntity(blockPos, ModBlockEntities.CRUSHING_BARREL_ENTITY);
        blockEntity.ifPresent(CrushingBarrelBlockEntity::processRecipe);
        super.fallOn(level, blockState, blockPos, entity, f);
    }
}
