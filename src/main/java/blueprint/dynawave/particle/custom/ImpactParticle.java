package blueprint.dynawave.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ImpactParticle extends SpriteBillboardParticle {
    protected ImpactParticle(ClientWorld level, double xCord, double yCord, double zCord,
                             SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.velocityMultiplier = 0.6f;

        this.x = xCord;
        this.y = yCord;
        this.z = zCord;

        this.velocityX = xd;
        this.velocityY = yd;
        this.velocityZ = zd;

        this.scale *= 0.75f;
        this.maxAge = 20;
        this.setSpriteForAge(spriteSet);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new ImpactParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
