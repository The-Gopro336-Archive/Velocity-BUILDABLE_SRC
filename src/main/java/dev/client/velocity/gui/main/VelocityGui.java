/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package dev.client.velocity.gui.main;

import dev.client.velocity.gui.main.VelocityWindow;
import dev.client.velocity.gui.util.GuiUtil;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.module.modules.client.ClickGui;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;

public class VelocityGui
extends GuiScreen {
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (VelocityWindow w2 : VelocityWindow.windows) {
            w2.drawGui(mouseX, mouseY);
        }
        GuiUtil.mouseListen(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            for (VelocityWindow w2 : VelocityWindow.windows) {
                w2.lclickListen();
            }
            GuiUtil.lclickListen();
        }
        if (mouseButton == 1) {
            GuiUtil.rclickListen();
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0) {
            for (VelocityWindow w2 : VelocityWindow.windows) {
                w2.releaseListen();
            }
            GuiUtil.releaseListen();
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        GuiUtil.keyListen(keyCode);
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        ModuleManager.getModuleByClass(ClickGui.class).disable();
        this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

