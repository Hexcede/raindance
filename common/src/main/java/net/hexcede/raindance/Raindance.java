package net.hexcede.raindance;

import net.hexcede.raindance.config.RaindanceConfig;

public class Raindance
{
	public static final String MOD_ID = "raindance";

	public static void init() {
		RaindanceConfig.HANDLER.load();
	}
}
