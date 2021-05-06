/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.hud;

import dev.client.velocity.Velocity;
import dev.client.velocity.gui.theme.Color;
import dev.client.velocity.module.Module;
import dev.client.velocity.module.modules.client.ClickGui;
import dev.client.velocity.module.modules.client.HudEditor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FPS
extends Module {
    public FPS() {
        super("FPS", Module.Category.HUD, "Your game's FPS");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (ClickGui.customFont.getValue()) {
            String string = Integer.toString(Minecraft.getDebugFPS()) + " FPS";
            Velocity.customFont.getClass();
            Velocity.customFont.drawStringWithShadow(string, 1.0f, HudEditor.boost * 9 + 1, Color.COLOR);
        } else {
            FPS.mc.fontRenderer.drawStringWithShadow(Integer.toString(Minecraft.getDebugFPS()) + " FPS", 1.0f, (float)(HudEditor.boost * FPS.mc.fontRenderer.FONT_HEIGHT + 1), Color.COLOR);
        }
        ++HudEditor.boost;
    }
}

