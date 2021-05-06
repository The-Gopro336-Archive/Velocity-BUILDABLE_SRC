/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package dev.client.velocity.module.modules.misc;

import com.mojang.authlib.GameProfile;
import dev.client.velocity.module.Module;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    public FakePlayer() {
        super("FakePlayer", Module.Category.MISC, "Creates a fake motionless player");
    }

    @Override
    public void onEnable() {
        if (this.Null()) {
            return;
        }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("873e2766-9254-49bc-89d7-5d4d585ad29d"), "linustouchtips24"));
        fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        FakePlayer.mc.world.addEntityToWorld(69420, (Entity)fakePlayer);
    }

    @Override
    public void onDisable() {
        FakePlayer.mc.world.removeEntityFromWorld(69420);
    }
}

