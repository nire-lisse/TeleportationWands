package ua.naicue.teleportationwands.common.config;

import it.hurts.octostudios.octolib.modules.config.annotations.IgnoreProp;
import it.hurts.octostudios.octolib.modules.config.annotations.Prop;
import it.hurts.octostudios.octolib.modules.config.impl.OctoConfig;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.naicue.teleportationwands.common.items.TeleportationWandItem;

@Data
@NoArgsConstructor
public class WandStats implements OctoConfig {
    @Prop(comment = "Maximum teleportation distance")
    private double maxDistance;
    @Prop(comment = "Wand cooldown")
    private int cooldown;
    @Prop(comment = "The maximum number of blocks that you are teleporting to above the block you are looking at")
    private int safeChecks;

    @IgnoreProp
    private TeleportationWandItem wand;

    public WandStats(double maxDistance, int cooldown, int safeChecks) {
        this.maxDistance = maxDistance;
        this.cooldown = cooldown;
        this.safeChecks = safeChecks;
    }

    public WandStats(TeleportationWandItem wand) {
        this(wand.getWandStats().getMaxDistance(), wand.getWandStats().getCooldown(), wand.getWandStats().getSafeChecks());

        this.wand = wand;
    }

    public void onLoadObject(Object object) {
        wand.setWandStats((WandStats) object);
    }
}
