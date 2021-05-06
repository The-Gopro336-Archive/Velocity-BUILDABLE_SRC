/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.player;

import dev.client.velocity.event.events.packet.PacketSendEvent;
import dev.client.velocity.module.Module;
import dev.client.velocity.setting.checkbox.Checkbox;
import dev.client.velocity.setting.slider.Slider;
import dev.client.velocity.util.client.Timer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PacketEat
extends Module {
    public static Slider delay = new Slider("Delay", 0.0, 6.0, 10.0, 0);
    private static Checkbox deSync = new Checkbox("DeSync", true);
    Timer timer = new Timer();

    public PacketEat() {
        super("PacketEat", Module.Category.PLAYER, "Allows you to eat instantly");
    }

    @Override
    public void setup() {
        this.addSetting(delay);
        this.addSetting(deSync);
    }

    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        if (item.equals((Object)Items.GOLDEN_APPLE)) {
            if (this.timer.passed((long)(delay.getValue() * 10.0))) {
                event.setCanceled(true);
                item.onItemUseFinish(itemStack, event.getWorld(), (EntityLivingBase)event.getEntityPlayer());
            }
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && PacketEat.mc.player.isHandActive() && deSync.getValue()) {
            event.setCanceled(true);
        }
    }
}

