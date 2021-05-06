/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.slider.Slider;
import net.minecraft.entity.Entity;

public class ReverseStep
extends Module {
    public static Slider height = new Slider("Height", 0.0, 2.0, 5.0, 0);

    public ReverseStep() {
        super("ReverseStep", Module.Category.MOVEMENT, "Allows you to fall faster");
    }

    @Override
    public void setup() {
        this.addSetting(height);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        if (ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (ReverseStep.mc.player != null && ReverseStep.mc.player.onGround && !ReverseStep.mc.player.isInWater() && !ReverseStep.mc.player.isOnLadder()) {
            for (double y2 = 0.0; y2 < height.getValue() + 0.5; y2 += 0.01) {
                if (ReverseStep.mc.world.getCollisionBoxes((Entity)ReverseStep.mc.player, ReverseStep.mc.player.getEntityBoundingBox().offset(0.0, -y2, 0.0)).isEmpty()) continue;
                ReverseStep.mc.player.motionY = -10.0;
                break;
            }
        }
    }
}

