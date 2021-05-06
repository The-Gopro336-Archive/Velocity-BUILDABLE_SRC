/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 */
package dev.client.velocity.util.world;

import dev.client.velocity.mixin.MixinInterface;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class InventoryUtil
implements MixinInterface {
    public static void switchToSlot(int slot) {
        InventoryUtil.mc.player.inventory.currentItem = slot;
    }

    public static void switchToSlot(Item item) {
        InventoryUtil.mc.player.inventory.currentItem = InventoryUtil.getHotbarItemSlot(item);
    }

    public static void switchToSlotGhost(int slot) {
        InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
    }

    public static void switchToSlotGhost(Item item) {
        InventoryUtil.switchToSlotGhost(InventoryUtil.getHotbarItemSlot(item));
    }

    public static void moveItemToOffhand(int slot) {
        boolean moving = false;
        boolean returning = false;
        int returnSlot = -1;
        if (slot == -1) {
            return;
        }
        InventoryUtil.mc.playerController.windowClick(0, slot < 9 ? slot + 36 : slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        for (int i2 = 0; i2 < 45; ++i2) {
            if (!InventoryUtil.mc.player.inventory.getStackInSlot(i2).isEmpty()) continue;
            returnSlot = i2;
            break;
        }
        if (returnSlot != -1) {
            InventoryUtil.mc.playerController.windowClick(0, returnSlot < 9 ? returnSlot + 36 : returnSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        }
    }

    public static void moveItemToOffhand(Item item) {
        int slot = InventoryUtil.getInventoryItemSlot(item);
        if (slot != -1) {
            InventoryUtil.moveItemToOffhand(slot);
        }
    }

    public static int getHotbarItemSlot(Item item) {
        int slot = 0;
        for (int i2 = 0; i2 < 9; ++i2) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i2).getItem() != item) continue;
            slot = i2;
            break;
        }
        return slot;
    }

    public static int getInventoryItemSlot(Item item) {
        int slot = -1;
        for (int i2 = 0; i2 < 45; ++i2) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(i2).getItem() != item) continue;
            slot = i2;
            break;
        }
        return slot;
    }

    public static int getBlockInHotbar(Block block) {
        for (int i2 = 0; i2 < 9; ++i2) {
            Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i2).getItem();
            if (!(item instanceof ItemBlock) || !((ItemBlock)item).getBlock().equals((Object)block)) continue;
            return i2;
        }
        return -1;
    }

    public static int getAnyBlockInHotbar() {
        for (int i2 = 0; i2 < 9; ++i2) {
            Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i2).getItem();
            if (!(item instanceof ItemBlock)) continue;
            return i2;
        }
        return -1;
    }

    public static int getItemCount(Item item) {
        int count = InventoryUtil.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == item).mapToInt(ItemStack::getCount).sum();
        return count;
    }

    public static boolean Is32k(ItemStack stack) {
        if (stack.getEnchantmentTagList() != null) {
            NBTTagList tags = stack.getEnchantmentTagList();
            for (int i2 = 0; i2 < tags.tagCount(); ++i2) {
                NBTTagCompound tagCompound = tags.getCompoundTagAt(i2);
                if (tagCompound == null || Enchantment.getEnchantmentByID((int)tagCompound.getByte("id")) == null) continue;
                Enchantment enchantment = Enchantment.getEnchantmentByID((int)tagCompound.getShort("id"));
                short lvl = tagCompound.getShort("lvl");
                if (enchantment == null || enchantment.isCurse() || lvl < 1000) continue;
                return true;
            }
        }
        return false;
    }
}

