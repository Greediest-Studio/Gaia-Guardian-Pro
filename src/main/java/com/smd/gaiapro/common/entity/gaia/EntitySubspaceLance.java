package com.smd.gaiapro.common.entity.gaia;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.entity.IBossProjectile;
import com.smd.gaiapro.common.entity.EntityThrowableCopy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 重力井——保留原亚空之矛的物理运动（下落/插地），但移除所有攻击特效，
 * 改为持续吸引周围玩家至自身位置。
 */
public class EntitySubspaceLance extends EntityThrowableCopy implements IBossProjectile {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_LIFE = "life";
    private static final String TAG_PITCH = "pitch";

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntitySubspaceLance.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Integer> LIFE = EntityDataManager.createKey(EntitySubspaceLance.class,
            DataSerializers.VARINT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntitySubspaceLance.class,
            DataSerializers.FLOAT);

    // 保留原版状态枚举（插地/空中）
    private Status status;
    private Status preStatus;

    public EntitySubspaceLance(World worldIn) {
        super(worldIn);
    }

    public EntitySubspaceLance(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        setSize(0F, 0F);
        dataManager.register(ROTATION, 0F);
        dataManager.register(LIFE, 0);
        dataManager.register(PITCH, 0F);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.15F; // 保留原版重力，正常下落
    }

    @Override
    public void onUpdate() {
        // ========== 保留原版物理运动逻辑（下落/插地） ==========
        this.preStatus = this.status;
        if (this.status != Status.STANDBY) {
            this.setPositionAndUpdate(this.posX, this.posY - 0.15F, this.posZ);
            this.status = getStatus();
        } else {
            // 插地后完全静止
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        }

        super.onUpdate(); // 执行投掷物基础刻更新（位置、速度等）

        if (ticksExisted > getLife()) {
            setDead();
        }

        EntityLivingBase thrower = getThrower();
        if (!world.isRemote && (thrower == null || thrower.isDead)) {
            setDead();
            return;
        }

        if (world.isRemote) {
            return;
        }

        double range = 4.0;        // 作用半径
        double strength = 2.5;     // 固定拉力系数（原Vortex配置值，可自行调整）

        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class,
                new AxisAlignedBB(posX - range, posY - range, posZ - range,
                        posX + range, posY + range, posZ + range));

        for (EntityPlayer player : players) {
            // 忽略创造/旁观者以及投射者本人
            if (player.isCreative() || player.isSpectator()) continue;
            if (player == thrower) continue;

            Vec3d toCenter = new Vec3d(posX - player.posX, posY - player.posY, posZ - player.posZ);
            double distance = toCenter.length();
            if (distance < 0.1) continue;

            Vec3d pull = toCenter.normalize().scale(strength);
            player.motionX += pull.x;
            player.motionY += pull.y;
            player.motionZ += pull.z;
            // ▲▲▲ 替换原 factor 计算与乘法 ▲▲▲

            // 限制最大速度（原代码保留，不动）
            double maxSpeed = 1.2;
            double speedSq = player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ;
            if (speedSq > maxSpeed * maxSpeed) {
                double scale = maxSpeed / Math.sqrt(speedSq);
                player.motionX *= scale;
                player.motionY *= scale;
                player.motionZ *= scale;
            }

            player.velocityChanged = true; // 强制同步客户端（Vortex同样做法）
        }
    }

    /**
     * 判断当前状态：下方是空气则为空中，否则插地
     */
    private Status getStatus() {
        if (this.world.getBlockState(this.getPosition().add(0, -1.9F, 0)).getBlock() != Blocks.AIR)
            return Status.STANDBY;
        return Status.INAIR;
    }

    @Override
    protected void onImpact(RayTraceResult pos) {
        // 重力井没有撞击伤害，保留空实现即可
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
        super.writeEntityToNBT(cmp);
        cmp.setFloat(TAG_ROTATION, getRotation());
        cmp.setInteger(TAG_LIFE, getLife());
        cmp.setFloat(TAG_PITCH, getPitch());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
        super.readEntityFromNBT(cmp);
        setRotation(cmp.getFloat(TAG_ROTATION));
        setLife(cmp.getInteger(TAG_LIFE));
        setPitch(cmp.getFloat(TAG_PITCH));
    }

    // ========== 数据参数访问器 ==========
    public float getRotation() { return dataManager.get(ROTATION); }
    public void setRotation(float rot) { dataManager.set(ROTATION, rot); }

    public float getPitch() { return dataManager.get(PITCH); }
    public void setPitch(float rot) { dataManager.set(PITCH, rot); }

    public int getLife() { return dataManager.get(LIFE); }
    public void setLife(int delay) { dataManager.set(LIFE, delay); }

    // ========== 状态枚举 ==========
    public static enum Status {
        INAIR, STANDBY;
    }

    @Override
    public boolean isBoss(Entity p) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }
}