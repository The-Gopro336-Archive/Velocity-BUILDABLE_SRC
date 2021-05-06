/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package dev.client.velocity.gui.main;

import dev.client.velocity.gui.main.HudWindow;
import dev.client.velocity.gui.util.GuiUtil;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.module.modules.client.HudEditor;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;

public class HudGui
extends GuiScreen {
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        HudWindow.hw.drawHud(mouseX, mouseY);
        GuiUtil.mouseListen(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            HudWindow.hw.lclickListen();
            GuiUtil.lclickListen();
        }
        if (mouseButton == 1) {
            GuiUtil.rclickListen();
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0) {
            HudWindow.hw.releaseListen();
            GuiUtil.releaseListen();
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        ModuleManager.getModuleByClass(HudEditor.class).disable();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

