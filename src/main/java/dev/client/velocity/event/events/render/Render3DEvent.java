/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.event.events.render;

import dev.client.velocity.event.VelocityEvent;

public class Render3DEvent
extends VelocityEvent {
    private final float partialTicks;

    public Render3DEvent(float ticks) {
        this.partialTicks = ticks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

