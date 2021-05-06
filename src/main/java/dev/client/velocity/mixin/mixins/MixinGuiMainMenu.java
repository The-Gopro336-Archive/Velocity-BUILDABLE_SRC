/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 */
package dev.client.velocity.mixin.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMainMenu.class})
public class MixinGuiMainMenu
extends GuiScreen {
    String s = "Velocity Client";
    String s2 = "Version 0.1";

    @Inject(method={"drawScreen"}, at={@At(value="TAIL")}, cancellable=true)
    public void drawText(int mouseX, int mouseY, float partialTicks, CallbackInfo ci2) {
        this.mc.fontRenderer.drawStringWithShadow(this.s, 2.0f, (float)(this.mc.displayHeight - 70), -1);
        this.mc.fontRenderer.drawStringWithShadow(this.s2, 2.0f, (float)(this.mc.displayHeight - 60), -1);
    }
}

