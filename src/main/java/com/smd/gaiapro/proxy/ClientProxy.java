package com.smd.gaiapro.proxy;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.smd.gaiapro.client.render.entity.gaiapro.*;
import com.smd.gaiapro.common.entity.gaia.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Set;

public class ClientProxy extends CommonProxy{

    public static boolean halloween = false;
    private final Set<GameProfile> skinRequested = Sets.newHashSet();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        initRenderers();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    private void initRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(EntityGaiaPro.class, RenderGaiaPro::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMinion.class, RenderMinion::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDomain.class, RenderDomain::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySwordDomain.class, RenderSwordDomain::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLandmine.class, RenderLandmine::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySubspaceLance.class, RenderSubspaceLance::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, RenderMissile::new);

    }

    @Override
    public void preloadSkin(GameProfile customSkin) {
        if (!skinRequested.contains(customSkin)) {
            Minecraft.getMinecraft().getSkinManager().loadProfileTextures(customSkin, (typeIn, location, profileTexture) -> {}, true);
            skinRequested.add(customSkin);
        }
    }
}
