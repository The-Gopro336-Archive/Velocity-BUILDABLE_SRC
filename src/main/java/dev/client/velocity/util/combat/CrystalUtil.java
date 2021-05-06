/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package dev.client.velocity.util.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class CrystalUtil {
    public static Minecraft mc = Minecraft.getMinecraft();
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0f;
        double distancedsize = entity.getDistance(posX, posY, posZ) / 12.0;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v2 = (1.0 - distancedsize) * blockDensity;
        float damage = (int)((v2 * v2 + v2) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = CrystalUtil.getBlastReduction((EntityLivingBase)entity, CrystalUtil.getDamageMultiplied(damage), new Explosion((World)CrystalUtil.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep2 = (EntityPlayer)entity;
            DamageSource ds2 = DamageSource.causeExplosionDamage((Explosion)explosion);
            damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)ep2.getTotalArmorValue(), (float)((float)ep2.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
            int k2 = EnchantmentHelper.getEnchantmentModifierDamage((Iterable)ep2.getArmorInventoryList(), (DamageSource)ds2);
            float f2 = MathHelper.clamp((float)k2, (float)0.0f, (float)20.0f);
            damage *= 1.0f - f2 / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById((int)11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entity.getTotalArmorValue(), (float)((float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = CrystalUtil.mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0.0f : (diff == 2 ? 1.0f : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return CrystalUtil.calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    public static boolean canBlockBeSeen(BlockPos blockPos) {
        return CrystalUtil.mc.world.rayTraceBlocks(new Vec3d(CrystalUtil.mc.player.posX, CrystalUtil.mc.player.posY + (double)CrystalUtil.mc.player.getEyeHeight(), CrystalUtil.mc.player.posZ), new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), false, true, false) == null;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(CrystalUtil.mc.player.posX), Math.floor(CrystalUtil.mc.player.posY), Math.floor(CrystalUtil.mc.player.posZ));
    }

    public static void lookAtPacket(double px, double py, double pz, EntityPlayer self) {
        double[] v2 = CrystalUtil.calculateLookAt(px, py, pz, self);
        CrystalUtil.setYawAndPitch((float)v2[0], (float)v2[1]);
    }

    public static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer self) {
        double dirx = self.posX - px;
        double diry = self.posY - py;
        double dirz = self.posZ - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        double pitch = Math.asin(diry /= len);
        double yaw = Math.atan2(dirz /= len, dirx /= len);
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        return new double[]{yaw += 90.0, pitch};
    }

    public static void resetRotation() {
        yaw = CrystalUtil.mc.player.rotationYaw;
        pitch = CrystalUtil.mc.player.rotationPitch;
    }

    public static void attackCrystal(EntityEnderCrystal crystal, boolean packet) {
        if (packet) {
            CrystalUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)crystal));
        } else {
            CrystalUtil.mc.playerController.attackEntity((EntityPlayer)CrystalUtil.mc.player, (Entity)crystal);
        }
    }

    public static void placeCrystal(BlockPos pos, double delay, boolean offhand) {
        CrystalUtil.lookAtPacket((double)pos.getX() + 0.5, (double)pos.getY() - 0.5, (double)pos.getZ() + 0.5, (EntityPlayer)CrystalUtil.mc.player);
        RayTraceResult result = CrystalUtil.mc.world.rayTraceBlocks(new Vec3d(CrystalUtil.mc.player.posX, CrystalUtil.mc.player.posY + (double)CrystalUtil.mc.player.getEyeHeight(), CrystalUtil.mc.player.posZ), new Vec3d((double)pos.getX() + 0.5, (double)pos.getY() - 0.5, (double)pos.getZ() + 0.5));
        EnumFacing f2 = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
        if ((double)(System.nanoTime() / 1000000L) >= delay) {
            CrystalUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, f2, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
    }

    public static EnumFacing getEnumFacing(boolean rayTrace, BlockPos render, BlockPos finalPos) {
        RayTraceResult result = CrystalUtil.mc.world.rayTraceBlocks(new Vec3d(CrystalUtil.mc.player.posX, CrystalUtil.mc.player.posY + (double)CrystalUtil.mc.player.getEyeHeight(), CrystalUtil.mc.player.posZ), new Vec3d((double)finalPos.getX() + 0.5, (double)finalPos.getY() - 0.5, (double)finalPos.getZ() + 0.5));
        EnumFacing enumFacing = null;
        if (rayTrace) {
            if (result == null || result.sideHit == null) {
                render = null;
                CrystalUtil.resetRotation();
                return null;
            }
            enumFacing = result.sideHit;
        }
        return enumFacing;
    }
}

