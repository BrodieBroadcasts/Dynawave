package blueprint.dynawave.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class SpinningBarEntity extends Entity {
    private float rotationSpeed; // Speed of rotation (degrees per tick)
    private float sizeMultiplier; // Multiplier for the size of the entity (1 = smallest, 2 = medium, 3 = largest)

    // Length of the bars based on the model size (adjust as per actual model size in blocks)
    private static final double BASE_LENGTH = 5.0; // Length in blocks of the smallest bar
    private static final double WIDTH_SCALING = 0.5; // Width multiplier based on entity size (adjust to match model)

    public SpinningBarEntity(EntityType<? extends SpinningBarEntity> type, World world) {
        super(type, world);
        this.rotationSpeed = 10.0f; // Default rotation speed
        this.sizeMultiplier = 1.0f; // Default size multiplier (1 = smallest size)
    }

    @Override
    public void tick() {
        super.tick();

        // Rotate the entity around its Y-axis based on rotation speed
        this.setYaw(this.getYaw() + this.rotationSpeed);
        if (this.getYaw() > 360) {
            this.setYaw(0); // Reset to avoid overflow
        }

        // Perform raycasting to detect collisions
        performRaycast();
    }

    private void performRaycast() {
        // Calculate the position and direction of the spinning bar based on its rotation
        double radians = Math.toRadians(this.getYaw());

        // Define the bar's half-length based on the size multiplier
        double halfLength;
        if (sizeMultiplier == 1.0f) {
            halfLength = 5.0; // Smallest model: 2 blocks each side, plus 1 in the middle
        } else if (sizeMultiplier == 2.0f) {
            halfLength = 7.0; // Medium model: 3 blocks each side, plus 1 in the middle
        } else {
            halfLength = 11.0; // Largest model: 5 blocks each side, plus 1 in the middle
        }

        // Calculate start and end points of the ray from the sides of the bar
        Vec3d center = this.getPos(); // Center of the entity
        Vec3d start = center.add(-Math.cos(radians) * halfLength, 0, -Math.sin(radians) * halfLength); // Start point
        Vec3d end = center.add(Math.cos(radians) * halfLength, 0, Math.sin(radians) * halfLength); // End point

        // Create a bounding box representing the ray's path
        Box rayBox = new Box(start, end).expand(0.5); // Expand if needed for width

        // Check for players intersecting the ray's path
        List<PlayerEntity> players = this.getWorld().getEntitiesByClass(PlayerEntity.class, rayBox, player -> player != null && player.isAlive());

        // Handle collisions
        for (PlayerEntity player : players) {
            if (rayIntersectsPlayer(start, end, player)) {
                applyHitEffect(player); // Apply hit effects to the player
            }
        }
    }

    private boolean rayIntersectsPlayer(Vec3d start, Vec3d end, PlayerEntity player) {
        // Get player's bounding box with slight expansion for precision
        Box playerBox = player.getBoundingBox().expand(0.1); // Adjust if needed

        // Check if the ray intersects with the player's bounding box
        Optional<Vec3d> hitResult = playerBox.raycast(start, end);

        // Return true if the ray intersects with the player's bounding box
        return hitResult.isPresent();
    }

    private void applyHitEffect(PlayerEntity player) {
        // Calculate the direction of the push based on current rotation of the entity
        double radians = Math.toRadians(this.getYaw());
        Vec3d pushDirection = new Vec3d(Math.cos(radians), 0.5, Math.sin(radians));

        // Apply push force proportional to the entity size
        double pushStrength = this.sizeMultiplier * 0.5; // Scale force by entity size
        player.addVelocity(pushDirection.x * pushStrength, 0, pushDirection.z * pushStrength);
        player.velocityModified = true; // Apply velocity to player
    }

    public void setRotationSpeed(float speed) {
        this.rotationSpeed = speed; // Set rotation speed
    }

    public float getRotationSpeed() {
        return this.rotationSpeed;
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier; // Set entity size multiplier
    }

    public float getSizeMultiplier() {
        return this.sizeMultiplier;
    }

    @Override
    protected void initDataTracker() {
        // Initialize data trackers if needed
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.rotationSpeed = nbt.getFloat("RotationSpeed");
        this.sizeMultiplier = nbt.getFloat("SizeMultiplier");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("RotationSpeed", this.rotationSpeed);
        nbt.putFloat("SizeMultiplier", this.sizeMultiplier);
    }
}