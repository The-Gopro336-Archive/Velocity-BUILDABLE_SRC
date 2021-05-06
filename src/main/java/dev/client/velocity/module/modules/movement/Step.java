/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package dev.client.velocity.module.modules.movement;

import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class Step
extends Module {
    public static Slider height = new Slider("Height", 0.5, 2.5, 2.5, 0);
    private static final Checkbox timer = new Checkbox("UseTimer", true);
    private static final Checkbox reverse = new Checkbox("Reverse", true);
    private int ticks = 0;

    public Step() {
        super("Step", Module.Category.MOVEMENT, "Allows you to step up blocks");
    }

    @Override
    public void setup() {
        this.addSetting(height);
        this.addSetting(timer);
        this.addSetting(reverse);
    }

    @Override
    public void onUpdate() {
        if (Step.mc.world == null || Step.mc.player == null || Step.mc.player.isInWater() || Step.mc.player.isInLava() || Step.mc.player.isOnLadder() || Step.mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }
        if (timer.getValue()) {
            if (this.ticks == 0) {
                EntityUtil.resetTimer();
            } else {
                --this.ticks;
            }
        }
        if (Step.mc.player != null && Step.mc.player.onGround && !Step.mc.player.isInWater() && !Step.mc.player.isOnLadder() && reverse.getValue()) {
            for (double y2 = 0.0; y2 < height.getValue() + 0.5; y2 += 0.01) {
                if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(0.0, -y2, 0.0)).isEmpty()) continue;
                Step.mc.player.motionY = -10.0;
                break;
            }
        }
        double[] dir = Step.forward(0.1);
        boolean twofive = false;
        boolean two = false;
        boolean onefive = false;
        boolean one = false;
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.4, dir[1])).isEmpty()) {
            twofive = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.9, dir[1])).isEmpty()) {
            two = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.4, dir[1])).isEmpty()) {
            onefive = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 0.6, dir[1])).isEmpty()) {
            one = true;
        }
        if (Step.mc.player.collidedHorizontally && (Step.mc.player.moveForward != 0.0f || Step.mc.player.moveStrafing != 0.0f) && Step.mc.player.onGround) {
            int i2;
            if (one && height.getValue() >= 1.0) {
                double[] oneOffset = new double[]{0.42, 0.753};
                for (i2 = 0; i2 < oneOffset.length; ++i2) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneOffset[i2], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (timer.getValue()) {
                    EntityUtil.setTimer(0.6f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                this.ticks = 1;
            }
            if (onefive && height.getValue() >= 1.5) {
                double[] oneFiveOffset = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
                for (i2 = 0; i2 < oneFiveOffset.length; ++i2) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneFiveOffset[i2], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (timer.getValue()) {
                    EntityUtil.setTimer(0.35f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5, Step.mc.player.posZ);
                this.ticks = 1;
            }
            if (two && height.getValue() >= 2.0) {
                double[] twoOffset = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
                for (i2 = 0; i2 < twoOffset.length; ++i2) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoOffset[i2], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (timer.getValue()) {
                    EntityUtil.setTimer(0.25f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
                this.ticks = 2;
            }
            if (twofive && height.getValue() >= 2.5) {
                double[] twoFiveOffset = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                for (i2 = 0; i2 < twoFiveOffset.length; ++i2) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i2], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (timer.getValue()) {
                    EntityUtil.setTimer(0.15f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5, Step.mc.player.posZ);
                this.ticks = 2;
            }
        }
    }

    public static double[] forward(double speed) {
        float forward = Step.mc.player.movementInput.moveForward;
        float side = Step.mc.player.movementInput.moveStrafe;
        float yaw = Step.mc.player.prevRotationYaw + (Step.mc.player.rotationYaw - Step.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }
}

