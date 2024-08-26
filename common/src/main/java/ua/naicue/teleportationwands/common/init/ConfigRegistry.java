package ua.naicue.teleportationwands.common.init;

import it.hurts.octostudios.octolib.modules.config.ConfigManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import ua.naicue.teleportationwands.common.TeleportationWands;
import ua.naicue.teleportationwands.common.config.Color;
import ua.naicue.teleportationwands.common.config.WandStats;
import ua.naicue.teleportationwands.common.items.TeleportationWandItem;

import java.util.Map;

public class ConfigRegistry {
    public static Color highlightColor = new Color();

    public static void registerCommon() {
        ConfigManager.registerConfig(TeleportationWands.MODID + "/highlight_color", highlightColor);

        for (Map.Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
            if (!(entry.getValue() instanceof TeleportationWandItem wand))
                continue;

            ConfigManager.registerConfig(TeleportationWands.MODID + "/items/" + entry.getKey().location().getPath(), new WandStats(wand));
        }
    }
}
