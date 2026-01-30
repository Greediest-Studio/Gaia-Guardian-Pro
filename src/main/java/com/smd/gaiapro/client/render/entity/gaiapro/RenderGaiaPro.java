package com.smd.gaiapro.client.render.entity.gaiapro;

import java.util.Map;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import com.smd.gaiapro.common.entity.EntityGaiaPro;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
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
        String customName = entity.getCustomNameTag();

        if (customName != null && !customName.isEmpty() && !customName.trim().isEmpty()) {
            try {
                ExtraBotany.proxy.preloadSkin(new GameProfile(null, customName));
                Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.getMinecraft().getSkinManager()
                        .loadSkinFromCache(new GameProfile(null, customName));

                if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                    ResourceLocation skinLocation = Minecraft.getMinecraft().getSkinManager()
                            .loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                    if (skinLocation != null) {
                        return skinLocation;
                    }
                }
            } catch (Exception e) {
                // 捕获异常，防止皮肤加载失败导致渲染崩溃
                ExtraBotany.logger.warn("Failed to load custom skin for GaiaPro: " + customName, e);
            }
        }

        if (ClientProxy.halloween && ConfigHandler.ENABLE_FEATURES) {
            return new ResourceLocation(LibResource.GAIAIII_PUMPKIN);
        }
        return GAIA_TEXTURES;
    }
}
