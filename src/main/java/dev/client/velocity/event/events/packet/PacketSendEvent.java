/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 */
package dev.client.velocity.event.events.packet;

import dev.client.velocity.event.VelocityEvent;
import dev.client.velocity.event.events.packet.PacketEvent;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketSendEvent
extends PacketEvent {
    public PacketSendEvent(Packet<?> packet, VelocityEvent.Stage stage) {
        super(packet, stage);
    }
}

