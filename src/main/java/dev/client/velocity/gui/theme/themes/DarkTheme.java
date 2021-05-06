/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package dev.client.velocity.gui.theme.themes;

import dev.client.velocity.Velocity;
import dev.client.velocity.gui.theme.Color;
import dev.client.velocity.gui.theme.Theme;
import dev.client.velocity.gui.util.GuiUtil;
import dev.client.velocity.gui.util.RenderUtil;
import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.Module;
import dev.client.velocity.module.modules.client.ClickGui;
import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.SubSetting;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.mode.Mode;
import dev.client.velocity.setting.mode.SubMode;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.setting.slider.SubSlider;
import dev.client.velocity.util.client.ColorUtil;
import dev.client.velocity.util.client.MathUtil;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class DarkTheme
extends Theme
implements MixinInterface {
    public static int boost = 0;
    public static final String name = "Default";
    public static final int width = 105;
    public static final int height = 14;
    private static FontRenderer font = DarkTheme.mc.fontRenderer;

    public DarkTheme() {
        super(name, 105, 14);
    }

    public static void drawTextWithShadow(String text, float x2, float y2, int color) {
        if (ClickGui.customFont.getValue()) {
            Velocity.customFont.drawStringWithShadow(text, x2 - 1.0f, y2 - 2.0f, color);
        } else {
            font.drawStringWithShadow(text, x2, y2, color);
        }
    }

    public static void drawText(String text, float x2, float y2, int color) {
        if (ClickGui.customFont.getValue()) {
            Velocity.customFont.drawString(text, x2 - 1.0f, y2 - 2.0f, color);
        } else {
            font.drawString(text, x2, y2, color, false);
        }
    }

    @Override
    public void updateColors() {
        Color.updateColors();
    }

    @Override
    public void drawTitles(String name, int x2, int y2) {
        GuiScreen.drawRect((int)x2, (int)y2, (int)(x2 + 105), (int)(y2 + 14), (int)-872415232);
        GuiScreen.drawRect((int)(x2 - 1), (int)(y2 - 1), (int)(x2 + 105 + 1), (int)y2, (int)-14671840);
        DarkTheme.drawTextWithShadow(name, (float)(x2 + (x2 + 105 - x2) / 2) - (ClickGui.customFont.getValue() ? Velocity.customFont.getStringWidth(name) : (float)font.getStringWidth(name)) / 2.0f, y2 + 3, -1);
    }

    @Override
    public void drawModules(List<Module> modules, int x2, int y2) {
        boost = 0;
        for (Module m2 : modules) {
            if (GuiUtil.mouseOver(x2, y2 + 14 + 1 + boost * 14, x2 + 105 - 1, y2 + 28 + boost * 14)) {
                if (GuiUtil.ldown) {
                    m2.toggle();
                }
                if (GuiUtil.rdown) {
                    m2.toggleState();
                }
            }
            GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
            if (Color.GRADIENT) {
                if (m2.isEnabled()) {
                    DarkTheme.drawGradientBox(boost, boost + 1, x2 + 1, y2 + 14 + boost * 14 + 1, 104, 14);
                } else {
                    GuiScreen.drawRect((int)(x2 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 28 + boost * 14), (int)-870112477);
                }
            } else {
                GuiScreen.drawRect((int)(x2 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 28 + boost * 14), (int)(m2.isEnabled() ? Color.COLOR : -870112477));
            }
            DarkTheme.drawTextWithShadow(m2.getName(), x2 + 4, y2 + 14 + 4 + boost * 14, -1);
            if (m2.hasSettings()) {
                DarkTheme.drawText("...", x2 + 105 - 12, y2 + 1 + 14 + boost * 14, -1);
            }
            if (m2.isOpened()) {
                if (m2.hasSettings()) {
                    DarkTheme.drawDropdown(m2, x2, y2);
                }
                if (!m2.hasSettings()) {
                    ++boost;
                }
                DarkTheme.drawBind(m2, GuiUtil.keydown, x2, y2);
            }
            ++boost;
        }
        GuiScreen.drawRect((int)(x2 - 1), (int)y2, (int)x2, (int)(y2 + 14 + boost * 14 + 1), (int)-14671840);
        GuiScreen.drawRect((int)(x2 + 105), (int)y2, (int)(x2 + 105 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)-14671840);
        GuiScreen.drawRect((int)(x2 - 1), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 + 1), (int)(y2 + 14 + boost * 14 + 2), (int)-14671840);
        GuiScreen.drawRect((int)(x2 - 1), (int)(y2 + 14 + boost * 14), (int)(x2 + 105 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)-1725947872);
    }

    public static void drawDropdown(Module m2, int x2, int y2) {
        for (Setting s2 : m2.getSettings()) {
            SubSlider ssl;
            SubMode sm;
            SubCheckbox sc;
            ++boost;
            if (s2 instanceof Checkbox) {
                Checkbox c2 = (Checkbox)s2;
                DarkTheme.drawCheckbox(c2, x2, y2);
                for (SubSetting ss : c2.getSubSettings()) {
                    if (!c2.isOpened()) continue;
                    ++boost;
                    if (ss instanceof SubCheckbox) {
                        sc = (SubCheckbox)ss;
                        DarkTheme.drawSubCheckbox(sc, x2, y2);
                    }
                    if (ss instanceof SubMode) {
                        sm = (SubMode)ss;
                        DarkTheme.drawSubMode(sm, x2, y2);
                    }
                    if (ss instanceof SubSlider) {
                        ssl = (SubSlider)ss;
                        DarkTheme.drawSubSlider(ssl, x2, y2);
                    }
                    GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-14671840);
                }
            }
            if (s2 instanceof Mode) {
                Mode mode = (Mode)s2;
                DarkTheme.drawMode(mode, x2, y2);
                for (SubSetting ss : mode.getSubSettings()) {
                    if (!mode.isOpened()) continue;
                    ++boost;
                    if (ss instanceof SubCheckbox) {
                        sc = (SubCheckbox)ss;
                        DarkTheme.drawSubCheckbox(sc, x2, y2);
                    }
                    if (ss instanceof SubMode) {
                        sm = (SubMode)ss;
                        DarkTheme.drawSubMode(sm, x2, y2);
                    }
                    if (ss instanceof SubSlider) {
                        ssl = (SubSlider)ss;
                        DarkTheme.drawSubSlider(ssl, x2, y2);
                    }
                    GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-14671840);
                }
            }
            if (!(s2 instanceof Slider)) continue;
            Slider sl = (Slider)s2;
            DarkTheme.drawSlider(sl, x2, y2);
            for (SubSetting ss : sl.getSubSettings()) {
                if (!sl.isOpened()) continue;
                ++boost;
                if (ss instanceof SubCheckbox) {
                    sc = (SubCheckbox)ss;
                    DarkTheme.drawSubCheckbox(sc, x2, y2);
                }
                if (ss instanceof SubMode) {
                    sm = (SubMode)ss;
                    DarkTheme.drawSubMode(sm, x2, y2);
                }
                if (ss instanceof SubSlider) {
                    ssl = (SubSlider)ss;
                    DarkTheme.drawSubSlider(ssl, x2, y2);
                }
                GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-14671840);
            }
        }
        ++boost;
    }

    private static void drawCheckbox(Checkbox checkbox, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 4, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14)) {
            if (GuiUtil.ldown) {
                checkbox.toggleValue();
            }
            if (GuiUtil.rdown) {
                checkbox.toggleState();
            }
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        if (Color.GRADIENT) {
            if (checkbox.getValue()) {
                RenderUtil.drawRainbowBox(boost, boost + 1, x2 + 4, y2 + 14 + boost * 14 + 1, 100, 14);
            } else {
                GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(checkbox.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -870112477));
            }
        } else {
            GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(checkbox.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -870112477));
        }
        DarkTheme.drawTextWithShadow(checkbox.getName(), x2 + 7, y2 + 14 + 4 + boost * 14, -1);
        if (checkbox.hasSubSettings()) {
            DarkTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -1);
        }
    }

    private static void drawSubCheckbox(SubCheckbox sc, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && GuiUtil.ldown) {
            sc.toggleValue();
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        if (Color.GRADIENT) {
            if (sc.getValue()) {
                RenderUtil.drawRainbowBox(boost, boost + 1, x2 + 8, y2 + 14 + boost * 14 + 1, 96, 14);
            } else {
                GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(sc.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -870112477));
            }
        } else {
            GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(sc.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -870112477));
        }
        DarkTheme.drawTextWithShadow(sc.getName(), x2 + 10, y2 + 14 + 4 + boost * 14, -1);
    }

    private static void drawMode(Mode m2, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 4, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14)) {
            if (GuiUtil.ldown) {
                m2.setMode(m2.nextMode());
            }
            if (GuiUtil.rdown) {
                m2.toggleState();
            }
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-870112477);
        DarkTheme.drawTextWithShadow(m2.getName(), x2 + 7, y2 + 14 + 4 + boost * 14, -1);
        DarkTheme.drawTextWithShadow(m2.getMode(m2.getValue()), x2 + (ClickGui.customFont.getValue() ? 10 : 14) + font.getStringWidth(m2.getName()), y2 + 14 + 4 + boost * 14, -9013642);
        if (m2.hasSubSettings()) {
            DarkTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -1);
        }
    }

    private static void drawSubMode(SubMode sm, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && GuiUtil.ldown) {
            sm.setMode(sm.nextMode());
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-870112477);
        DarkTheme.drawTextWithShadow(sm.getName(), x2 + 12, y2 + 14 + 4 + boost * 14, -1);
        DarkTheme.drawTextWithShadow(sm.getMode(sm.getValue()), x2 + (ClickGui.customFont.getValue() ? 12 : 16) + font.getStringWidth(sm.getName()), y2 + 14 + 4 + boost * 14, -9013642);
    }

    private static void drawSlider(Slider sl, int x2, int y2) {
        int rectAdd = (int)MathHelper.clamp((double)((double)(x2 - 2 + 105 - (x2 + 3)) * ((sl.getValue() - sl.getMinValue()) / (sl.getMaxValue() - sl.getMinValue()))), (double)0.0, (double)(x2 + 3 + 105 - x2));
        if (GuiUtil.mouseOver(x2 + 4, y2 + 14 + boost * 14 + 2, x2 + 105, y2 + 14 + 14 + boost * 14) && GuiUtil.lheld) {
            int percentError = (GuiUtil.mX - (x2 + 4)) * 100 / (x2 + 105 - (x2 + 4));
            sl.setValue(MathUtil.roundDouble((double)percentError * ((sl.getMaxValue() - sl.getMinValue()) / 100.0) + sl.getMinValue(), sl.getRoundingScale()));
        }
        if (GuiUtil.mouseOver(x2 + 4, y2 + 14 + boost * 14 + 2, (int)((double)x2 + ClickGui.snapSub.getValue()), y2 + 14 + 14 + boost * 14) && ClickGui.snapSlider.getValue() && GuiUtil.lheld) {
            rectAdd = 0;
            sl.setValue(sl.getMinValue());
        }
        if (GuiUtil.mouseOver((int)((double)(x2 + 4 + 105) - ClickGui.snapSub.getValue()), y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && ClickGui.snapSlider.getValue() && GuiUtil.lheld) {
            rectAdd = x2 + 4 + 105 - (x2 + 4);
            sl.setValue(sl.getMaxValue());
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-870112477);
        if (Color.GRADIENT) {
            RenderUtil.drawRainbowBox(boost, boost + 1, x2 + 4, y2 + 14 + boost * 14 + 1, rectAdd > 104 ? 100 : rectAdd, 14);
        } else {
            GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 4 + (rectAdd > 104 ? 100 : rectAdd)), (int)(y2 + 14 + 14 + boost * 14), (int)(Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR));
        }
        DarkTheme.drawTextWithShadow(sl.getName(), x2 + 6, y2 + 14 + 4 + boost * 14, -1);
        DarkTheme.drawTextWithShadow(Double.toString(sl.getValue()), x2 + font.getStringWidth(sl.getName()) + (ClickGui.customFont.getValue() ? 8 : 12), y2 + 14 + 4 + boost * 14, -9013642);
        if (sl.hasSubSettings()) {
            DarkTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -1);
        }
    }

    private static void drawSubSlider(SubSlider ssl, int x2, int y2) {
        int rectAdd = (int)MathHelper.clamp((double)((double)(x2 - 2 + 105 - (x2 + 7)) * ((ssl.getValue() - ssl.getMinValue()) / (ssl.getMaxValue() - ssl.getMinValue()))), (double)0.0, (double)(x2 + 7 + 105 - x2));
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, x2 + 105, y2 + 14 + 14 + boost * 14) && GuiUtil.lheld) {
            int percentError = (GuiUtil.mX - (x2 + 8)) * 100 / (x2 + 105 - (x2 + 8));
            ssl.setValue(MathUtil.roundDouble((double)percentError * ((ssl.getMaxValue() - ssl.getMinValue()) / 100.0) + ssl.getMinValue(), ssl.getRoundingScale()));
        }
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, (int)((double)x2 + ClickGui.snapSub.getValue()), y2 + 14 + 14 + boost * 14) && ClickGui.snapSlider.getValue() && GuiUtil.lheld) {
            rectAdd = 0;
            ssl.setValue(ssl.getMinValue());
        }
        if (GuiUtil.mouseOver((int)((double)(x2 + 8 + 105) - ClickGui.snapSub.getValue()), y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && ClickGui.snapSlider.getValue() && GuiUtil.lheld) {
            rectAdd = x2 + 8 + 105 - (x2 + 8);
            ssl.setValue(ssl.getMaxValue());
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-870112477);
        if (Color.GRADIENT) {
            RenderUtil.drawRainbowBox(boost, boost + 1, x2 + 8, y2 + 14 + boost * 14 + 1, rectAdd > 105 ? 97 : rectAdd, 14);
        } else {
            GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 8 + (rectAdd > 105 ? 97 : rectAdd)), (int)(y2 + 14 + 14 + boost * 14), (int)Color.COLOR);
        }
        DarkTheme.drawTextWithShadow(ssl.getName(), x2 + 10, y2 + 14 + 4 + boost * 14, -1);
        DarkTheme.drawTextWithShadow(Double.toString(ssl.getValue()), x2 + font.getStringWidth(ssl.getName()) + (ClickGui.customFont.getValue() ? 8 : 12), y2 + 14 + 4 + boost * 14, -9013642);
    }

    public static void drawBind(Module m2, int key, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 4, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && GuiUtil.ldown) {
            m2.setBinding(true);
        }
        if (m2.isBinding() && key != -1 && key != 1) {
            if (key == 211) {
                m2.key = -1;
                m2.setBinding(false);
            } else {
                m2.key = key;
            }
            m2.setBinding(false);
        }
        if (m2.isBinding() && key == 1) {
            m2.setBinding(false);
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1725947872);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-870112477);
        if (!m2.isBinding()) {
            DarkTheme.drawTextWithShadow("Keybind", x2 + 7, y2 + 14 + 4 + boost * 14, -1);
            if (m2.key == -1) {
                DarkTheme.drawTextWithShadow("NONE", x2 + (ClickGui.customFont.getValue() ? 6 : 10) + font.getStringWidth("Keybind") + 3, y2 + 14 + 4 + boost * 14, -9013642);
                m2.setBinding(false);
            } else {
                DarkTheme.drawTextWithShadow(Keyboard.getKeyName((int)m2.key), x2 + (ClickGui.customFont.getValue() ? 6 : 10) + font.getStringWidth("Keybind") + 3, y2 + 14 + 4 + boost * 14, -9013642);
                m2.setBinding(false);
            }
        } else {
            DarkTheme.drawTextWithShadow("Listening...", x2 + 7, y2 + 14 + 4 + boost * 14, -1);
        }
    }

    public static void drawGradientBox(long boost, long boost2, int x2, int y2, int width, int height) {
        java.awt.Color color = new java.awt.Color(ColorUtil.rainbow(boost));
        java.awt.Color color2 = new java.awt.Color(ColorUtil.rainbow(boost2));
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

