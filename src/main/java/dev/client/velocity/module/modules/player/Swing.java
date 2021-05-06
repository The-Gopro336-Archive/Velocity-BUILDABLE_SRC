/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHand
 */
package dev.client.velocity.module.modules.player;

import dev.client.velocity.module.Module;
import net.minecraft.util.EnumHand;

public class Swing
extends Module {
    public Swing() {
        super("Swing", Module.Category.PLAYER, "Swings with your offhand");
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        Swing.mc.player.swingingHand = EnumHand.OFF_HAND;
    }
}

