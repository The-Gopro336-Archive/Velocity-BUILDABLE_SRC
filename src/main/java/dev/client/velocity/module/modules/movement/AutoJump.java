/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoJump
extends Module {
    public AutoJump() {
        super("AutoJump", Module.Category.MOVEMENT, "Automatically jumps");
    }

    @Override
    public void onUpdate() {
        KeyBinding.setKeyBindState((int)AutoJump.mc.gameSettings.keyBindJump.getKeyCode(), (boolean)true);
    }
}

