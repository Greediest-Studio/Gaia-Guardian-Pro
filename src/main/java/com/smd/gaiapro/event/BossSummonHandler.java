package com.smd.gaiapro.event;

import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.smd.gaiapro.common.entity.EntityGaiaPro;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
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
    public static void GaiaSpawn(PlayerInteractEvent.RightClickBlock event) {

        if (event.getWorld().isRemote) {
            return;
        }

        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        EnumHand hand = event.getHand();
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItem() == Items.BOOK && player.isSneaking()) {
            if (ConfigHandler.GAIA_ENABLE) {
                if (EntityGaiaPro.spawn(player, stack, world, pos, false)) {

                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                        player.addPotionEffect(new PotionEffect(ModPotions.witchcurse, 200, 0));
                    }

                    event.setCanceled(true);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }

    @SubscribeEvent
    public static void BeaconClosed(PlayerInteractEvent.RightClickBlock event) {

        if (event.getWorld().isRemote) {
            return;
        }

        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();

        if ((world.getTileEntity(pos) instanceof TileEntityBeacon) && player.isPotionActive(ModPotions.witchcurse)){
            event.setCanceled(true);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
