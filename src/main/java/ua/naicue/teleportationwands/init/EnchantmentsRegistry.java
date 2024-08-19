package ua.naicue.teleportationwands.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import ua.naicue.teleportationwands.TeleportationWands;

public class EnchantmentsRegistry {
    public static final ResourceKey<Enchantment> MAX_DISTANCE = key("max_distance");
    public static final ResourceKey<Enchantment> COOLDOWN = key("cooldown");

    private static ResourceKey<Enchantment> key(String key) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(TeleportationWands.MODID, key));
    }
}
