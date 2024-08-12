package ua.naicue.teleportationwands;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static class WandStats {
        public final ModConfigSpec.DoubleValue maxDistance;
        public final ModConfigSpec.IntValue cooldown;
        public final ModConfigSpec.IntValue safeChecks;

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

    public static class Color {
        public final ModConfigSpec.DoubleValue red;
        public final ModConfigSpec.DoubleValue green;
        public final ModConfigSpec.DoubleValue blue;

        Color(ModConfigSpec.Builder builder) {
            builder.push("Color");
            red = builder
                    .defineInRange("red", 255D, 0D, 255D);
            green = builder
                    .defineInRange("green", 215D, 0D, 255D);
            blue = builder
                    .defineInRange("blue", 0D, 0D, 255D);
            builder.pop();
        }
    }

    public static Color color;

    public static WandStats copper;
    public static WandStats iron;
    public static WandStats gold;
    public static WandStats diamond;
    public static WandStats netherite;

    private Config(ModConfigSpec.Builder builder) {
        builder.push("General");

        color = new Color(builder);

        builder.pop();

        copper = new WandStats(builder, "copper", 10, 35, 1);
        iron = new WandStats(builder, "iron", 15, 30, 2);
        gold = new WandStats(builder, "gold", 20, 25, 3);
        diamond = new WandStats(builder, "diamond", 25, 20, 4);
        netherite = new WandStats(builder, "netherite", 30, 10, 5);
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


