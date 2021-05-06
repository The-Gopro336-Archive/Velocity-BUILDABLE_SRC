/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.world.InventoryUtil;
import dev.client.velocity.util.world.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class QuickEXP
extends Module {
    private static final Mode mode = new Mode("Mode", "Packet", "AutoMend", "Throw");
    public static Slider delay = new Slider("Throw Delay", 0.0, 0.0, 4.0, 0);
    private static final Checkbox footEXP = new Checkbox("FootEXP", true);

    public QuickEXP() {
        super("QuickEXP", Module.Category.COMBAT, "Throws EXP much faster");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
        this.addSetting(delay);
        this.addSetting(footEXP);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        Item itemMainHand = QuickEXP.mc.player.getHeldItemMainhand().getItem();
        Item itemONotMainHand = QuickEXP.mc.player.getHeldItemOffhand().getItem();
        boolean expInMainHand = itemMainHand instanceof ItemExpBottle;
        boolean expNotInMainHand = itemONotMainHand instanceof ItemExpBottle;
        if (expInMainHand || expNotInMainHand) {
            QuickEXP.mc.rightClickDelayTimer = (int)delay.getValue();
        }
        if (QuickEXP.mc.player.isSneaking() && 0 < PlayerUtil.getArmorDurability() && (mode.getValue() == 0 || mode.getValue() == 1)) {
            switch (mode.getValue()) {
                case 1: {
                    QuickEXP.mc.player.inventory.currentItem = InventoryUtil.getHotbarItemSlot(Items.EXPERIENCE_BOTTLE);
                    break;
                }
                case 0: {
                    InventoryUtil.switchToSlotGhost(InventoryUtil.getHotbarItemSlot(Items.EXPERIENCE_BOTTLE));
                }
            }
            if (footEXP.getValue()) {
                QuickEXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
            }
            mc.rightClickMouse();
        }
    }
}

