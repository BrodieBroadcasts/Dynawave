package blueprint.dynawave.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class ModBlocks {
    public static final Block POOL_BLOCK = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CORAL)),
            "pool_block", true
    );
    public static final Block DARK_POOL_BLOCK = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CORAL)),
            "dark_pool_block", true
    );
    public static final Block GREEN_POOL_BLOCK = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CORAL)),
            "green_pool_block", true
    );
    public static final Block POOL_GRID = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CORAL)),
            "pool_grid", true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item
        Identifier id = new Identifier(MOD_ID, name);

        if(shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }
    public static void initialize() {}
}
