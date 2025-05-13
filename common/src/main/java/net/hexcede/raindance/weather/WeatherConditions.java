package net.hexcede.raindance.weather;

import java.util.function.Supplier;

import net.hexcede.raindance.config.WeatherMode;

public class WeatherConditions {
    private static int spoofGlobalRainCounter = 0;

    public static boolean shouldSpoofGlobalRain() {
        return spoofGlobalRainCounter > 0;
    }

    public static <T> T withRainSpoofed(Supplier<T> callback) {
        spoofGlobalRainCounter += 1;
        T result = callback.get();
        spoofGlobalRainCounter -= 1;

        return result;
    }

    public static void withRainSpoofed(Runnable callback) {
        withRainSpoofed(() -> {
            callback.run();
            return null;
        });
    }

    public static boolean applyMode(WeatherMode mode, Supplier<Boolean> result) {
        switch (mode) {
            case ALLOW:
                break;
            case FORCE:
                return true;
            case DISALLOW:
                return false;
        }

        return result.get();
    }

    public static boolean applyModeInverse(WeatherMode mode, Supplier<Boolean> result) {
        return !applyMode(mode, () -> !result.get());
    }
}
