package blueprint.dynawave.particle;

import blueprint.dynawave.Dynawave;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType IMPACT_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Dynawave.MOD_ID, "impact_particle"),
                IMPACT_PARTICLE);
    }
}
