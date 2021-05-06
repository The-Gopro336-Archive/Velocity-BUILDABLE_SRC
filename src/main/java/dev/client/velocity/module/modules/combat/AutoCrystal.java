/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev.client.velocity.module.modules.combat;

import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import dev.client.velocity.event.events.packet.PacketReceiveEvent;
import dev.client.velocity.util.client.MathUtil;
import java.awt.Color;
import dev.client.velocity.util.render.RenderUtil;
import dev.client.velocity.event.events.render.Render3DEvent;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import dev.client.velocity.util.combat.EnemyUtil;
import net.minecraft.entity.EntityLivingBase;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import dev.client.velocity.util.combat.CrystalUtil;
import dev.client.velocity.util.world.PlayerUtil;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import dev.client.velocity.setting.Setting;
import dev.client.velocity.setting.mode.SubMode;
import dev.client.velocity.setting.checkbox.SubCheckbox;
import dev.client.velocity.setting.slider.SubSlider;
import dev.client.velocity.setting.checkbox.Checkbox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import dev.client.velocity.util.client.Timer;
import dev.client.velocity.module.Module;

public class AutoCrystal extends Module
{
    Timer breakTimer;
    public static EntityPlayer lastTarget;
    private long placeSystemTime;
    private boolean switchCooldown;
    private BlockPos render;
    private Entity renderEnt;
    public static Checkbox explode;
    public static SubSlider breakRange;
    public static SubSlider breakDelay;
    public static SubSlider breakAttempts;
    public static SubCheckbox antiDeSync;
    public static SubCheckbox syncBreak;
    public static SubCheckbox unload;
    public static SubCheckbox rotate;
    public static SubCheckbox packetBreak;
    public static SubMode breakHand;
    public static Checkbox pause;
    public static SubSlider pauseHealth;
    public static SubMode pauseMode;
    public static Checkbox place;
    public static SubSlider placeRange;
    public static SubSlider enemyRange;
    public static SubSlider placeDelay;
    public static SubSlider minDamage;
    public static SubCheckbox autoSwitch;
    public static SubCheckbox rayTrace;
    public static SubCheckbox multiPlace;
    public static Checkbox facePlace;
    public static SubSlider facePlaceHealth;
    public static SubCheckbox armorMelt;
    public static SubSlider armorDurability;
    public static Checkbox calculations;
    public static SubMode placeCalc;
    public static SubMode damageCalc;
    public static SubMode blockCalc;
    public static Checkbox logic;
    public static SubMode logicMode;
    public static Checkbox hole;
    public static SubCheckbox multiPlaceInHole;
    public static Checkbox renderCrystal;
    public static SubSlider r;
    public static SubSlider g;
    public static SubSlider b;
    public static SubSlider a;
    public static SubCheckbox renderDamage;
    public static SubCheckbox outline;

    public AutoCrystal() {
        super("AutoCrystal", Category.COMBAT, "Automatically places and explodes crystals");
        this.breakTimer = new Timer();
        this.switchCooldown = false;
    }

    @Override
    public void setup() {
        this.addSetting(AutoCrystal.explode);
        this.addSetting(AutoCrystal.place);
        this.addSetting(AutoCrystal.facePlace);
        this.addSetting(AutoCrystal.pause);
        this.addSetting(AutoCrystal.calculations);
        this.addSetting(AutoCrystal.hole);
        this.addSetting(AutoCrystal.renderCrystal);
        this.addSetting(AutoCrystal.logic);
        this.placeSystemTime = -1L;
        this.switchCooldown = false;
    }

    @Override
    public void onUpdate() {
        if (AutoCrystal.logicMode.getValue() == 1) {
            this.placeCrystal();
            this.breakCrystal();
        }
        else {
            this.breakCrystal();
            this.placeCrystal();
        }
    }

