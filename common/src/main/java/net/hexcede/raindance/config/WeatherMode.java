package net.hexcede.raindance.config;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;

public enum WeatherMode implements NameableEnum {
    ALLOW,
    DISALLOW,
    FORCE;

    @Override
    public Component getDisplayName() {
        return Component.translatable("raindance.weathermode." + name().toLowerCase());
    }
}
