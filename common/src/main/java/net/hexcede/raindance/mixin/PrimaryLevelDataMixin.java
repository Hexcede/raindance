package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @WrapMethod(method = "isRaining")
    private boolean isRaining(Operation<Boolean> original) {
        boolean isRaining = original.call();
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

    @WrapMethod(method = "isThundering")
    private boolean isThundering(Operation<Boolean> original) {
        boolean isThundering = original.call();
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
