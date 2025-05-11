package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @ModifyReturnValue(method = "isRaining", at = @At("RETURN"))
    private boolean isRaining(boolean isRaining) {
        WeatherMode weatherMode = raindance$config.weatherMode;

        switch (weatherMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return true;
            case WeatherMode.DISALLOW:
                return false;
        }

        return isRaining;
    }

    @ModifyReturnValue(method = "isThundering", at = @At("RETURN"))
    private boolean isThundering(boolean isThundering) {
        WeatherMode weatherMode = raindance$config.thunderMode;

        switch (weatherMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return true;
            case WeatherMode.DISALLOW:
                return false;
        }

        return isThundering;
    }
}
