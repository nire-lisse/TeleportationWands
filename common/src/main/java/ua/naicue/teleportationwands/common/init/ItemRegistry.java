package ua.naicue.teleportationwands.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import ua.naicue.teleportationwands.common.TeleportationWands;
import ua.naicue.teleportationwands.common.config.WandStats;
import ua.naicue.teleportationwands.common.items.TeleportationWandItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TeleportationWands.MODID, Registries.ITEM);

    public static final RegistrySupplier<TeleportationWandItem> COPPER_TELEPORTATION_WAND = ITEMS.register("copper_teleportation_wand",
            () -> new TeleportationWandItem(new WandStats(10,30,1)));

    public static final RegistrySupplier<TeleportationWandItem> IRON_TELEPORTATION_WAND = ITEMS.register("iron_teleportation_wand",
            () -> new TeleportationWandItem(new WandStats(15,25,2)));

    public static final RegistrySupplier<TeleportationWandItem> GOLD_TELEPORTATION_WAND = ITEMS.register("gold_teleportation_wand",
            () -> new TeleportationWandItem(new WandStats(20,20,3)));

    public static final RegistrySupplier<TeleportationWandItem> DIAMOND_TELEPORTATION_WAND = ITEMS.register("diamond_teleportation_wand",
            () -> new TeleportationWandItem(new WandStats(25,15,4)));

    public static final RegistrySupplier<TeleportationWandItem> NETHERITE_TELEPORTATION_WAND = ITEMS.register("netherite_teleportation_wand",
            () -> new TeleportationWandItem(new WandStats(30,10,5)));

    public static void registerCommon() {
        ITEMS.register();
    }
}
