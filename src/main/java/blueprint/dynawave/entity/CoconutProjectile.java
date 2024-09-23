package blueprint.dynawave.entity;

import blueprint.dynawave.init.ModEntities;
import blueprint.dynawave.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
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
        if(!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.5F);
        }

        this.discard();
        super.onEntityHit(entityHitResult);
    }
}
