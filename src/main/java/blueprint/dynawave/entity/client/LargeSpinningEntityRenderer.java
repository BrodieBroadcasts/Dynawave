package blueprint.dynawave.entity.client;

import blueprint.dynawave.entity.LargeBarModel;
import blueprint.dynawave.entity.SpinningBarEntity;
import blueprint.dynawave.entity.SpinningBarModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class LargeSpinningEntityRenderer extends EntityRenderer<SpinningBarEntity> {
    private final LargeBarModel model;

    public LargeSpinningEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        // Load the custom model from a model loader
        this.model = new LargeBarModel(ctx.getPart(ModModelLayers.LARGE_SPINNING_BAR_LAYER));
    }

    @Override
    public Identifier getTexture(SpinningBarEntity entity) {
        return new Identifier(MOD_ID, "textures/entity/large_spinning_bar.png");
    }

    @Override
    public void render(SpinningBarEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Apply the size multiplier during rendering
        matrices.push();


        // Scale the model based on the sizeMultiplier
        float scale = entity.getSizeMultiplier(); // Get size multiplier
        matrices.scale(scale, 1.0F, scale); // Scale length only, height remains constant

        // Translate to ensure the entity remains centered
        matrices.translate(0.5, 0, 0.5);

        // Apply rotation
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + tickDelta * entity.getRotationSpeed()));

        // Ren der the custom model
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity)));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
