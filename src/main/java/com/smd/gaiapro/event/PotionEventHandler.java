package com.smd.gaiapro.event;

import com.smd.gaiapro.potion.ModPotion;
import ibxm.Player;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        Entity attacker= event.getSource().getTrueSource();
        // 获取伤害来源实体
        if (attacker instanceof EntityPlayer) {
            // 检查攻击者是否拥有抗性效果
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if ((player.isPotionActive(ModPotion.Perplexity))) {

                float reducedDamage = event.getAmount() * 0.75f;
                event.setAmount(reducedDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {

        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        if(player.isPotionActive(ModPotion.NoJump)){
            player.motionY = 0;
        }
    }

    @SubscribeEvent
    public static void onInputUpdate(InputUpdateEvent event) {
        EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();

        if (player.isPotionActive(ModPotion.Forst)) {

            event.getMovementInput().moveForward = 0;
            event.getMovementInput().moveStrafe = 0;

            event.getMovementInput().sneak = false;

        }
    }
}
