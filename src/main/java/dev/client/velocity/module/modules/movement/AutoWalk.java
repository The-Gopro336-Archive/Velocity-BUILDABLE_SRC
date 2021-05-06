/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super("AutoWalk", Module.Category.MOVEMENT, "Automatically walks");
    }

    @Override
    public void onUpdate() {
        KeyBinding.setKeyBindState((int)AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), (boolean)true);
    }
}

