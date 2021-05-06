/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.module.Module;

public class NoWeather
extends Module {
    public NoWeather() {
        super("NoWeather", Module.Category.RENDER, "Prevents rain from rendering client-side");
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        if (NoWeather.mc.world.isRaining()) {
            NoWeather.mc.world.setRainStrength(0.0f);
        }
    }
}

