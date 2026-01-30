package com.smd.gaiapro.proxy;

import com.smd.gaiapro.common.entity.ModEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        ModEntities.init();
    }

}
