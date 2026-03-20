package com.smd.gaiapro.common.sound;

import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class ModSoundEvents {

    private static final ResourceLocation GAIA_BOSS_MUSIC_ID = new ResourceLocation(Tags.MOD_ID, "gaia_boss_music");

    public static final SoundEvent GAIA_BOSS_MUSIC = new SoundEvent(GAIA_BOSS_MUSIC_ID)
            .setRegistryName(GAIA_BOSS_MUSIC_ID);

    private ModSoundEvents() {
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(GAIA_BOSS_MUSIC);
    }
}