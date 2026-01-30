package com.smd.gaiapro.proxy;

import com.smd.gaiapro.client.render.entity.gaiapro.RenderGaiaPro;
import com.smd.gaiapro.common.entity.EntityGaiaPro;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

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

    }
}
