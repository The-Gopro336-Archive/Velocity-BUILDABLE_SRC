/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package dev.client.velocity.module;

import dev.client.velocity.event.events.render.Render3DEvent;
import dev.client.velocity.gui.theme.Color;
import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.Module;
import dev.client.velocity.module.modules.client.ClickGui;
import dev.client.velocity.module.modules.client.Colors;
import dev.client.velocity.module.modules.client.HudEditor;
import dev.client.velocity.module.modules.combat.Aura;
import dev.client.velocity.module.modules.combat.AutoCrystal;
import dev.client.velocity.module.modules.combat.AutoTotem;
import dev.client.velocity.module.modules.combat.Burrow;
import dev.client.velocity.module.modules.combat.Criticals;
import dev.client.velocity.module.modules.combat.Offhand;
import dev.client.velocity.module.modules.combat.QuickBow;
import dev.client.velocity.module.modules.combat.QuickEXP;
import dev.client.velocity.module.modules.combat.Quiver;
import dev.client.velocity.module.modules.combat.Trigger;
import dev.client.velocity.module.modules.hud.FPS;
import dev.client.velocity.module.modules.hud.ServerIP;
import dev.client.velocity.module.modules.misc.AutoDisconnect;
import dev.client.velocity.module.modules.misc.ChatSuffix;
import dev.client.velocity.module.modules.misc.FakePlayer;
import dev.client.velocity.module.modules.misc.Notify;
import dev.client.velocity.module.modules.misc.Timer;
import dev.client.velocity.module.modules.movement.AirJump;
import dev.client.velocity.module.modules.movement.AutoJump;
import dev.client.velocity.module.modules.movement.AutoWalk;
import dev.client.velocity.module.modules.movement.Parkour;
import dev.client.velocity.module.modules.movement.ReverseStep;
import dev.client.velocity.module.modules.movement.Scaffold;
import dev.client.velocity.module.modules.movement.Sprint;
import dev.client.velocity.module.modules.movement.Step;
import dev.client.velocity.module.modules.movement.Velocity;
import dev.client.velocity.module.modules.player.ExtraSlots;
import dev.client.velocity.module.modules.player.HandProgress;
import dev.client.velocity.module.modules.player.NoHunger;
import dev.client.velocity.module.modules.player.NoRotate;
import dev.client.velocity.module.modules.player.PacketEat;
import dev.client.velocity.module.modules.player.Swing;
import dev.client.velocity.module.modules.render.CustomFOV;
import dev.client.velocity.module.modules.render.ESP;
import dev.client.velocity.module.modules.render.FullBright;
import dev.client.velocity.module.modules.render.NoBob;
import dev.client.velocity.module.modules.render.NoWeather;
import dev.client.velocity.module.modules.render.Skeleton;
import dev.client.velocity.util.render.RenderUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ModuleManager
implements MixinInterface {
    private static final List<Module> modules = Arrays.asList(new ClickGui(), new Colors(), new HudEditor(), new AutoCrystal(), new QuickEXP(), new Criticals(), new Trigger(), new QuickBow(), new Aura(), new AutoTotem(), new Offhand(), new Quiver(), new Burrow(), new ExtraSlots(), new HandProgress(), new Swing(), new PacketEat(), new NoHunger(), new NoRotate(), new AutoDisconnect(), new ChatSuffix(), new Notify(), new FakePlayer(), new Timer(), new Velocity(), new ReverseStep(), new AutoJump(), new AutoWalk(), new Parkour(), new AirJump(), new Sprint(), new Step(), new Scaffold(), new ESP(), new CustomFOV(), new NoWeather(), new NoBob(), new FullBright(), new Skeleton(), new FPS(), new ServerIP());

    public static List<Module> getModules() {
        return new ArrayList<Module>(modules);
    }

    public static List<Module> getModulesInCategory(Module.Category cat) {
        ArrayList<Module> module = new ArrayList<Module>();
        for (Module m2 : modules) {
            if (!m2.getCategory().equals((Object)cat)) continue;
            module.add(m2);
        }
        return module;
    }

    public static Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Module getModuleByClass(Class<?> clazz) {
        return modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        ModuleManager.onUpdate();
        Color.updateColors();
    }

    @SubscribeEvent
    public void onFastTick(TickEvent event) {
        ModuleManager.onFastUpdate();
    }

    public static void onUpdate() {
        for (Module m2 : modules) {
            try {
                if (!m2.isEnabled()) continue;
                m2.onUpdate();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void onFastUpdate() {
        for (Module m2 : modules) {
            try {
                if (!m2.isEnabled()) continue;
                m2.onFastUpdate();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module module : modules) {
                if (module.key != Keyboard.getEventKey()) continue;
                module.toggle();
            }
        }
    }

    @SubscribeEvent
    public static void onRender3D(RenderWorldLastEvent event) {
        HudEditor.boost = 0;
        Minecraft.getMinecraft().profiler.startSection("velocity");
        Minecraft.getMinecraft().profiler.startSection("setup");
        RenderUtil.prepareProfiler();
        Render3DEvent e2 = new Render3DEvent(event.getPartialTicks());
        Minecraft.getMinecraft().profiler.endSection();
        modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onRender3D(e2);
            Minecraft.getMinecraft().profiler.endSection();
        });
        Minecraft.getMinecraft().profiler.startSection("release");
        RenderUtil.releaseRender();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }
}

