package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @Inject(method = "isRaining", at = @At("TAIL"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> ci) {
        WeatherMode weatherMode = raindance$config.weatherMode;

        // Do nothing if rain is allowed
        if (weatherMode == WeatherMode.ALLOW) {
            return;
        }

        // Determine the current raining state
        boolean isRaining = ci.getReturnValue();
        boolean targetRaining = weatherMode == WeatherMode.FORCE;

        // If the current raining state is already set, cancel
        if (isRaining == targetRaining) {
            return;
        }

        PrimaryLevelDataInvoker invoker = (PrimaryLevelDataInvoker) this;

        // Set the raining state & update the return value
        invoker.setIsRaining(targetRaining);
        ci.setReturnValue(targetRaining);
    }

    @Inject(method = "isThundering", at = @At("TAIL"), cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> ci) {
        WeatherMode thunderMode = raindance$config.thunderMode;

        // Do nothing if thunder is allowed
        if (thunderMode == WeatherMode.ALLOW) {
            return;
        }

        // Determine the current thundering state
        boolean isThundering = ci.getReturnValue();
        boolean targetThundering = thunderMode == WeatherMode.FORCE;

        // If the current thundering state is already set, cancel
        if (isThundering == targetThundering) {
            return;
        }

        PrimaryLevelDataInvoker invoker = (PrimaryLevelDataInvoker) this;

        // Set the thundering state & update the return value
        invoker.setIsThundering(targetThundering);
        ci.setReturnValue(targetThundering);
    }
}
