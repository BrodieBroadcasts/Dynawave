package blueprint.dynawave;

import blueprint.dynawave.entity.client.ModModelLayers;
import blueprint.dynawave.entity.client.SpinningEntityRenderer;
import blueprint.dynawave.init.ModBlocks;
import blueprint.dynawave.init.ModEntities;
import blueprint.dynawave.particle.ModParticles;
import blueprint.dynawave.particle.custom.ImpactParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class DynawaveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register entity renderer
        EntityRendererRegistry.register(ModEntities.SPINNING_BAR, SpinningEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.COCONUT_PROJECTILE, FlyingItemEntityRenderer::new);


        ParticleFactoryRegistry.getInstance().register(ModParticles.IMPACT_PARTICLE, ImpactParticle.Factory::new);

        ModModelLayers.registerLayers();
    }
}
