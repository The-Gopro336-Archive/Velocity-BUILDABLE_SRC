/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package dev.client.velocity.util.combat;

import dev.client.velocity.util.world.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EnemyUtil {
    public static boolean getArmor(EntityPlayer target, boolean melt, double durability) {
        for (ItemStack stack : target.getArmorInventoryList()) {
            if (stack == null || stack.getItem() == Items.AIR) {
                return true;
            }
            float armorDurability = (float)(stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * 100.0f;
            if (!melt || !(durability >= (double)armorDurability)) continue;
            return true;
        }
        return false;
    }

    public static boolean enemyCheck(EntityPlayer enemy, int range) {
        if (enemy.getHealth() <= 0.0f) {
            return false;
        }
        return !(Minecraft.getMinecraft().player.getDistanceSq((Entity)enemy) > (double)(range * range));
    }

    public static boolean attackCheck(Entity entity, boolean players, boolean animals, boolean mobs) {
        if (players && entity instanceof EntityPlayer && ((EntityPlayer)entity).getHealth() > 0.0f) {
            return true;
        }
        if (animals && (EntityUtil.isPassive(entity) || EntityUtil.isNeutralMob(entity))) {
            return true;
        }
        return mobs && EntityUtil.isHostileMob(entity);
    }

    public static float getHealth(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount();
    }
}

