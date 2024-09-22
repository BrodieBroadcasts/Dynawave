package blueprint.dynawave.command;

import blueprint.dynawave.entity.SpinningBarEntity;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class KillBarCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("killbar")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            ServerWorld world = context.getSource().getWorld();

                            // Find and remove all SpinningEntity instances
                            List<SpinningBarEntity> entitiesToRemove = world.getEntitiesByClass(SpinningBarEntity.class, new Box(world.getBottomY(), world.getBottomY(), world.getBottomY(), world.getTopY(), world.getTopY(), world.getTopY()), entity -> true);

                            for (SpinningBarEntity entity : entitiesToRemove) {
                                entity.kill(); // This will mark the entity for removal
                            }

                            // Optional: send a message to the player
                            return entitiesToRemove.size(); // Return the number of entities killed
                        })));
    }
}
