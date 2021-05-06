/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.mode.Mode;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Quiver
extends Module {
    private static Mode mode = new Mode("Mode", "Automatic", "Manual");
    public static SubCheckbox speed = new SubCheckbox(mode, "Speed", true);
    public static SubCheckbox strength = new SubCheckbox(mode, "Strength", true);
    public static Checkbox toggle = new Checkbox("Toggles", false);
    private int randomVariation;

    public Quiver() {
        super("Quiver", Module.Category.COMBAT, "Shoots arrows at you");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
    }

    @Override
    public void onUpdate() {
        PotionEffect speedEffect = Quiver.mc.player.getActivePotionEffect(Potion.getPotionById((int)1));
        PotionEffect strengthEffect = Quiver.mc.player.getActivePotionEffect(Potion.getPotionById((int)5));
        boolean hasSpeed = false;
        boolean hasStrength = false;
        hasSpeed = speedEffect != null;
        hasStrength = strengthEffect != null;
        Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, -90.0f, true));
        if (mode.getValue() == 0) {
            if (strength.getValue() && !hasStrength && Quiver.mc.player.inventory.getCurrentItem().getItem() == Items.BOW && this.isArrowInInventory("Arrow of Strength")) {
                if (Quiver.mc.player.getItemInUseMaxCount() >= this.getBowCharge()) {
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Quiver.mc.player.getHorizontalFacing()));
                    Quiver.mc.player.stopActiveHand();
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    Quiver.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                } else if (Quiver.mc.player.getItemInUseMaxCount() == 0) {
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    Quiver.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                }
            }
            if (speed.getValue() && !hasSpeed && Quiver.mc.player.inventory.getCurrentItem().getItem() == Items.BOW && this.isArrowInInventory("Arrow of Speed")) {
                if (Quiver.mc.player.getItemInUseMaxCount() >= this.getBowCharge()) {
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Quiver.mc.player.getHorizontalFacing()));
                    Quiver.mc.player.stopActiveHand();
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    Quiver.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                } else if (Quiver.mc.player.getItemInUseMaxCount() == 0) {
                    Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    Quiver.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                }
            }
        }
        if (toggle.getValue()) {
            this.disable();
        }
    }

    private boolean isArrowInInventory(String type) {
        boolean inInv = false;
        for (int i2 = 0; i2 < 36; ++i2) {
            ItemStack itemStack = Quiver.mc.player.inventory.getStackInSlot(i2);
            if (itemStack.getItem() != Items.TIPPED_ARROW || !itemStack.getDisplayName().equalsIgnoreCase(type)) continue;
            inInv = true;
            this.switchArrow(i2);
            break;
        }
        return inInv;
    }

    private void switchArrow(int oldSlot) {
        int bowSlot = Quiver.mc.player.inventory.currentItem;
        int placeSlot = bowSlot + 1;
        if (placeSlot > 8) {
            placeSlot = 1;
        }
        if (placeSlot != oldSlot) {
            if (Quiver.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            Quiver.mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
            Quiver.mc.playerController.windowClick(0, placeSlot, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
            Quiver.mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
        }
    }

    private int getBowCharge() {
        if (this.randomVariation == 0) {
            this.randomVariation = 1;
        }
        return 3 + this.randomVariation;
    }
}

