package net.hexcede.raindance.fabric;

import net.hexcede.raindance.Raindance;
import net.fabricmc.api.ModInitializer;

public class RaindanceFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Raindance.init();
    }
}