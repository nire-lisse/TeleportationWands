package ua.naicue.teleportationwands;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import ua.naicue.teleportationwands.init.CreativeTabRegistry;
import ua.naicue.teleportationwands.init.ItemRegistry;

@Mod(TeleportationWands.MODID)
public class TeleportationWands {
    public static final String MODID = "teleportationwands";

    public TeleportationWands(IEventBus bus, ModContainer container) {
        ItemRegistry.register(bus);
        CreativeTabRegistry.register(bus);
    }
}
