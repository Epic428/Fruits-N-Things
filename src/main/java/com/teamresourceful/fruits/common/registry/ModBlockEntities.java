package com.teamresourceful.fruits.common.registry;

import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blockentities.RainBarrelBlockEntity;
import com.teamresourceful.fruits.common.lib.Constants;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    private ModBlockEntities() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static final BlockEntityType<RainBarrelBlockEntity> RAIN_BARREL_ENTITY = registerBlockEntity("rain_barrel", FabricBlockEntityTypeBuilder.create(RainBarrelBlockEntity::new, ModBlocks.RAIN_BARREL));

    public static void onInitialize() {
        //class load
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, FabricBlockEntityTypeBuilder<T>  blockEntityTypeBuilder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Fruits.MOD_ID, name), blockEntityTypeBuilder.build());
    }
}
