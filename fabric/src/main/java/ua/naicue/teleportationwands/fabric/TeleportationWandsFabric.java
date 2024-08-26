package ua.naicue.teleportationwands.fabric;

import net.fabricmc.api.ModInitializer;
import ua.naicue.teleportationwands.common.TeleportationWands;

public class TeleportationWandsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TeleportationWands.init();
    }
}
