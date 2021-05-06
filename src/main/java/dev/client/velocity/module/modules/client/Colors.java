/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.client;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.setting.slider.SubSlider;

public class Colors
extends Module {
    public static Checkbox rainbow = new Checkbox("Rainbow", true);
    public static SubCheckbox gradient = new SubCheckbox(rainbow, "Gradient", true);
    public static SubSlider saturation = new SubSlider(rainbow, "Saturation", 0.0, 0.8, 1.0, 2);
    public static SubSlider brightness = new SubSlider(rainbow, "Brightness", 0.0, 0.8, 1.0, 2);
    public static SubSlider difference = new SubSlider(rainbow, "Difference", 1.0, 30.0, 100.0, 0);
    public static SubSlider speed = new SubSlider(rainbow, "Speed", 1.0, 30.0, 100.0, 0);
    public static Slider r = new Slider("R", 0.0, 220.0, 255.0, 0);
    public static Slider g = new Slider("G", 0.0, 20.0, 255.0, 0);
    public static Slider b = new Slider("B", 0.0, 220.0, 255.0, 0);
    public static Slider a = new Slider("A", 0.0, 220.0, 255.0, 0);

    public Colors() {
        super("Colors", Module.Category.CLIENT, "The client-wide color scheme.");
    }

    @Override
    public void setup() {
        this.addSetting(rainbow);
        this.addSetting(r);
        this.addSetting(g);
        this.addSetting(b);
        this.addSetting(a);
    }
}

