package blueprint.dynawave.entity;

import blueprint.dynawave.init.ModEntities;
import blueprint.dynawave.init.ModItems;
import blueprint.dynawave.particle.ModParticles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CoconutProjectile extends ThrownItemEntity {
    public CoconutProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CoconutProjectile(LivingEntity livingEntity, World world) {
        super(ModEntities.COCONUT_PROJECTILE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.COCONUT;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.5F);
            World world = this.getWorld();

            double x = entityHitResult.getEntity().getX();
            double y = entityHitResult.getEntity().getEyeY();
            double z = entityHitResult.getEntity().getZ();

            ((ServerWorld) world).spawnParticles(ModParticles.IMPACT_PARTICLE, x, y, z, 10, 0.1, 0.1, 0.1, 0.01);
        }
    }
}
