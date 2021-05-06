/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.setting.checkbox;

import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import java.util.ArrayList;
import java.util.List;

public class Checkbox
extends Setting {
    private String name;
    private boolean checked;
    private boolean opened;
    private List<SubSetting> subs = new ArrayList<SubSetting>();

    public Checkbox(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
        this.opened = false;
    }

    public List<SubSetting> getSubSettings() {
        return this.subs;
    }

    public boolean hasSubSettings() {
        return this.subs.size() > 0;
    }

    public void addSub(SubSetting s2) {
        this.subs.add(s2);
    }

    public void toggleState() {
        this.opened = !this.opened;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public void toggleValue() {
        this.checked = !this.checked;
    }

    public String getName() {
        return this.name;
    }

    public boolean getValue() {
        return this.checked;
    }
}

