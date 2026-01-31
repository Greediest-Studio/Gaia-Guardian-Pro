package com.smd.gaiapro.proxy;

import com.smd.gaiapro.client.render.entity.gaiapro.RenderDomain;
import com.smd.gaiapro.client.render.entity.gaiapro.RenderSwordDomain;
import com.smd.gaiapro.common.entity.EntityDomain;
import com.smd.gaiapro.common.entity.EntityMinion;
import com.smd.gaiapro.client.render.entity.gaiapro.RenderGaiaPro;
import com.smd.gaiapro.client.render.entity.gaiapro.RenderMinion;
import com.smd.gaiapro.common.entity.EntityGaiaPro;
import com.smd.gaiapro.common.entity.EntitySwordDomain;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityMinion.class, RenderMinion::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDomain.class, RenderDomain::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySwordDomain.class, RenderSwordDomain::new);


    }
}
