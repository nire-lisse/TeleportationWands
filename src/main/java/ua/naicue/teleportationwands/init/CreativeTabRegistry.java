package ua.naicue.teleportationwands.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.naicue.teleportationwands.TeleportationWands;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TeleportationWands.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TELEPORTATION_WAND_TAB = CREATIVE_TABS.register("teleportationwands", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.teleportationwands"))
            .icon(() -> ItemRegistry.NETHERITE_TELEPORTATION_WAND.get().getDefaultInstance())
            .build());

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);

        bus.addListener(CreativeTabRegistry::fillCreativeTabs);
    }

    private static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TELEPORTATION_WAND_TAB.get()) {
            event.accept(ItemRegistry.COPPER_TELEPORTATION_WAND.get());
            event.accept(ItemRegistry.IRON_TELEPORTATION_WAND.get());
            event.accept(ItemRegistry.GOLD_TELEPORTATION_WAND.get());
            event.accept(ItemRegistry.DIAMOND_TELEPORTATION_WAND.get());
            event.accept(ItemRegistry.NETHERITE_TELEPORTATION_WAND.get());
        }
    }
}
