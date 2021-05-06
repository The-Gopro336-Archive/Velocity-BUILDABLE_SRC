/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.command.ICommand
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.Display
 */
package dev.client.velocity;

import dev.client.velocity.commands.Suffix;
import dev.client.velocity.commands.Toggle;
import dev.client.velocity.gui.main.VelocityWindow;
import dev.client.velocity.gui.theme.Theme;
import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.util.client.VelocityFont;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid="velocity", name="Velocity", version="0.1", acceptedMinecraftVersions="[1.12.2]")
public class Velocity
implements MixinInterface {
    public static final String MODID = "velocity";
    public static final String NAME = "Velocity";
    public static final String VERSION = "0.1";
    public static final Logger LOGGER;
    public static FontRenderer mcFont;
    public static VelocityFont customFont;
    public static ModuleManager moduleManager;
    @Mod.Instance
    private static Velocity INSTANCE;

    public Velocity() {
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle((String)"Velocity Utility Mod 0.1");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Mod Initialized!");
        VelocityWindow.initGui();
        LOGGER.info("ClickGui Initialized!");
        Theme.initThemes();
        LOGGER.info("GUI Themes Initialized!");
        moduleManager = new ModuleManager();
        this.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.registerCommands();
        LOGGER.info("Commands Initialized!");
    }

    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)moduleManager);
    }

    public void registerCommands() {
        ClientCommandHandler.instance.registerCommand((ICommand)new Toggle());
        ClientCommandHandler.instance.registerCommand((ICommand)new Suffix());
    }

    static {
        mcFont = Velocity.mc.fontRenderer;
        customFont = new VelocityFont("Verdana", 18.0f);
        LOGGER = LogManager.getLogger((String)NAME);
    }
}

