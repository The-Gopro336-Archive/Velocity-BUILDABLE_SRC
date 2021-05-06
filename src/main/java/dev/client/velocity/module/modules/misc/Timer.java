/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.misc;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.slider.Slider;

public class Timer
extends Module {
    public static Slider ticks = new Slider("Ticks", 0.0, 4.0, 20.0, 0);

    public Timer() {
        super("Timer", Module.Category.MISC, "Modifies client-side ticks");
    }

    @Override
    public void onDisable() {
        Timer.mc.timer.tickLength = 50.0f;
    }

    @Override
    public void onUpdate() {
        Timer.mc.timer.tickLength = (float)(50.0 / ticks.getValue());
    }
}

