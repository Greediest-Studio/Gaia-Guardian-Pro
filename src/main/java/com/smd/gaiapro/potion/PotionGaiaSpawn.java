package com.smd.gaiapro.potion;

import net.minecraft.entity.SharedMonsterAttributes;

public class PotionGaiaSpawn extends PotionCore {

    public PotionGaiaSpawn() {
        super("gaiaspawn",true, 0x550000);
        setPotionName("effect.gaiaspawn");
        registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR,
                "a1b2c3d4-1234-5678-90ab-cdef12345678", -0.5, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS,
                "b2c3d4e5-2345-6789-01ab-cdef23456789", -0.5, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED,
                "c3d4e5f6-3456-7890-12ab-cdef34567890", -0.5, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED,
                "31d13310-5ed6-4627-889a-8594e7f95d93", -0.3, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH,
                "a9577a6d-c0ac-4c4d-a76c-380d42b0cc76", -0.25, 2);
    }
}
