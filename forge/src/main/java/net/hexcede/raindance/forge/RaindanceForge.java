package net.hexcede.raindance.forge;

import net.hexcede.raindance.Raindance;
import net.hexcede.raindance.config.RaindanceConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(Raindance.MOD_ID)
public class RaindanceForge {
    public RaindanceForge() {
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> RaindanceConfig.HANDLER.instance()
                .buildGui()
                .generateScreen(parent))
        );
        Raindance.init();
    }
}