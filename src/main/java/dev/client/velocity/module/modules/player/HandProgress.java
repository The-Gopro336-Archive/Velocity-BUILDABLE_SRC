/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.player;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.slider.Slider;

public class HandProgress
extends Module {
    public static Slider offhandHeight = new Slider("OffHand", 0.0, 0.5, 1.0, 2);
    public static Slider mainhandHeight = new Slider("MainHand", 0.0, 0.5, 1.0, 2);

    public HandProgress() {
        super("HandProgress", Module.Category.PLAYER, "Allows you to change your hand height");
    }

    @Override
    public void setup() {
        this.addSetting(offhandHeight);
        this.addSetting(mainhandHeight);
    }

    @Override
    public void onUpdate() {
        HandProgress.mc.entityRenderer.itemRenderer.equippedProgressMainHand = (float)mainhandHeight.getValue();
        HandProgress.mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float)offhandHeight.getValue();
    }
}

