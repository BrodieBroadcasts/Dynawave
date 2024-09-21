// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package blueprint.dynawave.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class SpinningBarModel extends EntityModel<SpinningBarEntity> {
	private final ModelPart bone;
	private float sizeMultiplier; // Store size multiplier
	public SpinningBarModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-32.0F, -16.0F, 0.0F, 48.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -8.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay); // Render the custom model part
	}

	@Override
	public void setAngles(SpinningBarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.bone.yaw = ageInTicks * entity.getRotationSpeed(); // Rotate based on entity's speed
		this.sizeMultiplier = entity.getSizeMultiplier(); // Capture size multiplier
	}
}