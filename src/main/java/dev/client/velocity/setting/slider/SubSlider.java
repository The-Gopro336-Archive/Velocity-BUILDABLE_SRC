/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.setting.slider;

import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;

public class SubSlider
extends SubSetting {
    private Setting parent;
    private String name;
    private double min;
    private double value;
    private double max;
    private int scale;

    public SubSlider(Setting parent, String name, double min, double value, double max, int scale) {
        this.parent = parent;
        this.name = name;
        this.min = min;
        this.value = scale == 0 ? (double)((int)value) : value;
        this.max = max;
        this.scale = scale;
        if (parent instanceof Checkbox) {
            Checkbox p2 = (Checkbox)parent;
            p2.addSub(this);
        } else if (parent instanceof Mode) {
            Mode p3 = (Mode)parent;
            p3.addSub(this);
        } else if (parent instanceof Slider) {
            Slider p4 = (Slider)parent;
            p4.addSub(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public Setting getParent() {
        return this.parent;
    }

    public int getRoundingScale() {
        return this.scale;
    }

    public double getValue() {
        return this.value;
    }

    public double getMaxValue() {
        return this.max;
    }

    public double getMinValue() {
        return this.min;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

