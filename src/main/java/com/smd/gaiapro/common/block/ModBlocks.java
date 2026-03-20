package com.smd.gaiapro.common.block;

import com.smd.gaiapro.gaiapro.Tags;
import com.smd.gaiapro.common.tile.TileEntityElvenBeacon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class ModBlocks {

    public static final BlockElvenBeacon ELVEN_BEACON = new BlockElvenBeacon();

    static {
        GameRegistry.registerTileEntity(TileEntityElvenBeacon.class,
                new ResourceLocation(Tags.MOD_ID, BlockElvenBeacon.NAME));
    }

    private ModBlocks() {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ELVEN_BEACON);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ELVEN_BEACON).setRegistryName(ELVEN_BEACON.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ELVEN_BEACON), 0,
                new ModelResourceLocation(ELVEN_BEACON.getRegistryName(), "inventory"));
    }
}