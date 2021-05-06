/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.mode.Mode;

public class Sprint
extends Module {
    private static Mode mode = new Mode("Mode", "Rage", "Legit");

    public Sprint() {
        super("Sprint", Module.Category.MOVEMENT, "Automatically sprints");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
    }

    @Override
    public void onUpdate() {
        if (mode.getValue() == 1) {
            try {
                if (Sprint.mc.gameSettings.keyBindForward.isKeyDown() && !Sprint.mc.player.collidedHorizontally && !Sprint.mc.player.isSneaking() && !Sprint.mc.player.isHandActive() && (float)Sprint.mc.player.getFoodStats().getFoodLevel() > 6.0f) {
                    Sprint.mc.player.setSprinting(true);
                }
            }
            catch (Exception exception) {}
        } else {
            try {
                if (!Sprint.mc.player.isSneaking() && !Sprint.mc.player.collidedHorizontally && (float)Sprint.mc.player.getFoodStats().getFoodLevel() > 6.0f && Sprint.mc.gameSettings.keyBindForward.isKeyDown() || Sprint.mc.gameSettings.keyBindLeft.isKeyDown() || Sprint.mc.gameSettings.keyBindRight.isKeyDown() || Sprint.mc.gameSettings.keyBindBack.isKeyDown()) {
                    Sprint.mc.player.setSprinting(true);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

