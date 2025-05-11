package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @ModifyReturnValue(method = "hasPrecipitation", at = @At("RETURN"))
    private boolean hasPrecipitation(boolean hasPrecipitation) {
        WeatherMode biomeMode = raindance$config.biomeMode;

        switch (biomeMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return true;
            case WeatherMode.DISALLOW:
                return false;
        }

        return hasPrecipitation;
    }

    @ModifyReturnValue(method = "getPrecipitationAt", at = @At("RETURN"))
    private Biome.Precipitation getPrecipitationAt(Biome.Precipitation precipitation) {
        WeatherMode snowMode = raindance$config.snowMode;

        switch (snowMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return Biome.Precipitation.SNOW;
            case WeatherMode.DISALLOW:
                return Biome.Precipitation.RAIN;
        }

        return precipitation;
    }
}
