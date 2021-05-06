/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package dev.client.velocity.util.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static double getHealth() {
        return PlayerUtil.mc.player.getHealth() + PlayerUtil.mc.player.getAbsorptionAmount();
    }

    public static int getArmorDurability() {
        int totalDurability = 0;
        for (ItemStack itemStack : PlayerUtil.mc.player.inventory.armorInventory) {
            totalDurability += itemStack.getItemDamage();
        }
        return totalDurability;
    }

    public static boolean IsInHole() {
        BlockPos blockPos = PlayerUtil.getLocalPlayerPosFloored();
        IBlockState blockState = PlayerUtil.mc.world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return false;
        }
        BlockPos[] touchingBlocks = new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()};
        int validHorizontalBlocks = 0;
        for (BlockPos touching : touchingBlocks) {
            IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || !touchingState.isFullBlock()) continue;
            ++validHorizontalBlocks;
        }
        return validHorizontalBlocks >= 4;
    }

    public static Vec3d getCenter(double posX, double posY, double posZ) {
        double x2 = Math.floor(posX) + 0.5;
        double y2 = Math.floor(posY);
        double z2 = Math.floor(posZ) + 0.5;
        return new Vec3d(x2, y2, z2);
    }

    public static BlockPos getLocalPlayerPosFloored() {
        if (PlayerUtil.mc.player == null) {
            return BlockPos.ORIGIN;
        }
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }

    public static void attackEntity(Entity entity) {
        if (PlayerUtil.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            PlayerUtil.mc.playerController.attackEntity((EntityPlayer)PlayerUtil.mc.player, entity);
            PlayerUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}

