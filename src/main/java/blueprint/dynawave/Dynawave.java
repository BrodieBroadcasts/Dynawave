package blueprint.dynawave;

import blueprint.dynawave.init.ModBlocks;
import blueprint.dynawave.init.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dynawave implements ModInitializer {
	public static final String MOD_ID = "dynawave";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Wipeout inspired mod initialization

		LOGGER.info("Hello Fabric world!");

		ModItems.initialize();
		ModBlocks.initialize();
	}
}