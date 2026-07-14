package com.smd.gaiapro.event;

import com.smd.gaiapro.potion.ModPotion;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientPotionEventHandler {

    @SubscribeEvent
    public static void onInputUpdate(InputUpdateEvent event) {
        EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();

        if (player.isPotionActive(ModPotion.Forst)) {

            event.getMovementInput().moveForward = 0;
            event.getMovementInput().moveStrafe = 0;

            event.getMovementInput().sneak = false;

        }
    }

    @SubscribeEvent
    public static void onControl(InputUpdateEvent event) {
        EntityPlayerSP player = (EntityPlayerSP) event.getEntityPlayer();

        if (player.isPotionActive(ModPotion.Control)) {
            MovementInput input = event.getMovementInput();

            input.moveForward = -input.moveForward;
            input.moveStrafe = -input.moveStrafe;

        }
    }
}