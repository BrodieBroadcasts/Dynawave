package blueprint.dynawave.datagen;

import blueprint.dynawave.init.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool poolBlockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POOL_BLOCK);
        poolBlockPool.slab(ModBlocks.POOL_BLOCK_SLAB);
        poolBlockPool.stairs(ModBlocks.POOL_BLOCK_STAIRS);
        poolBlockPool.wall(ModBlocks.POOL_BLOCK_WALLS);

        BlockStateModelGenerator.BlockTexturePool darkPoolBlockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DARK_POOL_BLOCK);
        darkPoolBlockPool.slab(ModBlocks.DARK_POOL_BLOCK_SLAB);
        darkPoolBlockPool.stairs(ModBlocks.DARK_POOL_BLOCK_STAIRS);
        darkPoolBlockPool.wall(ModBlocks.DARK_POOL_BLOCK_WALLS);

        BlockStateModelGenerator.BlockTexturePool greenPoolBlockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GREEN_POOL_BLOCK);
        greenPoolBlockPool.slab(ModBlocks.GREEN_POOL_BLOCK_SLAB);
        greenPoolBlockPool.stairs(ModBlocks.GREEN_POOL_BLOCK_STAIRS);
        greenPoolBlockPool.wall(ModBlocks.GREEN_POOL_BLOCK_WALLS);

        BlockStateModelGenerator.BlockTexturePool redPoolBlockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RED_POOL_BLOCK);
        redPoolBlockPool.slab(ModBlocks.RED_POOL_BLOCK_SLAB);
        redPoolBlockPool.stairs(ModBlocks.RED_POOL_BLOCK_STAIRS);
        redPoolBlockPool.wall(ModBlocks.RED_POOL_BLOCK_WALLS);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POOL_GRID);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.JUMP_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
