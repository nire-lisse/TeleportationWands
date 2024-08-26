package ua.naicue.teleportationwands.common.config;

import it.hurts.octostudios.octolib.modules.config.annotations.Prop;
import it.hurts.octostudios.octolib.modules.config.impl.OctoConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Color implements OctoConfig {
    @Prop
    private double red = 215;
    @Prop
    private double green = 100;
    @Prop
    private double blue = 10;

    public Color() {}
}
