/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.module.Module;

public class NoBob
extends Module {
    public NoBob() {
        super("NoBob", Module.Category.RENDER, "Prevents the bobbing animation");
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        NoBob.mc.player.distanceWalkedModified = 4.0f;
    }
}

