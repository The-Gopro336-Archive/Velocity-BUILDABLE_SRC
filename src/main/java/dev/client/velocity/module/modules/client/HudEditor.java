/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.client;

import dev.client.velocity.gui.main.HudGui;
import dev.client.velocity.module.Module;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudEditor
extends Module {
    public static HudGui hudEditor = new HudGui();
    public static int boost = 0;

    public HudEditor() {
        super("HudEditor", Module.Category.CLIENT, "The in-game hudeditor.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen((GuiScreen)hudEditor);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
    }
}

