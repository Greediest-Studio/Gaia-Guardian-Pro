package com.smd.gaiapro.common.entity;

import com.smd.gaiapro.GaiaPro;
import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void init() {
        int id = 0;
        EntityRegistry.registerModEntity(makeName("gaiapro"), EntityGaiaPro.class, Tags.MOD_ID + ":gaiapro", id++,
                GaiaPro.instance, 64, 10, true);
        EntityRegistry.registerModEntity(makeName("minion"), EntityMinion.class, Tags.MOD_ID + ":minion",
                id++, GaiaPro.instance, 64, 10, true);
        EntityRegistry.registerModEntity(makeName("sworddomain"), EntitySwordDomain.class, Tags.MOD_ID + ":sworddomain",
                id++, GaiaPro.instance, 64, 10, true);
        EntityRegistry.registerModEntity(makeName("domain"), EntityDomain.class, Tags.MOD_ID + ":domain", id++,
                GaiaPro.instance, 64, 10, true);
    }

    private static ResourceLocation makeName(String s) {
        return new ResourceLocation(Tags.MOD_ID, s);
    }
}
