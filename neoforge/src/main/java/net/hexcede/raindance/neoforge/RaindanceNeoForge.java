package net.hexcede.raindance.neoforge;

import net.hexcede.raindance.Raindance;
import net.hexcede.raindance.config.RaindanceConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Raindance.MOD_ID)
public class RaindanceNeoForge {
    public RaindanceNeoForge() {
        Raindance.init();

        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class,
            () -> (client, parent) -> RaindanceConfig.HANDLER.instance()
                .buildGui()
                .generateScreen(parent)
        );
    }
}