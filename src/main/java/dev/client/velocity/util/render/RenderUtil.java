/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  org.lwjgl.opengl.GL11
 */
package dev.client.velocity.util.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil
extends Tessellator {
    public static RenderUtil INSTANCE = new RenderUtil();
    public static ICamera camera = new Frustum();

    public RenderUtil() {
        super(0x200000);
    }

    public static void prepareRender(int mode) {
        RenderUtil.prepareGL();
    }

    public static void releaseRender() {
        RenderUtil.releaseGL();
    }

    private static void prepareGL() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
    }

    private static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void prepareProfiler() {
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
    }

    public static void releaseProfiler() {
        GL11.glDisable((int)34383);
        GL11.glDisable((int)2848);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GL11.glHint((int)3154, (int)4352);
    }

    public static void beginRender(int mode) {
        INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void onWorldRender() {
        INSTANCE.draw();
    }

    public static void drawBoxFromBlockPos(BlockPos blockPos, Color color, int sides) {
        RenderUtil.drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0, 1.0, 1.0, color, sides);
    }

    public static void drawPrismFromBlockPos(BlockPos blockPos, Color color, double height, int sides) {
        RenderUtil.drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0, height, 1.0, color, sides);
    }

    public static void drawBox(double x2, double y2, double z2, double w2, double h2, double d2, Color color, int sides) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GL11.glColor4d((double)((float)color.getRed() / 255.0f), (double)((float)color.getGreen() / 255.0f), (double)((float)color.getBlue() / 255.0f), (double)((float)color.getAlpha() / 255.0f));
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        if ((sides & 1) != 0) {
            RenderUtil.vertexStatic(x2 + w2, y2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2, z2, bufferbuilder);
        }
        if ((sides & 2) != 0) {
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2 + d2, bufferbuilder);
        }
        if ((sides & 4) != 0) {
            RenderUtil.vertexStatic(x2 + w2, y2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2, bufferbuilder);
        }
        if ((sides & 8) != 0) {
            RenderUtil.vertexStatic(x2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2 + d2, bufferbuilder);
        }
        if ((sides & 0x10) != 0) {
            RenderUtil.vertexStatic(x2, y2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2, y2 + h2, z2, bufferbuilder);
        }
        if ((sides & 0x20) != 0) {
            RenderUtil.vertexStatic(x2 + w2, y2, z2 + d2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2, bufferbuilder);
            RenderUtil.vertexStatic(x2 + w2, y2 + h2, z2 + d2, bufferbuilder);
        }
        tessellator.draw();
    }

    public static void drawNametagFromBlockPos(BlockPos pos, String text) {
        GlStateManager.pushMatrix();
        RenderUtil.glBillboardDistanceScaled((float)pos.getX() + 0.5f, (float)pos.getY() + 0.5f, (float)pos.getZ() + 0.5f, (EntityPlayer)Minecraft.getMinecraft().player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate((double)(-((double)Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2.0)), (double)0.0, (double)0.0);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, new Color(255, 255, 255).getRGB());
        GlStateManager.popMatrix();
    }

    public static void glBillboardDistanceScaled(float x2, float y2, float z2, EntityPlayer player, float scale) {
        RenderUtil.glBillboard(x2, y2, z2);
        int distance = (int)player.getDistance((double)x2, (double)y2, (double)z2);
        float scaleDistance = (float)distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale((float)scaleDistance, (float)scaleDistance, (float)scaleDistance);
    }

    public static void glBillboard(float x2, float y2, float z2) {
        float scale = 0.02666667f;
        GlStateManager.translate((double)((double)x2 - Minecraft.getMinecraft().getRenderManager().renderPosX), (double)((double)y2 - Minecraft.getMinecraft().getRenderManager().renderPosY), (double)((double)z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ));
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-Minecraft.getMinecraft().player.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)Minecraft.getMinecraft().player.rotationPitch, (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)-0.02666667f, (float)-0.02666667f, (float)0.02666667f);
    }

    private static void vertexStatic(double x2, double y2, double z2, BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x2 - Minecraft.getMinecraft().getRenderManager().viewerPosX, y2 - Minecraft.getMinecraft().getRenderManager().viewerPosY, z2 - Minecraft.getMinecraft().getRenderManager().viewerPosZ).endVertex();
    }

    private static void vertexColor(double x2, double y2, double z2, Color color, BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x2 - Minecraft.getMinecraft().getRenderManager().viewerPosX, y2 - Minecraft.getMinecraft().getRenderManager().viewerPosY, z2 - Minecraft.getMinecraft().getRenderManager().viewerPosZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
    }
}

