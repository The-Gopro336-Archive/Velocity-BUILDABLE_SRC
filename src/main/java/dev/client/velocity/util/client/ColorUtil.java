/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package dev.client.velocity.util.client;

import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.modules.client.Colors;
import dev.client.velocity.util.world.EntityUtil;
import java.awt.Color;
import net.minecraft.entity.Entity;

public abstract class ColorUtil
implements MixinInterface {
    public static int rainbow(long offset) {
        float hue = (float)(((double)System.currentTimeMillis() * (Colors.speed.getValue() / 10.0) + (double)(offset * 500L)) % (30000.0 / (Colors.difference.getValue() / 100.0)) / (30000.0 / (Colors.difference.getValue() / 20.0)));
        int rgb = Color.HSBtoRGB(hue, (float)Colors.saturation.getValue(), (float)Colors.brightness.getValue());
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        int color = ColorUtil.toRGBA(red, green, blue, (int)Colors.a.getValue());
        return color;
    }

    public static int toRGBA(int r2, int g2, int b2, int a2) {
        return (r2 << 16) + (g2 << 8) + (b2 << 0) + (a2 << 24);
    }

    public static Color getEntityColor(Entity e2) {
        if (EntityUtil.isPassive(e2)) {
            return new Color(0, 200, 0);
        }
        if (EntityUtil.isHostileMob(e2)) {
            return new Color(153, 73, 205);
        }
        return new Color(213, 75, 75);
    }
}

