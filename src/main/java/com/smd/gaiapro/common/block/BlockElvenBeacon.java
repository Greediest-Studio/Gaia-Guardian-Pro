package com.smd.gaiapro.common.block;

import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import com.smd.gaiapro.common.tile.TileEntityElvenBeacon;

public class BlockElvenBeacon extends BlockBeacon {

    public static final String NAME = "elven_beacon";

    public BlockElvenBeacon() {
        setRegistryName(Tags.MOD_ID, NAME);
        setTranslationKey(Tags.MOD_ID + "." + NAME);
        setHardness(3.0F);
        setResistance(15.0F);
        setLightLevel(1.0F);
        setSoundType(SoundType.GLASS);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityElvenBeacon();
    }
}