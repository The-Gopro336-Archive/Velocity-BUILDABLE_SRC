/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.setting.checkbox;

import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;

public class SubCheckbox
extends SubSetting {
    private Setting parent;
    private String name;
    private boolean checked;

    public SubCheckbox(Setting parent, String name, boolean checked) {
        this.parent = parent;
        this.name = name;
        this.checked = checked;
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

    public boolean getValue() {
        return this.checked;
    }

    public void toggleValue() {
        this.checked = !this.checked;
    }

    public Setting getParent() {
        return this.parent;
    }
}

