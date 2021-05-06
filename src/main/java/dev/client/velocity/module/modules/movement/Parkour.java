/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import net.minecraft.entity.Entity;

public class Parkour
extends Module {
    public Parkour() {
        super("Parkour", Module.Category.MOVEMENT, "Automatically jumps at the edge of a block");
    }

    @Override
    public void onUpdate() {
        if (Parkour.mc.player.onGround && !Parkour.mc.player.isSneaking() && !Parkour.mc.gameSettings.keyBindSneak.isPressed() && !Parkour.mc.gameSettings.keyBindJump.isPressed() && Parkour.mc.world.getCollisionBoxes((Entity)Parkour.mc.player, Parkour.mc.player.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001)).isEmpty()) {
            Parkour.mc.player.jump();
        }
    }
}

