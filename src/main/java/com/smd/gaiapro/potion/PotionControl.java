package com.smd.gaiapro.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionControl extends PotionCore {

    public PotionControl() {
        super("control",true, 0x550000);
        setPotionName("effect.control");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {

    }
}
