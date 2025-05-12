package net.hexcede.raindance.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.hexcede.raindance.weather.StormyWeather;
import net.hexcede.raindance.weather.WeatherConditions;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @WrapOperation(
        method="tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V",
        at=@At(
            value = "INVOKE",
            target = "net/minecraft/server/level/ServerLevel.isThundering()Z"
        )
    )
    public boolean tickChunk_isThundering(ServerLevel level, Operation<Boolean> original)
    {
        return StormyWeather.shouldCreateLightning(() -> original.call(level));
    }

    @WrapOperation(
        method="tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V",
        at=@At(
            value = "INVOKE",
            target = "net/minecraft/server/level/ServerLevel.isRainingAt(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    public boolean tickChunk_isRainingAt(ServerLevel level, BlockPos pos, Operation<Boolean> original)
    {
        Supplier<Boolean> result = () -> original.call(level, pos);

        // If the lightning mode is forced, we need to call the game's original logic but with the precipitation spoofed as rain
        // This way, conditions like sky access are still handled properly by the game
        if (raindance$config.lightningMode == WeatherMode.FORCE) {
            return WeatherConditions.withRainSpoofed(result);
        }

        return StormyWeather.shouldCreateLightning(result);
    }
}
