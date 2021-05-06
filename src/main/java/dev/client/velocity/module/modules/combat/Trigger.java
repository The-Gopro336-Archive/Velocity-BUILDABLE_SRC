/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.RayTraceResult$Type
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

public class Trigger
extends Module {
    public static Slider attackSpeed = new Slider("Attack Speed", 0.0, 5.0, 10.0, 0);
    private static Checkbox players = new Checkbox("Players", true);
    private static Checkbox animals = new Checkbox("Animals", false);
    private static Checkbox mobs = new Checkbox("Mobs", false);

    public Trigger() {
        super("Trigger", Module.Category.COMBAT, "Attacks entities in your crosshair");
    }

    @Override
    public void setup() {
        this.addSetting(attackSpeed);
        this.addSetting(players);
        this.addSetting(animals);
        this.addSetting(mobs);
    }

    @Override
    public void onUpdate() {
        if (Trigger.mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && Trigger.mc.objectMouseOver.entityHit instanceof EntityPlayer && players.getValue() || Trigger.mc.objectMouseOver.entityHit instanceof EntityAnimal && animals.getValue() || Trigger.mc.objectMouseOver.entityHit instanceof EntityMob && mobs.getValue()) {
            Trigger.mc.playerController.attackEntity((EntityPlayer)Trigger.mc.player, Trigger.mc.objectMouseOver.entityHit);
        }
    }
}

