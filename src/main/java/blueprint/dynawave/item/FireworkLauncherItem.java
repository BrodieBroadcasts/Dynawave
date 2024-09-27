package blueprint.dynawave.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireworkLauncherItem extends Item {
    private static final int FIREWORKS_PER_SHOT = 15; // Number of fireworks to shoot at once
    private static final float SPEED = 1.5F; // Speed of the launched fireworks
    private static final float DIVERGENCE = 5.0F; // Angle adjustment for multishot effect in degrees

    public FireworkLauncherItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        // Shoot fireworks if the world is server-side
        if (!world.isClient) {
            shootFireworks(player, world, FIREWORKS_PER_SHOT); // Pass the multishot variable
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private void shootFireworks(PlayerEntity player, World world, int fireworkCount) {
        Vec3d direction = player.getRotationVec(1.0F); // Get the direction the player is looking

        for (int i = 0; i < fireworkCount; i++) {
            // Find a firework rocket in the player's inventory
            ItemStack fireworkStack = findFireworkStackInInventory(player);

            if (fireworkStack != null) {
                // Create the firework rocket entity
                FireworkRocketEntity firework = new FireworkRocketEntity(world, fireworkStack, player,
                        player.getX(), player.getEyeY() - 0.15F, player.getZ(), true);

                // Calculate angle adjustments for multishot effect
                float angleAdjustment = (i - (fireworkCount - 1) / 2f) * DIVERGENCE; // Spread fireworks evenly

                // Calculate adjusted direction based on angle adjustment
                double yawAdjustment = Math.toRadians(angleAdjustment);
                Vec3d adjustedDirection = new Vec3d(
                        direction.x * Math.cos(yawAdjustment) - direction.z * Math.sin(yawAdjustment),
                        direction.y,
                        direction.x * Math.sin(yawAdjustment) + direction.z * Math.cos(yawAdjustment)
                ).normalize();

                // Set the velocity of the firework in a straight line
                firework.setVelocity(adjustedDirection.x, adjustedDirection.y, adjustedDirection.z, SPEED, 0.0F);

                // Spawn the firework entity in the world
                world.spawnEntity(firework);

                // Play the launch sound effect
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT,
                        SoundCategory.PLAYERS, 0.01F, 0.1F);

                // Only consume the firework if not in Creative mode
                if (!player.getAbilities().creativeMode) {
                    fireworkStack.decrement(1);
                }
            }
        }
    }

    private ItemStack findFireworkStackInInventory(PlayerEntity player) {
        // Search for the first firework rocket stack in the player's inventory
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof FireworkRocketItem) {
                return stack;
            }
        }
        return null;
    }
}