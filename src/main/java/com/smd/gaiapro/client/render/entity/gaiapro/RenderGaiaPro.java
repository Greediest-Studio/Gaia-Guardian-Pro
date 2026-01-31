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
import vazkii.botania.api.internal.ShaderCallback;
import vazkii.botania.client.core.helper.ShaderHelper;
import org.lwjgl.opengl.ARBShaderObjects;

@SideOnly(Side.CLIENT)
public class RenderGaiaPro extends RenderBiped<EntityGaiaPro> {

    private static final ResourceLocation GAIA_TEXTURES = new ResourceLocation("extrabotany:textures/entity/gaia3.png");

    // 特效强度参数
    private float grainIntensity = 0.05F;
    private float disfiguration = 0.025F;
    private static final float DEFAULT_GRAIN_INTENSITY = 0.05F;
    private static final float DEFAULT_DISFIGURATION = 0.025F;

    // Botania着色器回调
    private final ShaderCallback shaderCallback = shader -> {
        // 片段着色器Uniforms
        int disfigurationUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "disfiguration");
        ARBShaderObjects.glUniform1fARB(disfigurationUniform, disfiguration);

        // 顶点着色器Uniforms
        int grainIntensityUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "grainIntensity");
        ARBShaderObjects.glUniform1fARB(grainIntensityUniform, grainIntensity);
    };

    public RenderGaiaPro(RenderManager renderManager) {
        super(renderManager, new ModelPlayer(0.0F, false), 0F);
    }

    @Override
    public void doRender(@Nonnull EntityGaiaPro gaia, double x, double y, double z, float entityYaw, float partialTicks) {

        int invulTime = gaia.getInvulTime();
        int hurtTime = gaia.hurtTime;

        boolean shouldUseShader = (invulTime > 0) || (hurtTime > 0);

        if (shouldUseShader) {
            // 计算特效参数
            updateShaderParameters(gaia, invulTime, hurtTime);

            // 绑定Botania的分身着色器
            // 改为 != 0 而不是 != null，因为 doppleganger 是 int 类型
            if (ShaderHelper.doppleganger != 0) {
                ShaderHelper.useShader(ShaderHelper.doppleganger, shaderCallback);
            }
        }

        // 执行渲染
        super.doRender(gaia, x, y, z, entityYaw, partialTicks);

        // 如果使用了着色器，释放它
        // 同样改为 != 0
        if (shouldUseShader && ShaderHelper.doppleganger != 0) {
            ShaderHelper.releaseShader();
        }
    }

    /**
     * 根据实体状态更新着色器参数
     */
    private void updateShaderParameters(EntityGaiaPro gaia, int invulTime, int hurtTime) {
        if (invulTime > 0) {
            // 无敌状态：颗粒强度随时间变化，畸变强度与颗粒强度成比例
            grainIntensity = invulTime > 20 ? 1.0F : invulTime * 0.05F;
            disfiguration = grainIntensity * 0.3F;
        } else if (hurtTime > 0) {
            // 受伤状态：使用Botania原版的算法
            disfiguration = (DEFAULT_DISFIGURATION + hurtTime * ((1.0F - 0.15F) / 20.0F)) / 2.0F;
            grainIntensity = DEFAULT_GRAIN_INTENSITY + hurtTime * ((1.0F - 0.15F) / 10.0F);
        } else {
            // 正常状态：使用默认值
            grainIntensity = DEFAULT_GRAIN_INTENSITY;
            disfiguration = DEFAULT_DISFIGURATION;
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityGaiaPro entity) {
        ResourceLocation resourceLocation = DefaultPlayerSkin.getDefaultSkinLegacy();
        String customName = entity.getCustomNameTag();

        // 尝试获取玩家皮肤
        if (customName != null && !customName.isEmpty()) {
            GaiaPro.proxy.preloadSkin(new GameProfile(null, customName));
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.getMinecraft().getSkinManager()
                    .loadSkinFromCache(new GameProfile(null, customName));
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