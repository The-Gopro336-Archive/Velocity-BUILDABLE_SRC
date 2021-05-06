/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.gui.theme;

import dev.client.velocity.module.modules.client.Colors;
import dev.client.velocity.util.client.ColorUtil;

public class Color {
    public static boolean GRADIENT;
    public static int COLOR;

    public static void updateColors() {
        if (Colors.rainbow.getValue()) {
            if (Colors.gradient.getValue()) {
                GRADIENT = true;
            } else {
                GRADIENT = false;
                COLOR = ColorUtil.rainbow(1L);
            }
        } else {
            COLOR = ColorUtil.toRGBA((int)Colors.r.getValue(), (int)Colors.g.getValue(), (int)Colors.b.getValue(), (int)Colors.a.getValue());
        }
    }
}

