package blueprint.dynawave.init;

import blueprint.dynawave.entity.SpinningBarEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class ModEntities {
    public static final EntityType<SpinningBarEntity> SPINNING_BAR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "spinning_bar"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SpinningBarEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f,1.0f)) // Default size, changable dynamically
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(1)
                    .build()
    );
}
