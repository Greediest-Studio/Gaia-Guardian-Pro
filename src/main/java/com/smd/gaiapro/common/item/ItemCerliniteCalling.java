package com.smd.gaiapro.common.item;

import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCerliniteCalling extends Item {

    public static final String NAME = "cerlinite_calling";

    public ItemCerliniteCalling() {
        setRegistryName(Tags.MOD_ID, NAME);
        setTranslationKey(Tags.MOD_ID + "." + NAME);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(16);
    }
}