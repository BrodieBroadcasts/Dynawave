package blueprint.dynawave.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
        if(this.getYaw() > 360) {
            this.setYaw(0); // Reset to avoid overflow
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        // Calculate the push direction based on entity's rotation
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