    public void breakCrystal() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal)AutoCrystal.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> AutoCrystal.mc.player.getDistance(c))).orElse(null);
        if (crystal != null && AutoCrystal.mc.player.getDistance((Entity)crystal) <= AutoCrystal.breakRange.getValue() && this.breakTimer.passed((long)AutoCrystal.breakDelay.getValue())) {
            if (AutoCrystal.pause.getValue() && PlayerUtil.getHealth() <= AutoCrystal.pauseHealth.getValue() && AutoCrystal.pauseMode.getValue() == 0) {
                return;
            }
            if (AutoCrystal.rotate.getValue()) {
                CrystalUtil.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)AutoCrystal.mc.player);
            }
            if (AutoCrystal.explode.getValue()) {
                for (int i = 0; i < AutoCrystal.breakAttempts.getValue(); ++i) {
                    CrystalUtil.attackCrystal(crystal, AutoCrystal.packetBreak.getValue());
                }
            }
            if (AutoCrystal.breakHand.getValue() == 0) {
                AutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (AutoCrystal.syncBreak.getValue()) {
                    crystal.setDead();
                }
                if (AutoCrystal.unload.getValue()) {
                    AutoCrystal.mc.world.removeAllEntities();
                    AutoCrystal.mc.world.getLoadedEntityList();
                }
            }
            if (AutoCrystal.breakHand.getValue() == 1 && !AutoCrystal.mc.player.getHeldItemOffhand().isEmpty()) {
                AutoCrystal.mc.player.swingArm(EnumHand.OFF_HAND);
                if (AutoCrystal.syncBreak.getValue()) {
                    crystal.setDead();
                }
                if (AutoCrystal.unload.getValue()) {
                    AutoCrystal.mc.world.removeAllEntities();
                    AutoCrystal.mc.world.getLoadedEntityList();
                }
            }
        }
        this.breakTimer.reset();
        if (!AutoCrystal.multiPlace.getValue()) {
            return;
        }
    }

    public void placeCrystal() {
        int crystalSlot = (AutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? AutoCrystal.mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (AutoCrystal.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        }
        else if (crystalSlot == -1) {
            return;
        }
        Entity entity = null;
        Entity lastTarget = null;
        BlockPos finalPos = null;
        final List<BlockPos> blocks = this.findCrystalBlocks();
        final List<Entity> entities = new ArrayList<Entity>();
        entities.addAll((Collection<? extends Entity>)AutoCrystal.mc.world.playerEntities.stream().collect(Collectors.toList()));
        double damage = 0.5;
        for (final Entity entityTarget : entities) {
            if (entityTarget != AutoCrystal.mc.player) {
                if (((EntityLivingBase)entityTarget).getHealth() <= 0.0f) {
                    continue;
                }
                if (AutoCrystal.mc.player.getDistanceSq(entityTarget) > AutoCrystal.enemyRange.getValue() * AutoCrystal.enemyRange.getValue()) {
                    continue;
                }
                for (final BlockPos blockPos : blocks) {
                    if (!CrystalUtil.canBlockBeSeen(blockPos) && AutoCrystal.mc.player.getDistanceSq(blockPos) > AutoCrystal.placeRange.getValue() * AutoCrystal.placeRange.getValue()) {
                        continue;
                    }
                    final double targetDistanceSq = entityTarget.getDistanceSq(blockPos);
                    if (targetDistanceSq > 56.2) {
                        continue;
                    }
                    double calcDamage;
                    if (AutoCrystal.placeCalc.getValue() == 1) {
                        calcDamage = CrystalUtil.calculateDamage(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), entityTarget);
                    }
                    else {
                        calcDamage = CrystalUtil.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, entityTarget);
                    }
                    int minDamagePlace;
                    if (EnemyUtil.getArmor((EntityPlayer)entityTarget, AutoCrystal.armorMelt.getValue(), AutoCrystal.armorDurability.getValue())) {
                        minDamagePlace = 2;
                    }
                    else {
                        minDamagePlace = (int)AutoCrystal.minDamage.getValue();
                    }
                    if (calcDamage < minDamagePlace && ((EntityLivingBase)entityTarget).getHealth() + ((EntityLivingBase)entityTarget).getAbsorptionAmount() > AutoCrystal.facePlaceHealth.getValue()) {
                        continue;
                    }
                    if (calcDamage <= damage) {
                        continue;
                    }
                    final double selfDamage = CrystalUtil.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)AutoCrystal.mc.player);
                    if (PlayerUtil.getHealth() - selfDamage <= AutoCrystal.pauseHealth.getValue() && AutoCrystal.pause.getValue()) {
                        continue;
                    }
                    if (selfDamage > calcDamage) {
                        continue;
                    }
                    damage = calcDamage;
                    finalPos = blockPos;
                    entity = entityTarget;
                    lastTarget = entityTarget;
                }
            }
        }
        if (damage == 0.5) {
            this.render = null;
            this.renderEnt = null;
            CrystalUtil.resetRotation();
            return;
        }
        this.render = finalPos;
        this.renderEnt = entity;
        if (!offhand && AutoCrystal.mc.player.inventory.currentItem != crystalSlot) {
            if (AutoCrystal.autoSwitch.getValue()) {
                AutoCrystal.mc.player.inventory.currentItem = crystalSlot;
            }
            CrystalUtil.resetRotation();
            this.switchCooldown = true;
            return;
        }
        if (AutoCrystal.place.getValue()) {
            if (AutoCrystal.rotate.getValue()) {
                CrystalUtil.lookAtPacket(finalPos.getX() + 0.5, finalPos.getY() - 0.5, finalPos.getZ() + 0.5, (EntityPlayer)AutoCrystal.mc.player);
            }
            final EnumFacing enumFacing = CrystalUtil.getEnumFacing(AutoCrystal.rayTrace.getValue(), this.render, finalPos);
            if (System.nanoTime() / 1000000L - this.placeSystemTime >= AutoCrystal.placeDelay.getValue()) {
                if (AutoCrystal.rayTrace.getValue() && enumFacing != null) {
                    AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(finalPos, enumFacing, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
                else {
                    AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(finalPos, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
        }
    }

    @Override
    public void onRender3D(final Render3DEvent eventRender) {
        if (AutoCrystal.renderCrystal.getValue() && this.render != null) {
            RenderUtil.prepareRender(7);
            RenderUtil.drawBoxFromBlockPos(this.render, new Color((int)AutoCrystal.r.getValue(), (int)AutoCrystal.g.getValue(), (int)AutoCrystal.b.getValue(), (int)AutoCrystal.a.getValue()), 63);
            RenderUtil.releaseRender();
            final double damage = CrystalUtil.calculateDamage(this.render.getX() + 0.5, this.render.getY() + 1, this.render.getZ() + 0.5, this.renderEnt);
            final double damageRounded = MathUtil.roundAvoid(damage, 1);
            if (AutoCrystal.renderDamage.getValue()) {
                RenderUtil.drawNametagFromBlockPos(this.render, String.valueOf(damageRounded));
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketSoundEffect && AutoCrystal.antiDeSync.getValue()) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity e : AutoCrystal.mc.world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                        e.setDead();
                    }
                }
            }
        }
    }

    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        if (AutoCrystal.blockCalc.getValue() == 1) {
            return (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
        return (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && AutoCrystal.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && AutoCrystal.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(CrystalUtil.getPlayerPos(), (float)placeRange.getValue(), (int)placeRange.getValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    static {
        AutoCrystal.explode = new Checkbox("Break", true);
        AutoCrystal.breakRange = new SubSlider(AutoCrystal.explode, "Break Range", 0.0, 5.0, 7.0, 0);
        AutoCrystal.breakDelay = new SubSlider(AutoCrystal.explode, "Break Delay", 0.0, 20.0, 60.0, 0);
        AutoCrystal.breakAttempts = new SubSlider(AutoCrystal.explode, "Break Attempts", 0.0, 1.0, 5.0, 0);
        AutoCrystal.antiDeSync = new SubCheckbox(AutoCrystal.explode, "Anti-DeSync", true);
        AutoCrystal.syncBreak = new SubCheckbox(AutoCrystal.explode, "Sync Break", true);
        AutoCrystal.unload = new SubCheckbox(AutoCrystal.explode, "Unload", true);
        AutoCrystal.rotate = new SubCheckbox(AutoCrystal.explode, "Rotate", false);
        AutoCrystal.packetBreak = new SubCheckbox(AutoCrystal.explode, "Packet Break", true);
        AutoCrystal.breakHand = new SubMode(AutoCrystal.explode, "BreakHand", new String[] { "MainHand", "OffHand" });
        AutoCrystal.pause = new Checkbox("Pause", true);
        AutoCrystal.pauseHealth = new SubSlider(AutoCrystal.pause, "Pause Health", 0.0, 7.0, 36.0, 0);
        AutoCrystal.pauseMode = new SubMode(AutoCrystal.pause, "Mode", new String[] { "Place", "Break", "Both" });
        AutoCrystal.place = new Checkbox("Place", true);
        AutoCrystal.placeRange = new SubSlider(AutoCrystal.place, "Place Range", 0.0, 5.0, 7.0, 0);
        AutoCrystal.enemyRange = new SubSlider(AutoCrystal.place, "Enemy Range", 0.0, 5.0, 7.0, 0);
        AutoCrystal.placeDelay = new SubSlider(AutoCrystal.place, "Place Delay", 0.0, 40.0, 600.0, 0);
        AutoCrystal.minDamage = new SubSlider(AutoCrystal.place, "Minimum Damage", 0.0, 7.0, 36.0, 0);
        AutoCrystal.autoSwitch = new SubCheckbox(AutoCrystal.place, "AutoSwitch", false);
        AutoCrystal.rayTrace = new SubCheckbox(AutoCrystal.place, "Ray-Trace", true);
        AutoCrystal.multiPlace = new SubCheckbox(AutoCrystal.place, "MultiPlace", false);
        AutoCrystal.facePlace = new Checkbox("Face-Place", true);
        AutoCrystal.facePlaceHealth = new SubSlider(AutoCrystal.facePlace, "Face-Place Health", 0.0, 16.0, 36.0, 0);
        AutoCrystal.armorMelt = new SubCheckbox(AutoCrystal.facePlace, "Armor Melt", false);
        AutoCrystal.armorDurability = new SubSlider(AutoCrystal.facePlace, "Armor Durability", 0.0, 15.0, 100.0, 0);
        AutoCrystal.calculations = new Checkbox("Calculations", true);
        AutoCrystal.placeCalc = new SubMode(AutoCrystal.calculations, "Place Calculation", new String[] { "Ideal", "Actual" });
        AutoCrystal.damageCalc = new SubMode(AutoCrystal.calculations, "Place Calculation", new String[] { "Full", "Semi" });
        AutoCrystal.blockCalc = new SubMode(AutoCrystal.calculations, "Block Logic", new String[] { "Normal", "1.13+" });
        AutoCrystal.logic = new Checkbox("Logic", true);
        AutoCrystal.logicMode = new SubMode(AutoCrystal.logic, "Crystal Logic", new String[] { "Break -> Place", "Place -> Break" });
        AutoCrystal.hole = new Checkbox("Hole", false);
        AutoCrystal.multiPlaceInHole = new SubCheckbox(AutoCrystal.hole, "MultiPlace in Hole", false);
        AutoCrystal.renderCrystal = new Checkbox("Render", true);
        AutoCrystal.r = new SubSlider(AutoCrystal.renderCrystal, "Red", 0.0, 250.0, 255.0, 0);
        AutoCrystal.g = new SubSlider(AutoCrystal.renderCrystal, "Green", 0.0, 0.0, 255.0, 0);
        AutoCrystal.b = new SubSlider(AutoCrystal.renderCrystal, "Blue", 0.0, 250.0, 255.0, 0);
        AutoCrystal.a = new SubSlider(AutoCrystal.renderCrystal, "Alpha", 0.0, 30.0, 255.0, 0);
        AutoCrystal.renderDamage = new SubCheckbox(AutoCrystal.renderCrystal, "Render Damage", true);
        AutoCrystal.outline = new SubCheckbox(AutoCrystal.renderCrystal, "Outline", false);
    }
}
