package com.smd.gaiapro;

import com.smd.gaiapro.gaiapro.Tags;
import com.smd.gaiapro.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class GaiaPro {

    @SidedProxy(serverSide = "com.smd.gaiapro.proxy.CommonProxy",
                clientSide = "com.smd.gaiapro.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Tags.MOD_ID)
    public static GaiaPro instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
