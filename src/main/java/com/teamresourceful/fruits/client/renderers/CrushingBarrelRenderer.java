package com.teamresourceful.fruits.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.teamresourceful.fruits.common.blockentities.CrushingBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CrushingBarrelRenderer implements BlockEntityRenderer<CrushingBarrelBlockEntity> {

    public CrushingBarrelRenderer(BlockEntityRendererProvider.Context context) {
        // does nothing
    }

    @Override
    public void render(CrushingBarrelBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        float progress = blockEntity.progress();
        if (progress > 0) {
            BlockState state = blockEntity.getCurrentRecipe() == null ? blockEntity.getFluid().defaultFluidState().createLegacyBlock() : blockEntity.getCurrentRecipe().getOutput().defaultFluidState().createLegacyBlock();
            TextureAtlasSprite textureAtlasSprite = Minecraft.getInstance().getModelManager().getBlockModelShaper().getParticleIcon(state);
            VertexConsumer builder = multiBufferSource.getBuffer(RenderType.translucentMovingBlock());
            createQuad(poseStack.last().pose(), builder, Mth.lerp(progress, 0.25F, 0.640625F), light, textureAtlasSprite);
        }
        ItemStack stack = blockEntity.getItem(0).isEmpty() ? blockEntity.getItem(1) : blockEntity.getItem(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, Mth.lerp(progress, 0.4F, 0.640625F),0.5);
            poseStack.scale(0.5f,0.5f,0.5f);
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 0);

            poseStack.popPose();
        }
    }

    private void createQuad(Matrix4f matrix, VertexConsumer builder, float verticalOffset, int light, TextureAtlasSprite texture) {
        builder.vertex(matrix, 0.0625F, verticalOffset, 0.9375F).color(63, 118, 228, 255).uv(texture.getU(0), texture.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.9375F, verticalOffset, 0.9375F).color(63, 118, 228, 255).uv(texture.getU(16), texture.getV(0)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.9375F, verticalOffset, 0.0625F).color(63, 118, 228, 255).uv(texture.getU(16), texture.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
        builder.vertex(matrix, 0.0625F, verticalOffset, 0.0625F).color(63, 118, 228, 255).uv(texture.getU(0), texture.getV(16)).uv2(light).normal(0, 1F, 0).endVertex();
    }
}
