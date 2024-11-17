package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @Inject(method = "hasPrecipitation", at = @At("TAIL"), cancellable = true)
    private void hasPrecipitation(CallbackInfoReturnable<Boolean> ci) {
        WeatherMode biomeMode = raindance$config.biomeMode;

        // If biome control is set to be allowed, don't modify the precipitation state
        if (biomeMode == WeatherMode.ALLOW) {
            return;
        }

        // Check for the precipitation state in this biome, and determine what the desired precipitation state is
        boolean hasPrecipitation = ci.getReturnValue();
        boolean targetPrecipitation = biomeMode == WeatherMode.FORCE;

        // Do nothing if the biome already has the desired precipitation
        if (hasPrecipitation == targetPrecipitation) {
            return;
        }

        // Update the return value depending on the target precipitation state
        ci.setReturnValue(targetPrecipitation);
    }

    @Inject(method = "getPrecipitationAt", at = @At("TAIL"), cancellable = true)
    private void getPrecipitationAt(CallbackInfoReturnable<Biome.Precipitation> ci) {
        WeatherMode snowMode = raindance$config.snowMode;

        // If snow is set to be allowed, don't modify the precipitation kind
        if (snowMode == WeatherMode.ALLOW) {
            return;
        }

        // Get the real precipitation kind
        Biome.Precipitation precipitation = ci.getReturnValue();

        // Check if the current precipitation is snow, and determine what the desired snow state is
        boolean hasSnow = precipitation == Biome.Precipitation.SNOW;
        boolean targetSnow = snowMode == WeatherMode.FORCE;

        // Do nothing if the target snow state is already the current snow state
        if (hasSnow == targetSnow) {
            return;
        }

        // Update the return value depending on the target snow value
        ci.setReturnValue(targetSnow ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN);
    }
}
