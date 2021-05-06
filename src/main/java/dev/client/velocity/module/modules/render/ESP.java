/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.client.ColorUtil;
import dev.client.velocity.util.render.ESPUtil;
import dev.client.velocity.util.render.RenderUtil;
import dev.client.velocity.util.world.EntityUtil;
import java.awt.Color;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ESP
extends Module {
    private static Mode mode = new Mode("Mode", "Outline");
    private static SubCheckbox players = new SubCheckbox(mode, "Players", true);
    private static SubCheckbox animals = new SubCheckbox(mode, "Animals", true);
    private static SubCheckbox mobs = new SubCheckbox(mode, "Mobs", true);
    private static Slider lineWidth = new Slider("Line Width", 0.0, 2.0, 5.0, 0);

    public ESP() {
        super("ESP", Module.Category.RENDER, "Highlights entities");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
        this.addSetting(lineWidth);
    }

    public void doRenderOutlines(ModelBase mainModel, Entity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if (entitylivingbaseIn == ESP.mc.player.getRidingEntity()) {
            return;
        }
        RenderUtil.camera.setPosition(ESP.mc.getRenderViewEntity().posX, ESP.mc.getRenderViewEntity().posY, ESP.mc.getRenderViewEntity().posZ);
        if (!RenderUtil.camera.isBoundingBoxInFrustum(entitylivingbaseIn.getEntityBoundingBox())) {
            return;
        }
        if (entitylivingbaseIn instanceof EntityPlayer && !(entitylivingbaseIn instanceof EntityPlayerSP) && players.getValue()) {
            GlStateManager.pushMatrix();
            Color color = ColorUtil.getEntityColor(entitylivingbaseIn);
            ESPUtil.setColor(color);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderOne((float)lineWidth.getValue());
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderTwo();
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderThree();
            ESPUtil.renderFour();
            ESPUtil.setColor(color);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderFive();
            ESPUtil.setColor(Color.WHITE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
        } else if (EntityUtil.isPassive(entitylivingbaseIn) && animals.getValue() || EntityUtil.isHostileMob(entitylivingbaseIn) && mobs.getValue()) {
            GlStateManager.pushMatrix();
            Color color = ColorUtil.getEntityColor(entitylivingbaseIn);
            ESPUtil.setColor(color);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderOne((float)lineWidth.getValue());
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderTwo();
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderThree();
            ESPUtil.renderFour();
            ESPUtil.setColor(color);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ESPUtil.renderFive();
            ESPUtil.setColor(Color.WHITE);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
        }
    }
}

