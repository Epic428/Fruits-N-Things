package com.teamresourceful.fruits.common.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ModClient {

    public static void onInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAIN_BARREL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OIL_PRESS, RenderType.cutout());
    }
}