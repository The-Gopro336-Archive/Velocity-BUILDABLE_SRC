/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.setting.mode;

import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;

public class SubMode
extends SubSetting {
    private Setting parent;
    private String name;
    private String[] modes;
    private int mode;

    public SubMode(Setting parent, String name, String ... modes) {
        this.parent = parent;
        this.name = name;
        this.modes = modes;
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

    public String getMode(int modeIndex) {
        return this.modes[modeIndex];
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getValue() {
        return this.mode;
    }

    public int nextMode() {
        return this.mode + 1 >= this.modes.length ? 0 : this.mode + 1;
    }
}

