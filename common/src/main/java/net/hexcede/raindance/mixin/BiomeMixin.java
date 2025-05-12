package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.hexcede.raindance.weather.SnowyWeather;
import net.hexcede.raindance.weather.WeatherConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @WrapMethod(method = "hasPrecipitation")
    private boolean hasPrecipitation(Operation<Boolean> original) {
        return WeatherConditions.applyMode(raindance$config.biomeMode, original::call);
    }

    @WrapMethod(method = "getPrecipitationAt")
    private Biome.Precipitation getPrecipitationAt(BlockPos pos, Operation<Biome.Precipitation> original) {
        // Short-circuit to allow global rain to be spoofed in certain contexts (e.g. when forcing lightning)
        if (WeatherConditions.shouldSpoofGlobalRain()) {
            return Biome.Precipitation.RAIN;
        }

        switch (raindance$config.snowMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return Biome.Precipitation.SNOW;
            case WeatherMode.DISALLOW:
                return Biome.Precipitation.RAIN;
        }

        return original.call(pos);
    }

    @WrapOperation(
        method="shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
        at=@At(
            value = "INVOKE",
            target = "net/minecraft/world/level/biome/Biome.warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    public boolean shouldSnow_warmEnoughToRain(Biome biome, BlockPos pos, Operation<Boolean> original)
    {
        Supplier<Boolean> coldEnoughToSnow = () -> !original.call(biome, pos);

        return !SnowyWeather.shouldCreateSnowLayers(coldEnoughToSnow);
    }

    @WrapOperation(
        method="shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
        at=@At(
            value = "INVOKE",
            target = "net/minecraft/world/level/biome/Biome.warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    public boolean shouldFreeze_warmEnoughToRain(Biome biome, BlockPos pos, Operation<Boolean> original)
    {
        Supplier<Boolean> coldEnoughToSnow = () -> !original.call(biome, pos);

        return !SnowyWeather.shouldFreezeBlocks(coldEnoughToSnow);
    }
}
