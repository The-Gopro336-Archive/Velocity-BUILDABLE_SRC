/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextFormatting
 */
package dev.client.velocity.util.client;

import dev.client.velocity.mixin.MixinInterface;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessageUtil
implements MixinInterface {
    public static void sendClientMessage(String message) {
        MessageUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString((Object)TextFormatting.DARK_PURPLE + "<Velocity> " + (Object)TextFormatting.RESET + message));
    }

    public static void sendPublicMessage(String message) {
        MessageUtil.mc.player.sendChatMessage(message);
    }

    public static String toUnicode(String s2) {
        return s2.toLowerCase().replace("a", "\u1d00").replace("b", "\u0299").replace("c", "\u1d04").replace("d", "\u1d05").replace("e", "\u1d07").replace("f", "\ua730").replace("g", "\u0262").replace("h", "\u029c").replace("i", "\u026a").replace("j", "\u1d0a").replace("k", "\u1d0b").replace("l", "\u029f").replace("m", "\u1d0d").replace("n", "\u0274").replace("o", "\u1d0f").replace("p", "\u1d18").replace("q", "\u01eb").replace("r", "\u0280").replace("s", "\ua731").replace("t", "\u1d1b").replace("u", "\u1d1c").replace("v", "\u1d20").replace("w", "\u1d21").replace("x", "\u02e3").replace("y", "\u028f").replace("z", "\u1d22");
    }
}

