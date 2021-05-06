/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package dev.client.velocity.util.world;

import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.util.world.EntityUtil;
import dev.client.velocity.util.world.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockUtil
implements MixinInterface {
    public static boolean isCollidedBlocks(BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(new BlockPos(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY, BlockUtil.mc.player.posZ)).getBlock().equals((Object)Blocks.OBSIDIAN) || BlockUtil.isInterceptedByOther(pos) || InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN) == -1;
    }

    public static boolean isInterceptedByOther(BlockPos pos) {
        for (Entity entity : BlockUtil.mc.world.loadedEntityList) {
            if (entity.equals((Object)BlockUtil.mc.player) || !new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    public static void placeBlock(BlockPos pos, boolean rotate) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (BlockUtil.mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals((Object)Blocks.AIR) || EntityUtil.isIntercepted(pos)) continue;
            Vec3d vec = new Vec3d((double)pos.getX() + 0.5 + (double)enumFacing.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)enumFacing.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)enumFacing.getZOffset() * 0.5);
            float[] old = new float[]{BlockUtil.mc.player.rotationYaw, BlockUtil.mc.player.rotationPitch};
            if (rotate) {
                BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.z - BlockUtil.mc.player.posZ, vec.x - BlockUtil.mc.player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (BlockUtil.mc.player.posY + (double)BlockUtil.mc.player.getEyeHeight()), Math.sqrt((vec.x - BlockUtil.mc.player.posX) * (vec.x - BlockUtil.mc.player.posX) + (vec.z - BlockUtil.mc.player.posZ) * (vec.z - BlockUtil.mc.player.posZ))))), BlockUtil.mc.player.onGround));
            }
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtil.mc.playerController.processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            if (rotate) {
                BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(old[0], old[1], BlockUtil.mc.player.onGround));
            }
            return;
        }
    }
}

