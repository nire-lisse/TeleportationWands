package ua.naicue.teleportationwands.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import ua.naicue.teleportationwands.common.TeleportationWands;

@Mod(TeleportationWands.MODID)
public class TeleportationWandsNeoForge {
    public TeleportationWandsNeoForge(IEventBus modBus) {
        TeleportationWands.init();
    }
}
