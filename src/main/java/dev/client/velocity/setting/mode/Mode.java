/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.setting.mode;

import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import java.util.ArrayList;
import java.util.List;

public class Mode
extends Setting {
    private String name;
    private String[] modes;
    private int mode;
    private boolean opened;
    private List<SubSetting> subs = new ArrayList<SubSetting>();

    public Mode(String name, String ... modes) {
        this.name = name;
        this.modes = modes;
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

    public String getMode(int modeIndex) {
        return this.modes[modeIndex];
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void toggleState() {
        this.opened = !this.opened;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.mode;
    }

    public int nextMode() {
        return this.mode + 1 >= this.modes.length ? 0 : this.mode + 1;
    }
}

