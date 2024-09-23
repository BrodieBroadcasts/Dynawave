package blueprint.dynawave.block;

import blueprint.dynawave.init.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GeyserBlock extends SlabBlock {
    public GeyserBlock(Settings settings) {
        super(settings);
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);

        if (entity instanceof PlayerEntity && !world.isClient) {
            PlayerEntity player = (PlayerEntity) entity;
            emitGeyserParticles((ServerWorld) world, pos);
            liftPlayer(player);
        }
    }

    private void emitGeyserParticles(ServerWorld world, BlockPos pos) {
        for (int i = 0; i < 10; i++) {
            world.spawnParticles(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.1);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        for (int i = 0; i < 200; i++) {
            double offsetX = random.nextDouble() * 0.6D - 0.3D;
            double offsetY = random.nextDouble() * 0.5D;
            double offsetZ = random.nextDouble() * 0.6D - 0.3D;

            double motionX = (random.nextDouble() - 0.5) * 0.1;
            double motionY = random.nextDouble() * 2;
            double motionZ = (random.nextDouble() - 0.5) * 0.1;

            world.addParticle(ParticleTypes.SPLASH,
                    pos.getX() + 0.5D + offsetX,
                    pos.getY() + 1.0D + offsetY,
                    pos.getZ() + 0.5D + offsetZ,
                    motionX, motionY, motionZ);

        }
        for (int i = 0; i < 3; i++) {
            double offsetX = random.nextDouble() * 0.6D - 0.3D;
            double offsetY = random.nextDouble() * 0.5D;
            double offsetZ = random.nextDouble() * 0.6D - 0.3D;

            double motionX = (random.nextDouble() - 0.5) * 0.1;
            double motionY = random.nextDouble() * 0.5;
            double motionZ = (random.nextDouble() - 0.5) * 0.1;
            world.addParticle(ParticleTypes.CLOUD,
                    pos.getX() + 0.5D + offsetX,
                    pos.getY() + 1.0D + offsetY,
                    pos.getZ() + 0.5D + offsetZ,
                    motionX, motionY, motionZ);
        }
    }

    private void liftPlayer(PlayerEntity player) {
        player.addVelocity(0, 1.5, 0); // Lift the player up slightly
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 80, 0, false, false)); // Slow fall for 4 seconds
        player.velocityModified = true;
    }

}
