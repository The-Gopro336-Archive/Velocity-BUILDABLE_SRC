/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package dev.client.velocity.event.events.packet;

import dev.client.velocity.event.VelocityEvent;
import net.minecraft.network.Packet;

public class PacketEvent
extends VelocityEvent {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet, VelocityEvent.Stage stage) {
        super(stage);
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}

