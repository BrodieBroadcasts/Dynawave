package blueprint.dynawave.init;

import blueprint.dynawave.entity.CoconutProjectile;
import blueprint.dynawave.entity.SpinningBarEntity;
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
            EntityType.Builder.create(SpinningBarEntity::new, SpawnGroup.MISC)
                    .setDimensions(1.0f,1.0f) // Default size, changable dynamically
                    .build("spinning_bar")
    );

        public static final EntityType<SpinningBarEntity> SMALL_SPINNING_BAR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "small_spinning_bar"),
            EntityType.Builder.create(SpinningBarEntity::new, SpawnGroup.MISC)
                    .setDimensions(1.0f,1.0f) // Default size, changable dynamically
                    .build("small_spinning_bar")
    );

        public static final EntityType<SpinningBarEntity> MEDIUM_SPINNING_BAR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "medium_spinning_bar"),
            EntityType.Builder.create(SpinningBarEntity::new, SpawnGroup.MISC)
                    .setDimensions(2.0f,1.0f) // Default size, changable dynamically
                    .build("medium_spinning_bar")
    );

        public static final EntityType<SpinningBarEntity> LARGE_SPINNING_BAR = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "large_spinning_bar"),
            EntityType.Builder.create(SpinningBarEntity::new, SpawnGroup.MISC)
                    .setDimensions(1.0f,1.0f) // Default size, changable dynamically
                    .build("large_spinning_bar")
    );


    public static final EntityType<CoconutProjectile> COCONUT_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "coconut_projectile"),
            EntityType.Builder.<CoconutProjectile>create(CoconutProjectile::new, SpawnGroup.MISC)
                    .setDimensions(0.25f, 0.25f)
                    .build()
    );

    public static void initialize() {
        // Register entity types here
    }
}
