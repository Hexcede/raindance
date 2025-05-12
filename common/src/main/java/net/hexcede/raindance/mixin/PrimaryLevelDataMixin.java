package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.weather.WeatherConditions;
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
        return WeatherConditions.applyMode(raindance$config.weatherMode, original::call);
    }

    @WrapMethod(method = "isThundering")
    private boolean isThundering(Operation<Boolean> original) {
        return WeatherConditions.applyMode(raindance$config.thunderMode, original::call);
    }
}
