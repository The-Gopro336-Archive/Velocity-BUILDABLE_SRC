/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.hud;

import dev.client.velocity.Velocity;
import dev.client.velocity.gui.theme.Color;
import dev.client.velocity.module.Module;
import dev.client.velocity.module.modules.client.ClickGui;
import dev.client.velocity.module.modules.client.HudEditor;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerIP
extends Module {
    public ServerIP() {
        super("ServerIP", Module.Category.HUD, "Current server address");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (ClickGui.customFont.getValue()) {
            if (!mc.isSingleplayer()) {
                Velocity.customFont.getClass();
                Velocity.customFont.drawStringWithShadow(ServerIP.mc.serverName, 1.0f, HudEditor.boost * 9 + 1, Color.COLOR);
            } else {
                Velocity.customFont.getClass();
                Velocity.customFont.drawStringWithShadow("Singleplayer", 1.0f, HudEditor.boost * 9 + 1, Color.COLOR);
            }
        } else if (!mc.isSingleplayer()) {
            ServerIP.mc.fontRenderer.drawStringWithShadow(ServerIP.mc.serverName, 1.0f, (float)(HudEditor.boost * ServerIP.mc.fontRenderer.FONT_HEIGHT + 1), Color.COLOR);
        } else {
            ServerIP.mc.fontRenderer.drawStringWithShadow("Singleplayer", 1.0f, (float)(HudEditor.boost * ServerIP.mc.fontRenderer.FONT_HEIGHT + 1), Color.COLOR);
        }
        ++HudEditor.boost;
    }
}

