/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package dev.client.velocity.module.modules.combat;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.util.client.MessageUtil;
import dev.client.velocity.util.world.BlockUtil;
import dev.client.velocity.util.world.InventoryUtil;
import dev.client.velocity.util.world.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Burrow
extends Module {
    private static final Mode mode = new Mode("Mode", "Rubberband", "Teleport", "Clip");
    private static final Checkbox rotate = new Checkbox("Rotate", true);
    private static final Checkbox centerPlayer = new Checkbox("Center", false);
    private static final Checkbox onGround = new Checkbox("On Ground", false);
    BlockPos originalPos;
    Vec3d center = Vec3d.ZERO;

    public Burrow() {
        super("Burrow", Module.Category.COMBAT, "Rubberbands you into a block");
    }

    @Override
    public void setup() {
        this.addSetting(mode);
        this.addSetting(rotate);
        this.addSetting(centerPlayer);
        this.addSetting(onGround);
    }

    @Override
    public void onEnable() {
        if (this.Null()) {
            return;
        }
        this.originalPos = new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ);
        this.center = PlayerUtil.getCenter(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ);
        if (BlockUtil.isCollidedBlocks(this.originalPos)) {
            this.disable();
        }
        if (centerPlayer.getValue()) {
            Burrow.mc.player.motionX = 0.0;
            Burrow.mc.player.motionZ = 0.0;
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.center.x, this.center.y, this.center.z, true));
            Burrow.mc.player.setPosition(this.center.x, this.center.y, this.center.z);
        }
        MessageUtil.sendClientMessage("Attempting to trigger a rubberband!");
        Burrow.mc.player.jump();
    }

    @Override
    public void onUpdate() {
        if (Burrow.mc.player.posY > (double)this.originalPos.getY() + 1.2) {
            Burrow.mc.player.inventory.currentItem = InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN);
            BlockUtil.placeBlock(this.originalPos, rotate.getValue());
            if (onGround.getValue()) {
                Burrow.mc.player.onGround = true;
            }
            switch (mode.getValue()) {
                case 0: {
                    Burrow.mc.player.jump();
                    break;
                }
                case 1: {
                    Burrow.mc.player.setPosition(Burrow.mc.player.posX, Burrow.mc.player.posY - 1.0, Burrow.mc.player.posZ);
                    break;
                }
                case 2: {
                    Burrow.mc.player.setPosition(Burrow.mc.player.posX, Burrow.mc.player.posY + 10.0, Burrow.mc.player.posZ);
                }
            }
            this.disable();
        }
    }
}

