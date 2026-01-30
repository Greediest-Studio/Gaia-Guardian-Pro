package com.smd.gaiapro.event;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.smd.gaiapro.common.entity.EntityGaiaPro;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BossSummonHandler {

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        EnumHand hand = event.getHand();

        if (player.getHeldItem(hand).getItem() == Items.BOOK && player.isSneaking()) {

            if (ConfigHandler.GAIA_ENABLE) {
                if (EntityGaiaPro.spawn(player, player.getHeldItem(hand), world, pos, false)) {
                    event.setResult(Event.Result.ALLOW);
                    event.setCanceled(true);
                }
            }
        }
    }
}
