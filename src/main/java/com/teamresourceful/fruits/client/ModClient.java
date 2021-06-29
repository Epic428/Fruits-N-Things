package com.teamresourceful.fruits.client;

import com.teamresourceful.fruits.client.renderers.RainBarrelRenderer;
import com.teamresourceful.fruits.common.lib.Constants;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import com.teamresourceful.fruits.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;

public class ModClient {
    private ModClient() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static void onInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAIN_BARREL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OIL_PRESS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKBERRY_BUSH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUEBERRY_BUSH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALM_LEAVES, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_BLOCK, RenderType.cutout());

        BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntities.RAIN_BARREL_ENTITY, RainBarrelRenderer::new);
        registerLeavesColor(ModBlocks.PALM_LEAVES);
    }


    private static void registerLeavesColor(Block... leaves){
        ColorProviderRegistry.BLOCK.register((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos) : FoliageColor.getDefaultColor(), leaves);
        ColorProviderRegistry.ITEM.register((itemStack, i) -> {
            Block block = Block.byItem(itemStack.getItem());
            return ColorProviderRegistry.BLOCK.get(block).getColor(block.defaultBlockState(), null, null, i);
        }, leaves);
    }
}
