package com.smd.gaiapro.potion;

import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public abstract class PotionCore extends Potion {

    public ResourceLocation icon;

    public PotionCore(String PotionName, boolean bad, int color) {
        super(bad, color);
        setRegistryName(PotionName);
        this.icon = new ResourceLocation(Tags.MOD_ID, "textures/potion/"+PotionName+".png");
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public boolean canAmplify() {
        return true;
    }

    public void affectEntity(Entity thrownPotion, Entity thrower, EntityLivingBase entity, int amplifier, double potency) {
        this.performEffect(entity, amplifier);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int posX, int posY, PotionEffect effect, Minecraft mc) {

        mc.getTextureManager().bindTexture(icon);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        int x = posX + 6;
        int y = posY + 7;
        int width = 18;
        int height = 18;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
        buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        buffer.pos(x + width, y,0).tex(1, 0).endVertex();
        buffer.pos(x, y,0).tex(0, 0).endVertex();
        tessellator.draw();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }
}
