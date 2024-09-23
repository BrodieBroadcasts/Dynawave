package blueprint.dynawave;

import blueprint.dynawave.command.KillBarCommand;
import blueprint.dynawave.command.SpinningBarCommand;
import blueprint.dynawave.init.ModBlocks;
import blueprint.dynawave.init.ModEntities;
import blueprint.dynawave.init.ModItems;
import blueprint.dynawave.particle.ModParticles;
import blueprint.dynawave.teams.TeamManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dynawave implements ModInitializer {
	public static final String MOD_ID = "dynawave";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItems.initialize();
		ModBlocks.initialize();
		ModEntities.initialize();

		SpinningBarCommand.register();
		KillBarCommand.register();

		ModParticles.registerParticles();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			TeamManager.registerCommands(dispatcher);
		});

	}
}