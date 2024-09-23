package blueprint.dynawave.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class WaterGunItem extends Item {
    private final int maxDistance = 20; // Maximum shooting distance

    public WaterGunItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            // Get the direction the player is facing
            Vec3d startPos = user.getPos().add(0, user.getEyeHeight(user.getPose()), 0);
            Vec3d direction = user.getRotationVec(1.0F);

            for (int i = 0; i < maxDistance; i++) {
                // Get the position along the direction
                Vec3d particlePos = startPos.add(direction.x * i, direction.y * i, direction.z * i);

                // Spawn water particle at this position
                ((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, particlePos.x, particlePos.y, particlePos.z, 1, 0.1, 0.1, 0.1, 0.0);

                // Check for entities in a small area around the particle position
                Box particleBox = new Box(particlePos, particlePos.add(0.5, 0.5, 0.5));
                List<Entity> entities = world.getOtherEntities(user, particleBox);

                for (Entity entity : entities) {
                    // Apply knockback to the entity
                    Vec3d knockback = direction.multiply(0.5); // Adjust knockback strength
                    entity.addVelocity(knockback.x, knockback.y, knockback.z);
                    entity.velocityModified = true; // Flag velocity change
                }
            }
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}