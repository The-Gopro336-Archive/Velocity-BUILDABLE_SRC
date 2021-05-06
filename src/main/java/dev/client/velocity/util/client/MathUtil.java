/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.util.client;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
    public static double roundDouble(double number, int scale) {
        BigDecimal bd2 = new BigDecimal(number);
        bd2 = bd2.setScale(scale, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10.0, places);
        return (double)Math.round(value * scale) / scale;
    }
}

