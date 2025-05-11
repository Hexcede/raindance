package net.hexcede.raindance.mixin;

import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    RaindanceConfig raindance$config = RaindanceConfig.HANDLER.instance();

    @WrapMethod(method = "hasPrecipitation")
    private boolean hasPrecipitation(Operation<Boolean> original) {
        boolean hasPrecipitation = original.call();

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

    @WrapMethod(method = "getPrecipitationAt")
    private Biome.Precipitation getPrecipitationAt(BlockPos pos, Operation<Biome.Precipitation> original) {
        Biome.Precipitation precipitation = original.call(pos);
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
