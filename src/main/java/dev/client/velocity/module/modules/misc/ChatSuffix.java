/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.misc;

import dev.client.velocity.commands.Suffix;
import dev.client.velocity.event.events.packet.PacketSendEvent;
import dev.client.velocity.module.Module;
import dev.client.velocity.util.client.MessageUtil;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix
extends Module {
    String suffix = "velocity";

    public ChatSuffix() {
        super("ChatSuffix", Module.Category.MISC, "Appends a chat suffix to messages");
    }

    @Override
    public void onUpdate() {
        this.suffix = Suffix.customSuffix;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String newMessage;
            CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
            if (packet.getMessage().startsWith("/")) {
                return;
            }
            if (packet.getMessage().startsWith("!")) {
                return;
            }
            if (packet.getMessage().startsWith("$")) {
                return;
            }
            if (packet.getMessage().startsWith("?")) {
                return;
            }
            if (packet.getMessage().startsWith(".")) {
                return;
            }
            if (packet.getMessage().startsWith(",")) {
                return;
            }
            packet.message = newMessage = packet.getMessage() + " \u23d0 " + MessageUtil.toUnicode(this.suffix);
        }
    }
}

