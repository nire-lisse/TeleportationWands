package ua.naicue.teleportationwands.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import ua.naicue.teleportationwands.common.TeleportationWands;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(TeleportationWands.MODID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> TELEPORTATION_WAND_TAB = CREATIVE_TABS.register("teleportationwands",
            () -> dev.architectury.registry.CreativeTabRegistry.create(
                    Component.translatable("itemGroup.teleportationwands"),
                    () -> ItemRegistry.NETHERITE_TELEPORTATION_WAND.get().getDefaultInstance()
                    ));

    public static void registerCommon() {
        CREATIVE_TABS.register();
    }
}
