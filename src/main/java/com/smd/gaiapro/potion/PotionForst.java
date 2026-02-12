package com.smd.gaiapro.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionForst extends PotionCore{

    public PotionForst() {
        super("forst",true, 0x550000);
        setPotionName("effect.forst");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.world.isRemote) return; // 客户端不在此处处理运动

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            // 强制清零水平运动向量
            player.motionX = 0;
            player.motionZ = 0;
            // 清除移动输入指令（服务端也有该字段，直接置零可避免网络包残留）
            player.moveForward = 0;
            player.moveStrafing = 0;
            // 标记速度已改变，同步给客户端（视觉上更平滑）
            player.velocityChanged = true;
        }
    }
}
