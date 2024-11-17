package net.hexcede.raindance.forge;

import dev.architectury.platform.forge.EventBuses;
import net.hexcede.raindance.Raindance;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Raindance.MOD_ID)
public class RaindanceForge {
    public RaindanceForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Raindance.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Raindance.init();
    }
}