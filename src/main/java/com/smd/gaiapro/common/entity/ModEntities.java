package com.smd.gaiapro.common.entity;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.Reference;
import com.smd.gaiapro.GaiaPro;
import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void init() {
        int id = 0;
        EntityRegistry.registerModEntity(makeName("gaiapro"), EntityGaiaPro.class, Tags.MOD_ID + ":gaiapro", id++,
                GaiaPro.instance, 64, 10, true);
    }

    private static ResourceLocation makeName(String s) {
        return new ResourceLocation(Tags.MOD_ID, s);
    }
}
