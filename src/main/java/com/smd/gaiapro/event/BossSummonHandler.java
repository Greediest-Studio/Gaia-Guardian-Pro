package com.smd.gaiapro.event;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.smd.gaiapro.common.item.ModItems;
import com.smd.gaiapro.common.tile.TileEntityElvenBeacon;
import com.smd.gaiapro.common.entity.gaia.EntityGaiaPro;
import com.smd.gaiapro.potion.ModPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
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

        if (stack.getItem() == ModItems.CERLINITE_CALLING && player.isSneaking()) {
            if (ConfigHandler.GAIA_ENABLE) {
                if (EntityGaiaPro.spawn(player, stack, world, pos, false)) {

                    stack.shrink(1);
                    player.addPotionEffect(new PotionEffect(ModPotion.GaiaSpawn, 200, 0));

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

        if ((world.getTileEntity(pos) instanceof TileEntityElvenBeacon) && player.isPotionActive(ModPotion.GaiaSpawn)){
            event.setCanceled(true);
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof EntityGaiaPro) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack stack = player.getHeldItem(event.getHand());

            if (stack != null && !stack.isEmpty()) {
                event.setCanceled(true);
                if (!event.getWorld().isRemote) {
                    player.sendMessage(new TextComponentTranslation("message.gaiapro"));
                }
            }
        }
    }
}
