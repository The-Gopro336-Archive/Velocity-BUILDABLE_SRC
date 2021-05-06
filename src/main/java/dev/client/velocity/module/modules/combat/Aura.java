/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemSword
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.setting.slider.SubSlider;
import dev.client.velocity.util.client.Timer;
import dev.client.velocity.util.combat.EnemyUtil;
import dev.client.velocity.util.world.EntityUtil;
import dev.client.velocity.util.world.PlayerUtil;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;

public class Aura
extends Module {
    private static final Mode mode = new Mode("Mode", "Closest", "Priority", "Armor");
    public static Checkbox attackCheck = new Checkbox("Attack Check", true);
    private static final SubCheckbox players = new SubCheckbox(attackCheck, "Players", true);
    private static final SubCheckbox animals = new SubCheckbox(attackCheck, "Animals", true);
    private static final SubCheckbox mobs = new SubCheckbox(attackCheck, "Mobs", true);
    public static Checkbox delay = new Checkbox("Delay", true);
    public static SubCheckbox useTicks = new SubCheckbox(delay, "Use Ticks", true);
    public static SubSlider tickDelay = new SubSlider(delay, "Tick Delay", 0.0, 10.0, 20.0, 1);
    public static SubCheckbox sync = new SubCheckbox(delay, "TPS Sync", false);
    public static Checkbox armorMelt = new Checkbox("Armor Melt", false);
    public static Mode weaponCheck = new Mode("Weapon", "Swing", "Damage");
    private static final SubCheckbox autoSwitch = new SubCheckbox(weaponCheck, "Auto Switch", true);
    private static final SubCheckbox swordOnly = new SubCheckbox(weaponCheck, "Sword Only", true);
    public static Checkbox pause = new Checkbox("Pause", true);
    public static SubCheckbox cannotSee = new SubCheckbox(pause, "Target Cannot be Seen", false);
    public static SubCheckbox crystalPause = new SubCheckbox(pause, "When Crystalling", false);
    public static SubCheckbox eatPause = new SubCheckbox(pause, "When Eating", false);
    public static Slider range = new Slider("Range", 0.0, 6.0, 10.0, 0);
    Timer syncTimer = new Timer();
    Entity currentTarget;

    public Aura() {
        super("Aura", Module.Category.COMBAT, "Attacks entities");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
        this.addSetting(attackCheck);
        this.addSetting(delay);
        this.addSetting(weaponCheck);
        this.addSetting(pause);
        this.addSetting(range);
    }

    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        if (autoSwitch.getValue()) {
            for (int i2 = 0; i2 < 9; ++i2) {
                int slot;
                if (!Aura.mc.player.inventory.getStackInSlot(i2).getItem().equals((Object)this.getItem())) continue;
                Aura.mc.player.inventory.currentItem = slot = i2;
                Aura.mc.playerController.updateController();
                break;
            }
        }
        this.killAura();
    }

    public void killAura() {
        Entity target = null;
        switch (mode.getValue()) {
            case 0: {
                target = Aura.mc.world.loadedEntityList.stream().filter(entity -> entity != Aura.mc.player).filter(entity -> (double)Aura.mc.player.getDistance(entity) <= range.getValue()).filter(entity -> !entity.isDead).filter(entity -> EnemyUtil.attackCheck(entity, players.getValue(), animals.getValue(), mobs.getValue())).sorted(Comparator.comparing(e2 -> Float.valueOf(Aura.mc.player.getDistance(e2)))).findFirst().orElse(null);
                break;
            }
            case 1: {
                target = Aura.mc.world.playerEntities.stream().filter(entity -> entity != Aura.mc.player).filter(entity -> (double)Aura.mc.player.getDistance((Entity)entity) <= range.getValue()).filter(entity -> !entity.isDead).min(Comparator.comparing(entityPlayer -> Float.valueOf(EnemyUtil.getHealth(entityPlayer)))).orElse(null);
                break;
            }
        }
        this.currentTarget = target;
        if (swordOnly.getValue() && !(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            return;
        }
        if (cannotSee.getValue() && !Aura.mc.player.canEntityBeSeen(target) && !EntityUtil.canEntityFeetBeSeen(target)) {
            return;
        }
        if (crystalPause.getValue() && (ModuleManager.getModuleByName("AutoCrystal").isEnabled() || Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal)) {
            return;
        }
        if (eatPause.getValue() && Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood) {
            return;
        }
        if (target != null) {
            this.attackEntity(target);
        }
    }

    public void attackEntity(Entity target) {
        if (useTicks.getValue() && !sync.getValue() && this.syncTimer.passed((long)(tickDelay.getValue() * 50.0))) {
            PlayerUtil.attackEntity(target);
        }
        if (armorMelt.getValue()) {
            Aura.mc.playerController.windowClick(Aura.mc.player.inventoryContainer.windowId, 9, Aura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Aura.mc.player);
            PlayerUtil.attackEntity(target);
            Aura.mc.playerController.windowClick(Aura.mc.player.inventoryContainer.windowId, 9, Aura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Aura.mc.player);
            PlayerUtil.attackEntity(target);
        } else {
            PlayerUtil.attackEntity(target);
        }
    }

    public Item getItem() {
        if (weaponCheck.getValue() == 0) {
            return Items.DIAMOND_SWORD;
        }
        return Items.DIAMOND_AXE;
    }
}

