/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import dev.client.velocity.event.VelocityEvent;
import dev.client.velocity.event.events.packet.PacketSendEvent;
import dev.client.velocity.module.Module;

public class NoHunger extends Module
{
    public NoHunger() {
        super("NoHunger", Category.PLAYER, "Spoofs your onGround state to use less food.");
    }

    @SubscribeEvent
    public void onPacketSendEvent(final PacketSendEvent event) {
        if (event.getStage() == VelocityEvent.Stage.PRE) {
            if (event.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
                packet.onGround = (Minecraft.getMinecraft().player.fallDistance > 0.0f || Minecraft.getMinecraft().playerController.isHittingBlock);
            }
            if (event.getPacket() instanceof CPacketEntityAction) {
                final CPacketEntityAction packet2 = (CPacketEntityAction)event.getPacket();
                if (packet2.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet2.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                    event.setCanceled(true);
                }
            }
        }
    }
}


