package blueprint.dynawave.datagen;

import blueprint.dynawave.init.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.POOL_BLOCK_WALLS)
                .add(ModBlocks.DARK_POOL_BLOCK_WALLS)
                .add(ModBlocks.GREEN_POOL_BLOCK_WALLS)
                .add(ModBlocks.RED_POOL_BLOCK_WALLS);

        getOrCreateTagBuilder(BlockTags.STAIRS)
                .add(ModBlocks.POOL_BLOCK_STAIRS)
                .add(ModBlocks.DARK_POOL_BLOCK_STAIRS)
                .add(ModBlocks.GREEN_POOL_BLOCK_STAIRS)
                .add(ModBlocks.RED_POOL_BLOCK_STAIRS);

        getOrCreateTagBuilder(BlockTags.SLABS)
                .add(ModBlocks.POOL_BLOCK_SLAB)
                .add(ModBlocks.DARK_POOL_BLOCK_SLAB)
                .add(ModBlocks.GREEN_POOL_BLOCK_SLAB)
                .add(ModBlocks.RED_POOL_BLOCK_SLAB);
    }
}
