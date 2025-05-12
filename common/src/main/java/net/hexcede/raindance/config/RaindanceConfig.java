package net.hexcede.raindance.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.network.chat.Component;

public class RaindanceConfig {
    public static ConfigClassHandler<RaindanceConfig> HANDLER = ConfigClassHandler.createBuilder(RaindanceConfig.class)
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(YACLPlatform.getConfigDir().resolve("raindance.json5"))
            .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
            .setJson5(true)
            .build())
        .build();

    public YetAnotherConfigLib buildGui() {
        return YetAnotherConfigLib.createBuilder()
            .title(Component.literal("Raindance Configuration"))
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Weather"))
                .tooltip(Component.literal("Raindance weather settings"))
                .group(OptionGroup.createBuilder()
                    .name(Component.literal("Weather Conditions"))
                    .description(OptionDescription.of(Component.literal("All settings related to weather conditions.")))
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Weather Override"))
                        .description(OptionDescription.of(Component.literal("This setting overrides the state of weather.")))
                        .binding(WeatherMode.FORCE, () -> this.weatherMode, newVal -> this.weatherMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Thunderstorm Override"))
                        .description(OptionDescription.of(Component.literal("This setting overrides the state of thunderstorms.")))
                        .binding(WeatherMode.ALLOW, () -> this.thunderMode, newVal -> this.thunderMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Biome Precipitation"))
                        .description(OptionDescription.of(Component.literal("This setting overrides biome specific precipitation (e.g. in deserts).")))
                        .binding(WeatherMode.FORCE, () -> this.biomeMode, newVal -> this.biomeMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Component.literal("Snowy Weather"))
                    .description(OptionDescription.of(Component.literal("All settings related to cold weather.")))
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Snow Override"))
                        .description(OptionDescription.of(Component.literal("This setting overrides whether or not precipitation is snow.")))
                        .binding(WeatherMode.ALLOW, () -> this.snowMode, newVal -> this.snowMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Snow Layer Override"))
                        .description(OptionDescription.of(Component.literal("This setting overrides whether or not weather creates snow layers.")))
                        .binding(WeatherMode.ALLOW, () -> this.snowLayersMode, newVal -> this.snowLayersMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Ice Generation Override"))
                        .description(OptionDescription.of(Component.literal("This setting overrides whether or not water will freeze into ice.")))
                        .binding(WeatherMode.ALLOW, () -> this.iceGenerationMode, newVal -> this.iceGenerationMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .build())
                .build())
            .save(() -> HANDLER.save())
            .build();
    }

    @SerialEntry
    public WeatherMode weatherMode = WeatherMode.FORCE;

    @SerialEntry
    public WeatherMode thunderMode = WeatherMode.ALLOW;

    @SerialEntry
    public WeatherMode biomeMode = WeatherMode.FORCE;

    @SerialEntry
    public WeatherMode snowMode = WeatherMode.ALLOW;

    @SerialEntry
    public WeatherMode snowLayersMode = WeatherMode.ALLOW;

    @SerialEntry
    public WeatherMode iceGenerationMode = WeatherMode.ALLOW;
}