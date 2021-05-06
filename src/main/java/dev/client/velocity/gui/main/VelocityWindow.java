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

public class VelocityWindow
implements MixinInterface {
    public int x;
    public int y;
    private int mX;
    private int mY;
    private boolean ldown;
    private boolean rdown;
    private boolean dragging;
    int currentTheme;
    private int lastmX;
    private int lastmY;
    private String name;
    private Module.Category category;
    private List<Module> modules;
    public static final List<VelocityWindow> windows = new ArrayList<VelocityWindow>();

    public VelocityWindow(String name, int x2, int y2, Module.Category category) {
        this.name = name;
        this.x = x2;
        this.y = y2;
        this.category = category;
        this.modules = ModuleManager.getModulesInCategory(category);
    }

    public static void initGui() {
        windows.add(new VelocityWindow(Module.Category.CLIENT.getName(), 12, 22, Module.Category.CLIENT));
        windows.add(new VelocityWindow(Module.Category.COMBAT.getName(), 122, 22, Module.Category.COMBAT));
        windows.add(new VelocityWindow(Module.Category.PLAYER.getName(), 232, 22, Module.Category.PLAYER));
        windows.add(new VelocityWindow(Module.Category.MISC.getName(), 342, 22, Module.Category.MISC));
        windows.add(new VelocityWindow(Module.Category.MOVEMENT.getName(), 452, 22, Module.Category.MOVEMENT));
        windows.add(new VelocityWindow(Module.Category.RENDER.getName(), 562, 22, Module.Category.RENDER));
    }

    public void drawGui(int mouseX, int mouseY) {
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

