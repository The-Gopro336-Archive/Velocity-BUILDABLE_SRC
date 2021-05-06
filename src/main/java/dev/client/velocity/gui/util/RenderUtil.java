/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package dev.client.velocity.gui.util;

import dev.client.velocity.util.client.ColorUtil;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void drawRainbowBox(long boost, long boost2, int x2, int y2, int width, int height) {
        Color color = new Color(ColorUtil.rainbow(boost));
        Color color2 = new Color(ColorUtil.rainbow(boost2));
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glColor4f((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex2f((float)x2, (float)(y2 + height));
        GL11.glColor4f((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
        GL11.glVertex2f((float)(x2 + width), (float)(y2 + height));
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glVertex2f((float)(x2 + width), (float)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }
}

