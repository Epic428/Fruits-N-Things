package com.teamresourceful.fruits.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.teamresourceful.fruits.common.blockentities.RainBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class RainBarrelRenderer implements BlockEntityRenderer<RainBarrelBlockEntity> {

    private static final TextureAtlasSprite WATER_SPRITE = Minecraft.getInstance().getModelManager().getBlockModelShaper().getParticleIcon(Blocks.WATER.defaultBlockState());

    public RainBarrelRenderer(BlockEntityRendererProvider.Context context) {
        // does nothing
    }

    @Override
    public void render(RainBarrelBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (blockEntity.getFluidAmount() != 0) {
            VertexConsumer builder = multiBufferSource.getBuffer(RenderType.translucentMovingBlock());
            createQuad(poseStack.last().pose(), builder, Mth.lerp(blockEntity.getPercentFilled(), 0.1875F, 0.9375F), light);
        }
    }

    private void createQuad(Matrix4f matrix, VertexConsumer builder, float verticalOffset, int light) {
        builder.vertex(matrix, 0.0625F, verticalOffset, 0.9375F).color(63, 118, 228, 255).uv(WATER_SPRITE.getU(0), WATER_SPRITE.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.9375F, verticalOffset, 0.9375F).color(63, 118, 228, 255).uv(WATER_SPRITE.getU(16), WATER_SPRITE.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.9375F, verticalOffset, 0.0625F).color(63, 118, 228, 255).uv(WATER_SPRITE.getU(16), WATER_SPRITE.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.0625F, verticalOffset, 0.0625F).color(63, 118, 228, 255).uv(WATER_SPRITE.getU(0), WATER_SPRITE.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
    }
}
