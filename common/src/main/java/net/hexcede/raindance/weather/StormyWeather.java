package net.hexcede.raindance.weather;

import java.util.function.Supplier;

import net.hexcede.raindance.config.RaindanceConfig;

public class StormyWeather {
    private static RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    public static boolean shouldCreateLightning(Supplier<Boolean> original) {
        return WeatherConditions.applyMode(raindance$config.lightningMode, original);
    }
}
