package com.smd.gaiapro.client.render.entity.gaiapro;

import java.util.Map;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.smd.gaiapro.GaiaPro;
import com.smd.gaiapro.proxy.ClientProxy;
import net.minecraft.client.resources.DefaultPlayerSkin;

import com.smd.gaiapro.common.entity.gaia.EntityGaiaPro;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGaiaPro extends RenderBiped<EntityGaiaPro> {

    private static final ResourceLocation GAIA_TEXTURES = new ResourceLocation("extrabotany:textures/entity/gaia3.png");

    public RenderGaiaPro(RenderManager renderManager) {
        super(renderManager, new ModelPlayer(0.0F, false), 0F);
    }

    @Override
    public void doRender(@Nonnull EntityGaiaPro dopple, double par2, double par4, double par6, float par8, float par9) {
        super.doRender(dopple, par2, par4, par6, par8, par9);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityGaiaPro entity) {
        ResourceLocation resourceLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
        if (new GameProfile(null, entity.getCustomNameTag()) != null) {
            GaiaPro.proxy.preloadSkin(new GameProfile(null, entity.getCustomNameTag()));
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.getMinecraft().getSkinManager()
                    .loadSkinFromCache(new GameProfile(null, entity.getCustomNameTag()));
            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                resourceLocation = Minecraft.getMinecraft().getSkinManager()
                        .loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            }
        }
        if (resourceLocation != null)
            return resourceLocation;
        if (ClientProxy.halloween && ConfigHandler.ENABLE_FEATURES)
            return new ResourceLocation(LibResource.GAIAIII_PUMPKIN);
        return GAIA_TEXTURES;
    }
}
