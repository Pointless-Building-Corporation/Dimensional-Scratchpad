package com.pointlessbuilding.scratchpad.dimension;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScratchpadEffects extends DimensionSpecialEffects{

    public ScratchpadEffects() {
        super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, true, false);
    }

    private static void renderCubeSky(PoseStack poseStack, int ticks, float partialTick) {
        float s = 100.0f;

        Vector3f bottomColor = new Vector3f(162/255.0f, 191/255.0f, 254/255.0f);
        Vector3f topColor = new Vector3f(1.0f, 197/255.0f, 211/255.0f);

        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        addGradientFace(buffer, matrix, new Vector3f(-s,-s,-s), new Vector3f( s,-s,-s), new Vector3f( s, s,-s), new Vector3f(-s, s,-s), bottomColor, topColor);
        addGradientFace(buffer, matrix, new Vector3f( s,-s, s), new Vector3f(-s,-s, s), new Vector3f(-s, s, s), new Vector3f( s, s, s), bottomColor, topColor);
        addGradientFace(buffer, matrix, new Vector3f(-s,-s, s), new Vector3f(-s,-s,-s), new Vector3f(-s, s,-s), new Vector3f(-s, s, s), bottomColor, topColor);
        addGradientFace(buffer, matrix, new Vector3f( s,-s,-s), new Vector3f( s,-s, s), new Vector3f( s, s, s), new Vector3f( s, s,-s), bottomColor, topColor);
        addGradientFace(buffer, matrix, new Vector3f(-s, s,-s), new Vector3f( s, s,-s), new Vector3f( s, s, s), new Vector3f(-s, s, s), topColor, topColor);
        addGradientFace(buffer, matrix, new Vector3f(-s,-s,-s), new Vector3f(-s,-s, s), new Vector3f( s,-s, s), new Vector3f(s,-s,-s), bottomColor, bottomColor);

        BufferUploader.drawWithShader(buffer.end());

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    private static void addGradientFace(BufferBuilder buffer, Matrix4f matrix, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, Vector3f bottomColor, Vector3f topColor) {
        buffer.vertex(matrix, v1.x, v1.y, v1.z).color(bottomColor.x, bottomColor.y, bottomColor.z, 1.0f).endVertex();
        buffer.vertex(matrix, v2.x, v2.y, v2.z).color(bottomColor.x, bottomColor.y, bottomColor.z, 1.0f).endVertex();
        buffer.vertex(matrix, v3.x, v3.y, v3.z).color(topColor.x, topColor.y, topColor.z, 1.0f).endVertex();
        buffer.vertex(matrix, v4.x, v4.y, v4.z).color(topColor.x, topColor.y, topColor.z, 1.0f).endVertex();
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness) {
        return pFogColor;
    }

    @Override
    public boolean isFoggyAt(int pX, int pY) {
        return false;
    }

    @Override
    public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
        return null;
    }
    
    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        renderCubeSky(poseStack, ticks, partialTick);
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        return true;
    }

}
