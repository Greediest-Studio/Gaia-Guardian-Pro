package com.smd.gaiapro.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModPotion {

    public static final Potion Perplexity = new PotionPerplexity();
    public static final Potion GaiaSpawn = new PotionGaiaSpawn();
    public static final Potion NoJump = new PotionNoJump();
    public static final Potion Forst = new PotionForst();

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(Perplexity);
        event.getRegistry().register(GaiaSpawn);
        event.getRegistry().register(NoJump);
        event.getRegistry().register(Forst);
    }


}
