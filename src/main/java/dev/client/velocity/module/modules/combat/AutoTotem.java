/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.world.PlayerUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoTotem
extends Module {
    public static Slider health = new Slider("Health", 0.0, 20.0, 36.0, 0);
    private static Checkbox soft = new Checkbox("Soft", true);
    int totems;
    boolean moving = false;
    boolean returnI = false;

    public AutoTotem() {
        super("AutoTotem", Module.Category.COMBAT, "Automatically replaces totems");
    }

    @Override
    public void setup() {
        this.addSetting(health);
        this.addSetting(soft);
    }

    @Override
    public void onUpdate() {
        int i2;
        int t2;
        if (this.Null()) {
            return;
        }
        Item item = this.getItem();
        if (AutoTotem.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            t2 = -1;
            for (i2 = 0; i2 < 45; ++i2) {
                if (!AutoTotem.mc.player.inventory.getStackInSlot(i2).isEmpty()) continue;
                t2 = i2;
                break;
            }
            if (t2 == -1) {
                return;
            }
            AutoTotem.mc.playerController.windowClick(0, t2 < 9 ? t2 + 36 : t2, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            this.returnI = false;
        }
        this.totems = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == item).mapToInt(ItemStack::getCount).sum();
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == item) {
            ++this.totems;
        } else {
            if (soft.getValue() && !AutoTotem.mc.player.getHeldItemOffhand().isEmpty()) {
                return;
            }
            if (this.moving) {
                AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = false;
                if (!AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                t2 = -1;
                for (i2 = 0; i2 < 45; ++i2) {
                    if (AutoTotem.mc.player.inventory.getStackInSlot(i2).getItem() != item) continue;
                    t2 = i2;
                    break;
                }
                if (t2 == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, t2 < 9 ? t2 + 36 : t2, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = true;
            } else if (!soft.getValue()) {
                t2 = -1;
                for (i2 = 0; i2 < 45; ++i2) {
                    if (!AutoTotem.mc.player.inventory.getStackInSlot(i2).isEmpty()) continue;
                    t2 = i2;
                    break;
                }
                if (t2 == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, t2 < 9 ? t2 + 36 : t2, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            }
        }
    }

    public Item getItem() {
        if (PlayerUtil.getHealth() <= health.getValue()) {
            return Items.TOTEM_OF_UNDYING;
        }
        return Items.AIR;
    }
}

