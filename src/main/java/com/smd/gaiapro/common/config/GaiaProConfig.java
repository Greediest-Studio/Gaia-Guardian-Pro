package com.smd.gaiapro.common.config;

import com.smd.gaiapro.gaiapro.Tags;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GaiaProConfig {

    private static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    private static final String CATEGORY_GAIA_GUARDIAN_IV = "gaia_guardian_iv";
    private static final String KEY_EXTRA_DROPS = "perPlayerExtraDrops";
    private static final String[] DEFAULT_EXTRA_DROPS = new String[0];

    private static Configuration configuration;
    private static List<ConfiguredDrop> perPlayerExtraDrops = Collections.emptyList();

    private GaiaProConfig() {
    }

    public static void init(File configDirectory) {
        configuration = new Configuration(new File(configDirectory, Tags.MOD_ID + ".cfg"));
        sync();
    }

    public static void sync() {
        if (configuration == null) {
            return;
        }

        configuration.load();

        String[] configuredEntries = configuration.getStringList(
                KEY_EXTRA_DROPS,
                CATEGORY_GAIA_GUARDIAN_IV,
                DEFAULT_EXTRA_DROPS,
            "Gaia Guardian IV dies and grants these extra drops to every attacking player. Format: modid:item@metadata,count"
        );

        perPlayerExtraDrops = parseDrops(configuredEntries);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void spawnConfiguredDropsForPlayer(World world, EntityPlayer player) {
        if (world == null || player == null || world.isRemote || perPlayerExtraDrops.isEmpty()) {
            return;
        }

        for (ConfiguredDrop drop : perPlayerExtraDrops) {
            int remaining = drop.count;

            while (remaining > 0) {
                int stackSize = Math.min(remaining, drop.item.getItemStackLimit());
                ItemStack stack = new ItemStack(drop.item, stackSize, drop.metadata);
                EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ, stack);
                entityItem.setNoPickupDelay();
                world.spawnEntity(entityItem);
                remaining -= stackSize;
            }
        }
    }

    private static List<ConfiguredDrop> parseDrops(String[] configuredEntries) {
        if (configuredEntries == null || configuredEntries.length == 0) {
            return Collections.emptyList();
        }

        List<ConfiguredDrop> drops = new ArrayList<>();
        for (String configuredEntry : configuredEntries) {
            if (configuredEntry == null) {
                continue;
            }

            String entry = configuredEntry.trim();
            if (entry.isEmpty()) {
                continue;
            }

            ConfiguredDrop configuredDrop = parseDrop(entry);
            if (configuredDrop != null) {
                drops.add(configuredDrop);
            }
        }

        return Collections.unmodifiableList(drops);
    }

    private static ConfiguredDrop parseDrop(String entry) {
        String[] splitByCount = entry.split(",", 2);
        if (splitByCount.length != 2) {
            LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': expected modid:item@metadata,count", entry);
            return null;
        }

        String itemDescriptor = splitByCount[0].trim();
        String countString = splitByCount[1].trim();

        int count;
        try {
            count = Integer.parseInt(countString);
        } catch (NumberFormatException exception) {
            LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': invalid count '{}'.", entry, countString);
            return null;
        }

        if (count <= 0) {
            LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': count must be greater than 0.", entry);
            return null;
        }

        int metadata = 0;
        String itemId = itemDescriptor;
        int metadataIndex = itemDescriptor.lastIndexOf('@');
        if (metadataIndex >= 0) {
            itemId = itemDescriptor.substring(0, metadataIndex).trim();
            String metadataString = itemDescriptor.substring(metadataIndex + 1).trim();

            try {
                metadata = Integer.parseInt(metadataString);
            } catch (NumberFormatException exception) {
                LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': invalid metadata '{}'.", entry, metadataString);
                return null;
            }

            if (metadata < 0) {
                LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': metadata must be 0 or greater.", entry);
                return null;
            }
        }

        Item item = Item.getByNameOrId(itemId);
        if (item == null) {
            LOGGER.warn("Ignoring invalid Gaia Guardian IV drop entry '{}': item '{}' was not found.", entry, itemId);
            return null;
        }

        return new ConfiguredDrop(item, metadata, count);
    }

    private static final class ConfiguredDrop {
        private final Item item;
        private final int metadata;
        private final int count;

        private ConfiguredDrop(Item item, int metadata, int count) {
            this.item = item;
            this.metadata = metadata;
            this.count = count;
        }
    }
}