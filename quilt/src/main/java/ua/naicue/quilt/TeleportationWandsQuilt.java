package ua.naicue.quilt;

import net.fabricmc.api.ModInitializer;
import ua.naicue.teleportationwands.common.TeleportationWands;

public final class TeleportationWandsQuilt implements ModInitializer {
    @Override
    public void onInitialize() {
        TeleportationWands.init();
    }
}
