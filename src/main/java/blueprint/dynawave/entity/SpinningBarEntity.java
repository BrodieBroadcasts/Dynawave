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
    private float sizeMultiplier; // Multiplier for the size of the entity

    public SpinningBarEntity(EntityType<? extends SpinningBarEntity> type, World world) {
        super(type, world);
        this.rotationSpeed = 10.0f; // Default rotation speed
        this.sizeMultiplier = 1.0f; // Default size multiplier
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

        // The length of the bar adjusted by the size multiplier (e.g., sizeMultiplier can be 1, 2, or 3)
        double barLength = 24.0 * this.sizeMultiplier; // Change this based on your model’s visual length

        // Define start and end points of the ray (center of the bar to its end)
        Vec3d start = this.getPos(); // Starting from the entity's position
        Vec3d end = start.add(Math.cos(radians) * barLength, 0, Math.sin(radians) * barLength);

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
        // Calculate the player's bounding box
        Box playerBox = player.getBoundingBox();

        // Check if the ray intersects with the player's bounding box
        Optional<Vec3d> hitResult = playerBox.raycast(start, end);

        return hitResult.isPresent(); // Return true if there was a hit
    }

    private void applyHitEffect(PlayerEntity player) {
        // Calculate the push direction based on the entity's current rotation
        double radians = Math.toRadians(this.getYaw());
        Vec3d pushDirection = new Vec3d(Math.cos(radians), 0.5, Math.sin(radians));

        // Apply push force to the player, scaling with the entity's sizeMultiplier
        double pushStrength = this.sizeMultiplier * 0.5; // Adjust push strength relative to size
        player.addVelocity(pushDirection.x * pushStrength, 0, pushDirection.z * pushStrength);
        player.velocityModified = true; // Ensure velocity is applied
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
        // No data trackers needed for this example
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