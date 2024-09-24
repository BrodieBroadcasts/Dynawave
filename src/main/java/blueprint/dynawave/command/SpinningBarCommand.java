package blueprint.dynawave.command;

import blueprint.dynawave.entity.SpinningBarEntity;
import blueprint.dynawave.init.ModEntities;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;

public class SpinningBarCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("spawnbar")
                        .then(CommandManager.argument("rotationSpeed", FloatArgumentType.floatArg())
                                .then(CommandManager.argument("size", IntegerArgumentType.integer(1, 3)) // Integer for size (1, 2, or 3)
                                        .executes(context -> {
                                            // Retrieve the parameters
                                            float rotationSpeed = FloatArgumentType.getFloat(context, "rotationSpeed");
                                            int size = IntegerArgumentType.getInteger(context, "size");

                                            // Get the server world and player
                                            ServerWorld world = context.getSource().getWorld();
                                            PlayerEntity player = context.getSource().getPlayer();

                                            // Create and spawn the appropriate sized entity
                                            SpinningBarEntity entity = null;
                                            switch (size) {
                                                case 1:
                                                    entity = ModEntities.SMALL_SPINNING_BAR.create(world);
                                                    break;
                                                case 2:
                                                    entity = ModEntities.MEDIUM_SPINNING_BAR.create(world);
                                                    break;
                                                case 3:
                                                    entity = ModEntities.LARGE_SPINNING_BAR.create(world);
                                                    break;
                                            }

                                            if (entity != null) {
                                                entity.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0, 0);
                                                entity.setRotationSpeed(rotationSpeed); // Set rotation speed
                                                world.spawnEntity(entity);
                                            }
                                            return 1; // Success
                                        }))))
        );
    }
}
