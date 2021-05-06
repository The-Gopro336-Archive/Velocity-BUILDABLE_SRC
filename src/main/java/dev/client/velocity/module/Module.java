/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  javax.annotation.Nullable
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.client.velocity.event.events.render.Render3DEvent;
import dev.client.velocity.mixin.MixinInterface;
import dev.client.velocity.module.ModuleManager;
import dev.client.velocity.module.modules.misc.Notify;
import dev.client.velocity.setting.Setting;
import dev.client.velocity.util.client.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class Module
implements MixinInterface {
    private final String name;
    private final Category category;
    private final String description;
    public int key;
    private boolean enabled;
    private boolean opened;
    protected boolean isKeyDown = false;
    private boolean isBinding;
    public List<Setting> settingsList = new ArrayList<Setting>();

    public Module(String name, Category category, @Nullable String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.enabled = false;
        this.opened = false;
        this.key = -1;
        this.setup();
    }

    public void setup() {
    }

    public void addSetting(Setting s2) {
        this.settingsList.add(s2);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isKeyDown() {
        return this.isKeyDown;
    }

    public void setKeyDown(boolean b2) {
        this.isKeyDown = b2;
    }

    public void onEnable() {
        if (ModuleManager.getModuleByClass(Notify.class).isEnabled() && !this.name.equalsIgnoreCase("clickgui")) {
            MessageUtil.sendClientMessage(this.name + (Object)ChatFormatting.GREEN + " enabled");
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void onDisable() {
        if (ModuleManager.getModuleByClass(Notify.class).isEnabled() && !this.name.equalsIgnoreCase("clickgui")) {
            MessageUtil.sendClientMessage(this.name + (Object)ChatFormatting.RED + " disabled");
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public void onUpdate() {
    }

    public void onFastUpdate() {
    }

    public void onRender3D(Render3DEvent eventRender) {
    }

    public void toggle() {
        this.enabled = !this.enabled;
        try {
            if (this.isEnabled()) {
                this.onEnable();
            } else {
                this.onDisable();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void enable() {
        if (!this.isEnabled()) {
            this.enabled = true;
            try {
                this.onEnable();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void disable() {
        if (this.isEnabled()) {
            this.enabled = false;
            try {
                this.onDisable();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        ModuleManager.onRender3D(event);
    }

    public boolean Null() {
        return Module.mc.player == null || Module.mc.world == null;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public int getKeybind() {
        return this.key;
    }

    public boolean hasSettings() {
        return this.settingsList.size() > 0;
    }

    public List<Setting> getSettings() {
        return this.settingsList;
    }

    public void toggleState() {
        this.opened = !this.opened;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public boolean isBinding() {
        return this.isBinding;
    }

    public void setBinding(boolean b2) {
        this.isBinding = b2;
    }

    public void setEnabled(boolean b2) {
        this.enabled = b2;
    }

    public static enum Category {
        CLIENT("Client"),
        COMBAT("Combat"),
        PLAYER("Player"),
        MISC("Misc"),
        MOVEMENT("Movement"),
        RENDER("Render"),
        HUD("Hud");

        private final String name;

        private Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

