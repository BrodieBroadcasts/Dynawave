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
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POOL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DARK_POOL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GREEN_POOL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POOL_GRID);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
