package com.teamresourceful.fruits.client;

import com.teamresourceful.fruits.client.renderers.RainBarrelRenderer;
import com.teamresourceful.fruits.common.lib.Constants;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import com.teamresourceful.fruits.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class ModClient {
    private ModClient() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static void onInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAIN_BARREL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OIL_PRESS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKBERRY_BUSH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUEBERRY_BUSH, RenderType.cutout());

        BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntities.RAIN_BARREL_ENTITY, RainBarrelRenderer::new);
    }
}
