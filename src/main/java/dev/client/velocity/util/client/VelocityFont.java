/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.StringUtils
 */
package dev.client.velocity.util.client;

import dev.client.velocity.mixin.MixinInterface;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class VelocityFont
implements MixinInterface {
    private int scaleFactor = new ScaledResolution(mc).getScaleFactor();
    private static final Pattern colorPattern = Pattern.compile("\u00c2\u00a7[0123456789abcdefklmnor]");
    public final int height = 9;
    private final Map<String, Float> stringWidthMap = new HashMap<String, Float>();
    private UnicodeFont font;
    private final String name;
    private final float size;
    private float aAFactor;

    public VelocityFont(String name, float size) {
        this.name = name;
        this.size = size;
        ScaledResolution sr = new ScaledResolution(mc);
        try {
            this.scaleFactor = sr.getScaleFactor();
            this.font = new UnicodeFont(this.getFontByName(name).deriveFont(size * (float)this.scaleFactor / 2.0f));
            this.font.addAsciiGlyphs();
            this.font.getEffects().add(new ColorEffect(Color.WHITE));
            this.font.loadGlyphs();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        this.aAFactor = sr.getScaleFactor();
    }

    private Font getFontByName(String name) throws IOException, FontFormatException {
        return this.getFontFromInput("/assets/velocity/fonts/" + name + ".ttf");
    }

    private Font getFontFromInput(String path) throws IOException, FontFormatException {
        return Font.createFont(0, VelocityFont.class.getResourceAsStream(path));
    }

    public int drawString(String text, float x2, float y2, int color) {
        if (text == null) {
            return 0;
        }
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        try {
            if (resolution.getScaleFactor() != this.scaleFactor) {
                this.scaleFactor = resolution.getScaleFactor();
                this.font = new UnicodeFont(this.getFontByName(this.name).deriveFont(this.size * (float)this.scaleFactor / 2.0f));
                this.font.addAsciiGlyphs();
                this.font.getEffects().add(new ColorEffect(Color.WHITE));
                this.font.loadGlyphs();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        this.aAFactor = resolution.getScaleFactor();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)(1.0f / this.aAFactor), (float)(1.0f / this.aAFactor), (float)(1.0f / this.aAFactor));
        y2 *= this.aAFactor;
        float originalX = x2 *= this.aAFactor;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        char[] characters = text.toCharArray();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.blendFunc((int)770, (int)771);
        String[] parts = colorPattern.split(text);
        int index = 0;
        for (String s2 : parts) {
            for (String s22 : s2.split("\n")) {
                for (String s3 : s22.split("\r")) {
                    this.font.drawString(x2, y2, s3, new org.newdawn.slick.Color(color));
                    x2 += (float)this.font.getWidth(s3);
                    if ((index += s3.length()) >= characters.length || characters[index] != '\r') continue;
                    x2 = originalX;
                    ++index;
                }
                if (index >= characters.length || characters[index] != '\n') continue;
                x2 = originalX;
                y2 += this.getHeight(s22) * 2.0f;
                ++index;
            }
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
        return (int)x2;
    }

    public int drawStringWithShadow(String text, float x2, float y2, int color) {
        this.drawString(StringUtils.stripControlCodes((String)text), x2 + 0.5f, y2 + 0.5f, 0);
        return this.drawString(text, x2, y2, color);
    }

    public int drawString(String text, float x2, float y2, int color, boolean shadow) {
        if (shadow) {
            this.drawStringWithShadow(text, x2, y2, color);
        } else {
            this.drawString(text, x2, y2, color);
        }
        return this.drawString(text, x2, y2, color);
    }

    public float getHeight(String s2) {
        return (float)this.font.getHeight(s2) / 2.0f;
    }

    public float getStringWidth(String text) {
        return this.font.getWidth(text) / 2;
    }
}

