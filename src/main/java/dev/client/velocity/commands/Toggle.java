/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.server.MinecraftServer
 *  net.minecraftforge.client.IClientCommand
 */
package dev.client.velocity.commands;

import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.util.client.MessageUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

public class Toggle
extends CommandBase
implements IClientCommand {
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public String getName() {
        return "toggle";
    }

    public String getUsage(ICommandSender sender) {
        return null;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageUtil.sendClientMessage("Usage: /toggle [module]");
        }
        if (args.length == 1) {
            ModuleManager.getModuleByName(args[0]).toggle();
        }
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}

