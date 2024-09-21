package blueprint.dynawave.entity.client;

import blueprint.dynawave.entity.SpinningBarModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer SPINNING_BAR_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "spinning_bar"), "main");

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(SPINNING_BAR_LAYER, SpinningBarModel::getTexturedModelData);
    }
}
