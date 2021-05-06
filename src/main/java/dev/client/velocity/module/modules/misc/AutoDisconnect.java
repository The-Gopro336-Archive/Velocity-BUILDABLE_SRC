/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package dev.client.velocity.module.modules.misc;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AutoDisconnect
extends Module {
    public static Slider health = new Slider("Health", 0.0, 7.0, 36.0, 0);
    public static Checkbox noTotems = new Checkbox("No Totems", false);
    public static Checkbox visualRange = new Checkbox("Player in Range", false);

    public AutoDisconnect() {
        super("AutoDisconnect", Module.Category.MISC, "Automatically logs you out when you're low on health");
    }

    @Override
    public void setup() {
        this.addSetting(health);
        this.addSetting(noTotems);
        this.addSetting(visualRange);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        int totems = AutoDisconnect.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if ((double)AutoDisconnect.mc.player.getHealth() <= health.getValue()) {
            this.disconnectFromWorld();
        }
        if (totems == 0 && noTotems.getValue()) {
            this.disconnectFromWorld();
        }
        if (AutoDisconnect.mc.world.playerEntities.size() > 1 && visualRange.getValue()) {
            this.disconnectFromWorld();
        }
    }

    public void disconnectFromWorld() {
        this.disable();
        AutoDisconnect.mc.world.sendQuittingDisconnectingPacket();
        mc.loadWorld(null);
        mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
    }
}

