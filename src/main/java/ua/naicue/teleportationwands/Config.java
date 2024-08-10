package ua.naicue.teleportationwands;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static class WandStats {
        public ModConfigSpec.DoubleValue maxDistance;
        public ModConfigSpec.IntValue cooldown;
        public ModConfigSpec.IntValue safeChecks;

        WandStats(ModConfigSpec.Builder builder, String name, double defaultMaxDistance, int defaultCooldown, int defaultSafeChecks) {
            builder.push(name + " wand");
            maxDistance = builder
                    .comment("Maximum teleportation distance")
                    .defineInRange("maxDistance", defaultMaxDistance, 1, Double.MAX_VALUE);
            cooldown = builder
                    .comment("Wand cooldown")
                    .defineInRange("cooldown", defaultCooldown, 1, Integer.MAX_VALUE);
            safeChecks = builder
                    .comment("The maximum number of blocks that you are teleporting to above the block you are looking at")
                    .defineInRange("safeChecks", defaultSafeChecks, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static WandStats copper;
    public static WandStats iron;
    public static WandStats gold;
    public static WandStats diamond;
    public static WandStats netherite;

    private Config(ModConfigSpec.Builder builder) {
        copper = new WandStats(builder, "copper", 5, 35, 1);
        iron = new WandStats(builder, "iron", 10, 30, 2);
        gold = new WandStats(builder, "gold", 15, 25, 3);
        diamond = new WandStats(builder, "diamond", 20, 20, 4);
        netherite = new WandStats(builder, "netherite", 25, 10, 5);
    }

    public static final ModConfigSpec spec;
    private static final Config config;

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(Config::new);

        spec = pair.getRight();
        config = pair.getLeft();
    }
}


