/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.player;

import dev.client.velocity.event.VelocityEvent;
import dev.client.velocity.event.events.packet.PacketReceiveEvent;
import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate
extends Module {
    private static final Checkbox strict = new Checkbox("NCPStrict", false);

    public NoRotate() {
        super("NoRotate", Module.Category.PLAYER, "Prevents the server from rotating you");
    }

    @Override
    public void setup() {
        this.addSetting(strict);
    }

    @SubscribeEvent
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && event.getStage() == VelocityEvent.Stage.PRE) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            packet.yaw = NoRotate.mc.player.rotationYaw;
            packet.pitch = NoRotate.mc.player.rotationPitch;
            float lastYaw = packet.yaw;
            float lastPitch = packet.yaw;
            if (strict.getValue()) {
                NoRotate.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(packet.yaw, packet.pitch, NoRotate.mc.player.onGround));
                for (int i2 = 0; i2 <= 3; ++i2) {
                    lastYaw = this.calculateAngle(lastYaw, NoRotate.mc.player.rotationYaw);
                    lastPitch = this.calculateAngle(lastPitch, NoRotate.mc.player.rotationPitch);
                    NoRotate.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.calculateAngle(lastYaw, NoRotate.mc.player.rotationYaw), this.calculateAngle(lastPitch, NoRotate.mc.player.rotationPitch), NoRotate.mc.player.onGround));
                }
                NoRotate.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(NoRotate.mc.player.rotationYaw, NoRotate.mc.player.rotationPitch, NoRotate.mc.player.onGround));
            }
        }
    }

    public float calculateAngle(float serverValue, float currentValue) {
        return (currentValue - serverValue) / 4.0f;
    }
}

