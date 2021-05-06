/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.model.ModelPlayer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.event.events.render.Render3DEvent;
import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.setting.slider.SubSlider;
import java.util.HashMap;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Skeleton
extends Module {
    public static Checkbox color = new Checkbox("Color", true);
    public static SubSlider red = new SubSlider(color, "Red", 0.0, 255.0, 255.0, 0);
    public static SubSlider green = new SubSlider(color, "Green", 0.0, 255.0, 255.0, 0);
    public static SubSlider blue = new SubSlider(color, "Blue", 0.0, 255.0, 255.0, 0);
    public static SubSlider alpha = new SubSlider(color, "Alpha", 0.0, 255.0, 255.0, 0);
    private static Slider lineWidth = new Slider("Line Width", 0.0, 1.0, 5.0, 1);
    private ICamera camera = new Frustum();
    private static final HashMap<EntityPlayer, float[][]> entities = new HashMap();

    public Skeleton() {
        super("Skeleton", Module.Category.RENDER, "Shows the skeleton of nearby players");
    }

    @Override
    public void setup() {
        this.addSetting(color);
        this.addSetting(lineWidth);
    }

    private Vec3d getVec3(Render3DEvent event, EntityPlayer e2) {
        float pt = event.getPartialTicks();
        double x2 = e2.lastTickPosX + (e2.posX - e2.lastTickPosX) * (double)pt;
        double y2 = e2.lastTickPosY + (e2.posY - e2.lastTickPosY) * (double)pt;
        double z2 = e2.lastTickPosZ + (e2.posZ - e2.lastTickPosZ) * (double)pt;
        return new Vec3d(x2, y2, z2);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (mc.getRenderManager() == null || Skeleton.mc.getRenderManager().options == null) {
            return;
        }
        this.startEnd(true);
        GL11.glEnable((int)2903);
        GL11.glDisable((int)2848);
        entities.keySet().removeIf(this::doesNotContain);
        Skeleton.mc.world.playerEntities.forEach(e2 -> this.drawSkeleton(event, (EntityPlayer)e2));
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
        this.startEnd(false);
    }

    private void drawSkeleton(Render3DEvent event, EntityPlayer e2) {
        double d3 = Skeleton.mc.player.lastTickPosX + (Skeleton.mc.player.posX - Skeleton.mc.player.lastTickPosX) * (double)event.getPartialTicks();
        double d4 = Skeleton.mc.player.lastTickPosY + (Skeleton.mc.player.posY - Skeleton.mc.player.lastTickPosY) * (double)event.getPartialTicks();
        double d5 = Skeleton.mc.player.lastTickPosZ + (Skeleton.mc.player.posZ - Skeleton.mc.player.lastTickPosZ) * (double)event.getPartialTicks();
        this.camera.setPosition(d3, d4, d5);
        float[][] entPos = entities.get((Object)e2);
        if (entPos != null && e2.isEntityAlive() && this.camera.isBoundingBoxInFrustum(e2.getEntityBoundingBox()) && !e2.isDead && e2 != Skeleton.mc.player && !e2.isPlayerSleeping()) {
            GL11.glPushMatrix();
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)((float)lineWidth.getValue()));
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            Vec3d vec = this.getVec3(event, e2);
            double x2 = vec.x - Skeleton.mc.getRenderManager().renderPosX;
            double y2 = vec.y - Skeleton.mc.getRenderManager().renderPosY;
            double z2 = vec.z - Skeleton.mc.getRenderManager().renderPosZ;
            GL11.glTranslated((double)x2, (double)y2, (double)z2);
            float xOff = e2.prevRenderYawOffset + (e2.renderYawOffset - e2.prevRenderYawOffset) * event.getPartialTicks();
            GL11.glRotatef((float)(-xOff), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslated((double)0.0, (double)0.0, (double)(e2.isSneaking() ? -0.235 : 0.0));
            float yOff = e2.isSneaking() ? 0.6f : 0.75f;
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)-0.125, (double)yOff, (double)0.0);
            if (entPos[3][0] != 0.0f) {
                GL11.glRotatef((float)(entPos[3][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (entPos[3][1] != 0.0f) {
                GL11.glRotatef((float)(entPos[3][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (entPos[3][2] != 0.0f) {
                GL11.glRotatef((float)(entPos[3][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)(-yOff), (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)0.125, (double)yOff, (double)0.0);
            if (entPos[4][0] != 0.0f) {
                GL11.glRotatef((float)(entPos[4][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (entPos[4][1] != 0.0f) {
                GL11.glRotatef((float)(entPos[4][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (entPos[4][2] != 0.0f) {
                GL11.glRotatef((float)(entPos[4][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)(-yOff), (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated((double)0.0, (double)0.0, (double)(e2.isSneaking() ? 0.25 : 0.0));
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)0.0, (double)(e2.isSneaking() ? -0.05 : 0.0), (double)(e2.isSneaking() ? -0.01725 : 0.0));
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)-0.375, (double)((double)yOff + 0.55), (double)0.0);
            if (entPos[1][0] != 0.0f) {
                GL11.glRotatef((float)(entPos[1][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (entPos[1][1] != 0.0f) {
                GL11.glRotatef((float)(entPos[1][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (entPos[1][2] != 0.0f) {
                GL11.glRotatef((float)(-entPos[1][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.375, (double)((double)yOff + 0.55), (double)0.0);
            if (entPos[2][0] != 0.0f) {
                GL11.glRotatef((float)(entPos[2][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (entPos[2][1] != 0.0f) {
                GL11.glRotatef((float)(entPos[2][1] * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (entPos[2][2] != 0.0f) {
                GL11.glRotatef((float)(-entPos[2][2] * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)-0.5, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef((float)(xOff - e2.rotationYawHead), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)0.0, (double)((double)yOff + 0.55), (double)0.0);
            if (entPos[0][0] != 0.0f) {
                GL11.glRotatef((float)(entPos[0][0] * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef((float)(e2.isSneaking() ? 25.0f : 0.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslated((double)0.0, (double)(e2.isSneaking() ? -0.16175 : 0.0), (double)(e2.isSneaking() ? -0.48025 : 0.0));
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0, (double)yOff, (double)0.0);
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)-0.125, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.125, (double)0.0, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)red.getValue() / 255.0f), (float)((float)green.getValue() / 255.0f), (float)((float)blue.getValue() / 255.0f), (float)((float)alpha.getValue() / 255.0f));
            GL11.glTranslated((double)0.0, (double)yOff, (double)0.0);
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.55, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)0.0, (double)((double)yOff + 0.55), (double)0.0);
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)-0.375, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.375, (double)0.0, (double)0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    private void startEnd(boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable((int)2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint((int)3154, (int)4354);
        } else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable((int)2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask((!revert ? 1 : 0) != 0);
    }

    public static void addEntity(EntityPlayer e2, ModelPlayer model) {
        entities.put(e2, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    private boolean doesNotContain(EntityPlayer var0) {
        return !Skeleton.mc.world.playerEntities.contains((Object)var0);
    }
}

