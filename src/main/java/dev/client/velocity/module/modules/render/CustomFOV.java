/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FOVModifier
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.render;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.slider.Slider;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomFOV
extends Module {
    public static Slider customFOV = new Slider("FOV", 0.0, 120.0, 250.0, 0);

    public CustomFOV() {
        super("CustomFOV", Module.Category.RENDER, "Changes your ingame FOV");
    }

    @Override
    public void setup() {
        this.addSetting(customFOV);
    }

    @SubscribeEvent
    public void eventFOV(EntityViewRenderEvent.FOVModifier FOV) {
        FOV.setFOV((float)customFOV.getValue());
    }
}

