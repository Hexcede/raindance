package net.hexcede.raindance.mixin;

import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class PrimaryLevelDataMixin {
    @Inject(method = "isRaining", at = @At("TAIL"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> ci) {
        if (ci.getReturnValue()) {
            return;
        }

        PrimaryLevelDataInvoker invoker = (PrimaryLevelDataInvoker) this;

        invoker.setIsRaining(true);
        ci.setReturnValue(true);
    }
}
