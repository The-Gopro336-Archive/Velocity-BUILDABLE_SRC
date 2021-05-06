/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.gui.main;

import dev.client.velocity.gui.theme.Theme;
import dev.client.velocity.gui.util.GuiUtil;
import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.Module;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.module.modules.client.ClickGui;
import java.util.ArrayList;
import java.util.List;

public class HudWindow
implements MixinInterface {
    private int x;
    private int y;
    private String name;
    private boolean dragging;
    int currentTheme;
    private int lastmX;
    private int lastmY;
    private boolean ldown;
    private boolean rdown;
    private List<Module> modules = new ArrayList<Module>();
    public static HudWindow hw = new HudWindow(Module.Category.HUD.getName(), 200, 200);

    public HudWindow(String name, int x2, int y2) {
        this.name = name;
        this.x = x2;
        this.y = y2;
        this.modules = ModuleManager.getModulesInCategory(Module.Category.HUD);
    }

    public void drawHud(int mouseX, int mouseY) {
        this.mouseListen();
        this.currentTheme = ClickGui.theme.getValue();
        Theme current = Theme.getTheme(this.currentTheme);
        current.drawTitles(this.name, this.x, this.y);
        current.drawModules(this.modules, this.x, this.y);
        this.reset();
    }

    private void mouseListen() {
        if (this.dragging) {
            this.x = GuiUtil.mX - (this.lastmX - this.x);
            this.y = GuiUtil.mY - (this.lastmY - this.y);
        }
        this.lastmX = GuiUtil.mX;
        this.lastmY = GuiUtil.mY;
    }

    private void reset() {
        this.ldown = false;
        this.rdown = false;
    }

    public void lclickListen() {
        Theme current = Theme.getTheme(this.currentTheme);
        if (GuiUtil.mouseOver(this.x, this.y, this.x + current.getThemeWidth(), this.y + current.getThemeHeight())) {
            this.dragging = true;
        }
    }

    public void releaseListen() {
        this.ldown = false;
        this.dragging = false;
    }
}

