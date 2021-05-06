/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class AirJump
extends Module {
    private static final Checkbox packet = new Checkbox("Packet", true);

    public AirJump() {
        super("AirJump", Module.Category.MOVEMENT, "Allows you to jump in the air");
    }

    @Override
    public void setup() {
        this.addSetting(packet);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        AirJump.mc.player.onGround = true;
        if (packet.getValue()) {
            AirJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AirJump.mc.player.posX, AirJump.mc.player.posY, AirJump.mc.player.posZ, true));
        }
    }
}

