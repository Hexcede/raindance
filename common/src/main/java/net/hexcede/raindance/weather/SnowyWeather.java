package net.hexcede.raindance.weather;

import java.util.function.Supplier;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;

public class SnowyWeather {
    private static RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    public static boolean shouldFreezeBlocks(Supplier<Boolean> original) {
        return WeatherConditions.applyMode(raindance$config.iceGenerationMode, () -> {
            // If snow mode is forced, blocks should be frozen
            if (raindance$config.snowMode == WeatherMode.FORCE) {
                return true;
            }

            return original.get();
        });
    }
}
