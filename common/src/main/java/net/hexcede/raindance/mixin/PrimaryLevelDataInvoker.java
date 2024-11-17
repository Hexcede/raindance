package net.hexcede.raindance.mixin;

import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PrimaryLevelData.class)
public interface PrimaryLevelDataInvoker {
    @Invoker(value = "setRaining")
    void setIsRaining(boolean raining);

    @Invoker(value = "setThundering")
    void setIsThundering(boolean thundering);
}
