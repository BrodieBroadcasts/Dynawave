package blueprint.dynawave.command;

import blueprint.dynawave.entity.SpinningBarEntity;
import blueprint.dynawave.init.ModEntities;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;

public class SpinningBarCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("spawnbar")
                        .then(CommandManager.argument("rotationSpeed", FloatArgumentType.floatArg())
                                .then(CommandManager.argument("size", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            // Retrieve the parameters
                                            float rotationSpeed = FloatArgumentType.getFloat(context, "rotationSpeed");
                                            float size = FloatArgumentType.getFloat(context, "size");

                                            // Get the server world and player
                                            ServerWorld world = context.getSource().getWorld();
                                            PlayerEntity player = context.getSource().getPlayer();

                                            // Create and spawn the entity
                                            SpinningBarEntity entity = ModEntities.SPINNING_BAR.create(world);
                                            if (entity != null) {
                                                entity.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0, 0);
                                                entity.setRotationSpeed(rotationSpeed); // Set rotation speed
                                                entity.setSizeMultiplier(size); // Set size multiplier
                                                world.spawnEntity(entity);
                                            }
                                            return 1; // Success
                                        }))))
        );
    }
}
