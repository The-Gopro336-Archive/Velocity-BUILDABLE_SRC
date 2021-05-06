/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.module.Module;

public class FullBright
extends Module {
    private float oldBright;

    public FullBright() {
        super("FullBright", Module.Category.RENDER, "Adjusts light levels");
    }

    @Override
    public void onEnable() {
        this.oldBright = FullBright.mc.gameSettings.gammaSetting;
        if (FullBright.mc.player != null) {
            FullBright.mc.gameSettings.gammaSetting = 100.0f;
        }
    }

    @Override
    public void onDisable() {
        if (FullBright.mc.player != null) {
            FullBright.mc.gameSettings.gammaSetting = this.oldBright;
        }
    }
}

