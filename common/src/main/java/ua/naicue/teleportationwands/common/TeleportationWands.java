package ua.naicue.teleportationwands.common;

import dev.architectury.event.events.common.LifecycleEvent;
import ua.naicue.teleportationwands.common.init.ConfigRegistry;
import ua.naicue.teleportationwands.common.init.CreativeTabRegistry;
import ua.naicue.teleportationwands.common.init.ItemRegistry;

public class TeleportationWands {
    public static final String MODID = "teleportationwands";

    public static void init() {
        ItemRegistry.registerCommon();
        CreativeTabRegistry.registerCommon();

        LifecycleEvent.SETUP.register(ConfigRegistry::registerCommon);
    }
}
