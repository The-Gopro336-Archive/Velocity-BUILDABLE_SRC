/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBow
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.math.BlockPos
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class QuickBow
extends Module {
    public QuickBow() {
        super("QuickBow", Module.Category.COMBAT, "Releases bow at a very high speed");
    }

    @Override
    public void onUpdate() {
        if (QuickBow.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && QuickBow.mc.player.isHandActive() && QuickBow.mc.player.getItemInUseMaxCount() >= 3) {
            QuickBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, QuickBow.mc.player.getHorizontalFacing()));
            QuickBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(QuickBow.mc.player.getActiveHand()));
            QuickBow.mc.player.stopActiveHand();
        }
    }
}

