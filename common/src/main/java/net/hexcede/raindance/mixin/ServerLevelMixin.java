package net.hexcede.raindance.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.hexcede.raindance.config.RaindanceConfig;
import net.hexcede.raindance.config.WeatherMode;

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
        WeatherMode lightningMode = raindance$config.lightningMode;

        switch (lightningMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                return true;
            case WeatherMode.DISALLOW:
                return false;
        }

        return original.call(level);
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
        WeatherMode lightningMode = raindance$config.lightningMode;

        switch (lightningMode) {
            case WeatherMode.ALLOW:
                break;
            case WeatherMode.FORCE:
                // Re-implementation of the vanilla isRainingAt logic with biome precipitation code removed
                // TODO: Conditionally wrap Biome.getPrecipitationAt call here instead
                if (!level.isRaining()) {
                    return false;
                } else if (!level.canSeeSky(pos)) {
                    return false;
                } else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
                    return false;
                } else {
                    return true;
                }
            case WeatherMode.DISALLOW:
                return false;
        }

        return original.call(level, pos);
    }
}
