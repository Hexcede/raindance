package net.hexcede.raindance.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.hexcede.raindance.config.RaindanceConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> RaindanceConfig.HANDLER.instance()
                .buildGui()
                .generateScreen(parentScreen);
    }
}