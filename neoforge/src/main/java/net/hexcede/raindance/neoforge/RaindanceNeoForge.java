package net.hexcede.raindance.neoforge;

import java.util.function.BiFunction;

import net.hexcede.raindance.Raindance;
import net.hexcede.raindance.config.RaindanceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@Mod(Raindance.MOD_ID)
public class RaindanceNeoForge {
    public record ConfigScreen(BiFunction<Minecraft, Screen, Screen> screenFunction) implements IExtensionPoint<ConfigScreen> {}

    public RaindanceNeoForge() {
        Raindance.init();

        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> RaindanceConfig.HANDLER.instance()
                    .buildGui()
                    .generateScreen(parent))
        );
    }
}