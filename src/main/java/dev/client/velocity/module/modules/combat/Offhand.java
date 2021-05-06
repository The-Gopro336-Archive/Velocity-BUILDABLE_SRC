/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemSword
 *  org.lwjgl.input.Mouse
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.world.InventoryUtil;
import dev.client.velocity.util.world.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

public class Offhand
extends Module {
    public static final Mode mode = new Mode("Mode", "Crystal", "Gapple", "Bed", "Chorus", "Totem");
    public static final Mode fallbackMode = new Mode("Fallback", "Crystal", "Gapple", "Bed", "Chorus", "Totem");
    public static final Slider health = new Slider("Health", 0.1, 16.0, 36.0, 1);
    private static final Checkbox checks = new Checkbox("Checks", true);
    private static final SubCheckbox caFunction = new SubCheckbox(checks, "AutoCrystal", false);
    private static final SubCheckbox elytraCheck = new SubCheckbox(checks, "Elytra", false);
    private static final SubCheckbox fallCheck = new SubCheckbox(checks, "Falling", false);
    public static final Checkbox swordGap = new Checkbox("Sword Gapple", true);
    public static final Checkbox forceGap = new Checkbox("Force Gapple", true);

    public Offhand() {
        super("Offhand", Module.Category.COMBAT, "Switches items in the offhand");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
        this.addSetting(fallbackMode);
        this.addSetting(health);
        this.addSetting(checks);
        this.addSetting(swordGap);
        this.addSetting(forceGap);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        Item searching = Items.TOTEM_OF_UNDYING;
        if (Offhand.mc.player.isElytraFlying() & elytraCheck.getValue()) {
            return;
        }
        if (Offhand.mc.player.fallDistance > 10.0f && fallCheck.getValue()) {
            return;
        }
        if (!ModuleManager.getModuleByName("AutoCrystal").isEnabled() && caFunction.getValue()) {
            return;
        }
        switch (mode.getValue()) {
            case 0: {
                searching = Items.END_CRYSTAL;
                break;
            }
            case 1: {
                searching = Items.GOLDEN_APPLE;
                break;
            }
            case 2: {
                searching = Items.BED;
                break;
            }
            case 3: {
                searching = Items.CHORUS_FRUIT;
            }
        }
        if (health.getValue() > PlayerUtil.getHealth()) {
            searching = Items.TOTEM_OF_UNDYING;
        }
        if (Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && swordGap.getValue()) {
            searching = Items.GOLDEN_APPLE;
        }
        if (forceGap.getValue() && Mouse.isButtonDown((int)1)) {
            searching = Items.GOLDEN_APPLE;
        }
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == searching) {
            return;
        }
        int slot = InventoryUtil.getInventoryItemSlot(searching);
        if (slot != -1) {
            InventoryUtil.moveItemToOffhand(slot);
            return;
        }
        switch (fallbackMode.getValue()) {
            case 0: {
                searching = Items.END_CRYSTAL;
                break;
            }
            case 1: {
                searching = Items.GOLDEN_APPLE;
                break;
            }
            case 2: {
                searching = Items.BED;
                break;
            }
            case 3: {
                searching = Items.CHORUS_FRUIT;
                break;
            }
            case 4: {
                searching = Items.TOTEM_OF_UNDYING;
            }
        }
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == searching) {
            return;
        }
        slot = InventoryUtil.getInventoryItemSlot(searching);
        if (slot != -1) {
            InventoryUtil.moveItemToOffhand(slot);
        }
    }
}

