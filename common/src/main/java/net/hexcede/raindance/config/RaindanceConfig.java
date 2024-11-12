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
                    .name(Component.literal("Weather Control"))
                    .description(OptionDescription.of(Component.literal("All settings related to weather control.")))
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Weather Mode"))
                        .description(OptionDescription.of(Component.literal("This setting controls how Raindance will apply weather settings. Force (default) will force weather to be always active. Disallow will forcefully prevent weather. Allow will fall back to the default Minecraft behaviour.")))
                        .binding(WeatherMode.FORCE, () -> this.weatherMode, newVal -> this.weatherMode = newVal)
                        .controller(opt -> EnumControllerBuilder.create(opt)
                            .enumClass(WeatherMode.class))
                        .build())
                    .option(Option.<WeatherMode>createBuilder()
                        .name(Component.literal("Thunder Mode"))
                        .description(OptionDescription.of(Component.literal("This setting controls how Raindance will apply thunder settings. Force will force weather to always be thunder. Disallow will forcefully prevent thunder. Allow (default) will fall back to the default Minecraft behaviour.")))
                        .binding(WeatherMode.ALLOW, () -> this.thunderMode, newVal -> this.thunderMode = newVal)
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
}