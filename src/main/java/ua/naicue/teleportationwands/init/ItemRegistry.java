package ua.naicue.teleportationwands.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.naicue.teleportationwands.TeleportationWands;
import ua.naicue.teleportationwands.items.TeleportationWand;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, TeleportationWands.MODID);

    public static final DeferredHolder<Item, TeleportationWand> COPPER_TELEPORTATION_WAND = ITEMS.register("copper_teleportation_wand",
            () -> new TeleportationWand(5, 35, 1));

    public static final DeferredHolder<Item, TeleportationWand> IRON_TELEPORTATION_WAND = ITEMS.register("iron_teleportation_wand",
            () -> new TeleportationWand(10, 30, 2));

    public static final DeferredHolder<Item, TeleportationWand> GOLD_TELEPORTATION_WAND = ITEMS.register("gold_teleportation_wand",
            () -> new TeleportationWand(15, 25, 3));

    public static final DeferredHolder<Item, TeleportationWand> DIAMOND_TELEPORTATION_WAND = ITEMS.register("diamond_teleportation_wand",
            () -> new TeleportationWand(20, 20, 4));

    public static final DeferredHolder<Item, TeleportationWand> NETHERITE_TELEPORTATION_WAND = ITEMS.register("netherite_teleportation_wand",
            () -> new TeleportationWand(25, 10, 5));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
