package blueprint.dynawave.entity.client;

import blueprint.dynawave.entity.LargeBarModel;
import blueprint.dynawave.entity.MediumBarModel;
import blueprint.dynawave.entity.SmallBarModel;
import blueprint.dynawave.entity.SpinningBarModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer SPINNING_BAR_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "spinning_bar"), "main");
    public static final EntityModelLayer SMALL_SPINNING_BAR_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "small_spinning_bar"), "main");
    public static final EntityModelLayer MEDIUM_SPINNING_BAR_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "medium_spinning_bar"), "main");
    public static final EntityModelLayer LARGE_SPINNING_BAR_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "large_spinning_bar"), "main");

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(SPINNING_BAR_LAYER, SpinningBarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SMALL_SPINNING_BAR_LAYER, SmallBarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MEDIUM_SPINNING_BAR_LAYER, MediumBarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LARGE_SPINNING_BAR_LAYER, LargeBarModel::getTexturedModelData);
    }
}
