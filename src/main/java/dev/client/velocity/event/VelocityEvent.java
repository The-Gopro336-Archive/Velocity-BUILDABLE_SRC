/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package dev.client.velocity.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class VelocityEvent
extends Event {
    private Stage s;

    public VelocityEvent() {
    }

    public VelocityEvent(Stage s2) {
        this.s = s2;
    }

    public Stage getStage() {
        return this.s;
    }

    public static enum Stage {
        PRE,
        POST;

    }
}

