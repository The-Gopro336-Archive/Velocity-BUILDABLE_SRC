/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ResourceLocation
 */
package dev.client.velocity.module.modules.client;

import dev.client.velocity.gui.main.VelocityGui;
import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.SubSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class ClickGui
extends Module {
    public static Checkbox customFont = new Checkbox("Custom Font", true);
    public static Checkbox snapSlider = new Checkbox("Slider Snap", true);
    public static SubSlider snapSub = new SubSlider(snapSlider, "Snap Distance", 1.0, 5.0, 10.0, 0);
    public static Mode theme = new Mode("Theme", "Light", "Dark");
    public static VelocityGui clickGui = new VelocityGui();

    public ClickGui() {
        super("ClickGUI", Module.Category.CLIENT, "Opens the ClickGUI");
        this.key = 37;
    }

    @Override
    public void setup() {
        this.addSetting(customFont);
        this.addSetting(snapSlider);
        this.addSetting(theme);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen((GuiScreen)clickGui);
        ClickGui.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
    }

    @Override
    public void onUpdate() {
    }
}

