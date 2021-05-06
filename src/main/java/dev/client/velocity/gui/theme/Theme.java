/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.gui.theme;

import dev.client.velocity.gui.theme.themes.DarkTheme;
import dev.client.velocity.gui.theme.themes.LightTheme;
import dev.client.velocity.module.Module;
import java.util.ArrayList;
import java.util.List;

public abstract class Theme {
    private String name;
    private int width;
    private int height;
    public static final List<Theme> themes = new ArrayList<Theme>();

    public Theme(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public abstract void updateColors();

    public abstract void drawTitles(String var1, int var2, int var3);

    public abstract void drawModules(List<Module> var1, int var2, int var3);

    public static void initThemes() {
        themes.add(new DarkTheme());
        themes.add(new LightTheme());
    }

    public void addTheme(Theme theme) {
        themes.add(theme);
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public String getThemeName() {
        return this.name;
    }

    public int getThemeWidth() {
        return this.width;
    }

    public int getThemeHeight() {
        return this.height;
    }

    public static Theme getTheme(int themeIndex) {
        return themes.get(themeIndex);
    }
}

