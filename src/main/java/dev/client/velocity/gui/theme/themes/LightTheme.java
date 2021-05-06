/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.input.Keyboard
 */
package dev.client.velocity.gui.theme.themes;

import dev.client.velocity.Velocity;
import dev.client.velocity.gui.theme.Color;
import dev.client.velocity.gui.theme.Theme;
import dev.client.velocity.gui.util.GuiUtil;
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

public class LightTheme
extends Theme
implements MixinInterface {
    public static int boost = 0;
    public static final String name = "Default";
    public static final int width = 105;
    public static final int height = 14;
    private static FontRenderer font = LightTheme.mc.fontRenderer;

    public LightTheme() {
        super(name, 105, 14);
    }

    public static void drawTextWithShadow(String text, float x2, float y2, int color) {
        if (ClickGui.customFont.getValue()) {
            Velocity.customFont.drawString(text, x2 - 1.0f, y2 - 2.0f, color, false);
        } else {
            font.drawString(text, x2, y2, color, false);
        }
    }

    public static void drawText(String text, float x2, float y2, int color) {
        if (ClickGui.customFont.getValue()) {
            Velocity.customFont.drawString(text, x2 - 1.0f, y2 - 2.0f, color, false);
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
        GuiScreen.drawRect((int)x2, (int)y2, (int)(x2 + 105), (int)(y2 + 14), (int)-858993460);
        GuiScreen.drawRect((int)(x2 - 1), (int)(y2 - 1), (int)(x2 + 105 + 1), (int)y2, (int)-570425345);
        LightTheme.drawTextWithShadow(name, (float)(x2 + (x2 + 105 - x2) / 2) - (ClickGui.customFont.getValue() ? Velocity.customFont.getStringWidth(name) : (float)font.getStringWidth(name)) / 2.0f, y2 + 3, -14606047);
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
            GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
            GuiScreen.drawRect((int)(x2 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 28 + boost * 14), (int)(m2.isEnabled() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -891429411));
            LightTheme.drawTextWithShadow(m2.getName(), x2 + 4, y2 + 14 + 4 + boost * 14, -14606047);
            if (m2.hasSettings()) {
                LightTheme.drawText("...", x2 + 105 - 12, y2 + 1 + 14 + boost * 14, -14606047);
            }
            if (m2.isOpened()) {
                if (m2.hasSettings()) {
                    LightTheme.drawDropdown(m2, x2, y2);
                }
                if (!m2.hasSettings()) {
                    ++boost;
                }
                LightTheme.drawBind(m2, GuiUtil.keydown, x2, y2);
            }
            ++boost;
        }
        GuiScreen.drawRect((int)(x2 - 1), (int)y2, (int)x2, (int)(y2 + 14 + boost * 14 + 1), (int)-570425345);
        GuiScreen.drawRect((int)(x2 + 105), (int)y2, (int)(x2 + 105 + 1), (int)(y2 + 14 + boost * 14 + 1), (int)-570425345);
        GuiScreen.drawRect((int)(x2 - 1), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 + 1), (int)(y2 + 14 + boost * 14 + 2), (int)-570425345);
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 14 + boost * 14 + 1), (int)-1716868438);
    }

    public static void drawDropdown(Module m2, int x2, int y2) {
        for (Setting s2 : m2.getSettings()) {
            SubSlider ssl;
            SubMode sm;
            SubCheckbox sc;
            ++boost;
            if (s2 instanceof Checkbox) {
                Checkbox c2 = (Checkbox)s2;
                LightTheme.drawCheckbox(c2, x2, y2);
                for (SubSetting ss : c2.getSubSettings()) {
                    if (!c2.isOpened()) continue;
                    ++boost;
                    if (ss instanceof SubCheckbox) {
                        sc = (SubCheckbox)ss;
                        LightTheme.drawSubCheckbox(sc, x2, y2);
                    }
                    if (ss instanceof SubMode) {
                        sm = (SubMode)ss;
                        LightTheme.drawSubMode(sm, x2, y2);
                    }
                    if (ss instanceof SubSlider) {
                        ssl = (SubSlider)ss;
                        LightTheme.drawSubSlider(ssl, x2, y2);
                    }
                    GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-2236963);
                }
            }
            if (s2 instanceof Mode) {
                Mode mode = (Mode)s2;
                LightTheme.drawMode(mode, x2, y2);
                for (SubSetting ss : mode.getSubSettings()) {
                    if (!mode.isOpened()) continue;
                    ++boost;
                    if (ss instanceof SubCheckbox) {
                        sc = (SubCheckbox)ss;
                        LightTheme.drawSubCheckbox(sc, x2, y2);
                    }
                    if (ss instanceof SubMode) {
                        sm = (SubMode)ss;
                        LightTheme.drawSubMode(sm, x2, y2);
                    }
                    if (ss instanceof SubSlider) {
                        ssl = (SubSlider)ss;
                        LightTheme.drawSubSlider(ssl, x2, y2);
                    }
                    GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-2236963);
                }
            }
            if (!(s2 instanceof Slider)) continue;
            Slider sl = (Slider)s2;
            LightTheme.drawSlider(sl, x2, y2);
            for (SubSetting ss : sl.getSubSettings()) {
                if (!sl.isOpened()) continue;
                ++boost;
                if (ss instanceof SubCheckbox) {
                    sc = (SubCheckbox)ss;
                    LightTheme.drawSubCheckbox(sc, x2, y2);
                }
                if (ss instanceof SubMode) {
                    sm = (SubMode)ss;
                    LightTheme.drawSubMode(sm, x2, y2);
                }
                if (ss instanceof SubSlider) {
                    ssl = (SubSlider)ss;
                    LightTheme.drawSubSlider(ssl, x2, y2);
                }
                GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + boost * 14 + 1), (int)(x2 + 5), (int)(y2 + 28 + boost * 14), (int)-2236963);
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
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(checkbox.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -891429411));
        LightTheme.drawTextWithShadow(checkbox.getName(), x2 + 7, y2 + 14 + 4 + boost * 14, -14606047);
        if (checkbox.hasSubSettings()) {
            LightTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -14606047);
        }
    }

    private static void drawSubCheckbox(SubCheckbox sc, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && GuiUtil.ldown) {
            sc.toggleValue();
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)(sc.getValue() ? (Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR) : -891429411));
        LightTheme.drawTextWithShadow(sc.getName(), x2 + 10, y2 + 14 + 4 + boost * 14, -14606047);
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
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-891429411);
        LightTheme.drawTextWithShadow(m2.getName(), x2 + 7, y2 + 14 + 4 + boost * 14, -14606047);
        LightTheme.drawTextWithShadow(m2.getMode(m2.getValue()), x2 + (ClickGui.customFont.getValue() ? 10 : 14) + font.getStringWidth(m2.getName()), y2 + 14 + 4 + boost * 14, -11250604);
        if (m2.hasSubSettings()) {
            LightTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -14606047);
        }
    }

    private static void drawSubMode(SubMode sm, int x2, int y2) {
        if (GuiUtil.mouseOver(x2 + 8, y2 + 14 + boost * 14 + 2, x2 + 105 - 1, y2 + 14 + 14 + boost * 14) && GuiUtil.ldown) {
            sm.setMode(sm.nextMode());
        }
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-891429411);
        LightTheme.drawTextWithShadow(sm.getName(), x2 + 12, y2 + 14 + 4 + boost * 14, -14606047);
        LightTheme.drawTextWithShadow(sm.getMode(sm.getValue()), x2 + (ClickGui.customFont.getValue() ? 12 : 16) + font.getStringWidth(sm.getName()), y2 + 14 + 4 + boost * 14, -11250604);
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
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-891429411);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 4 + (rectAdd > 104 ? 100 : rectAdd)), (int)(y2 + 14 + 14 + boost * 14), (int)(Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR));
        LightTheme.drawTextWithShadow(sl.getName(), x2 + 6, y2 + 14 + 4 + boost * 14, -14606047);
        LightTheme.drawTextWithShadow(Double.toString(sl.getValue()), x2 + font.getStringWidth(sl.getName()) + (ClickGui.customFont.getValue() ? 8 : 12), y2 + 14 + 4 + boost * 14, -11250604);
        if (sl.hasSubSettings()) {
            LightTheme.drawText("...", x2 + 105 - 12, y2 + 14 + 1 + boost * 14, -14606047);
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
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-891429411);
        GuiScreen.drawRect((int)(x2 + 8), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 8 + (rectAdd > 105 ? 97 : rectAdd)), (int)(y2 + 14 + 14 + boost * 14), (int)(Color.GRADIENT ? ColorUtil.rainbow(boost) : Color.COLOR));
        LightTheme.drawTextWithShadow(ssl.getName(), x2 + 10, y2 + 14 + 4 + boost * 14, -14606047);
        LightTheme.drawTextWithShadow(Double.toString(ssl.getValue()), x2 + font.getStringWidth(ssl.getName()) + (ClickGui.customFont.getValue() ? 8 : 12), y2 + 14 + 4 + boost * 14, -11250604);
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
        GuiScreen.drawRect((int)x2, (int)(y2 + 14 + boost * 14), (int)(x2 + 105), (int)(y2 + 28 + boost * 14), (int)-1716868438);
        GuiScreen.drawRect((int)(x2 + 4), (int)(y2 + 14 + boost * 14 + 1), (int)(x2 + 105 - 1), (int)(y2 + 14 + 14 + boost * 14), (int)-891429411);
        if (!m2.isBinding()) {
            LightTheme.drawTextWithShadow("Keybind", x2 + 7, y2 + 14 + 4 + boost * 14, -14606047);
            if (m2.key == -1) {
                LightTheme.drawTextWithShadow("NONE", x2 + (ClickGui.customFont.getValue() ? 6 : 10) + font.getStringWidth("Keybind") + 3, y2 + 14 + 4 + boost * 14, -14606047);
                m2.setBinding(false);
            } else {
                LightTheme.drawTextWithShadow(Keyboard.getKeyName((int)m2.key), x2 + (ClickGui.customFont.getValue() ? 6 : 10) + font.getStringWidth("Keybind") + 3, y2 + 14 + 4 + boost * 14, -14606047);
                m2.setBinding(false);
            }
        } else {
            LightTheme.drawTextWithShadow("Listening...", x2 + 7, y2 + 14 + 4 + boost * 14, -14606047);
        }
    }
}

