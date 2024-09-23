package blueprint.dynawave.init;

import blueprint.dynawave.item.CoconutItem;
import blueprint.dynawave.item.PoolNoodleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static blueprint.dynawave.Dynawave.MOD_ID;

public class ModItems {
    public static final Item POOL_NOODLE = register(
            new PoolNoodleItem(new FabricItemSettings().maxCount(1)),
            "pool_noodle"
    );

    public static final Item COCONUT = register(
            new CoconutItem(new FabricItemSettings().maxCount(16)),
            "coconut"
    );

    public static Item register(Item item, String id) {
        // Create the identifier for the item
        Identifier itemID = new Identifier(MOD_ID, id);

        // Register the item
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, DYNAWAVE_GROUP_KEY, DYNAWAVE_GROUP);

        ItemGroupEvents.modifyEntriesEvent(DYNAWAVE_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.POOL_BLOCK);
            itemGroup.add(ModBlocks.POOL_BLOCK_SLAB);
            itemGroup.add(ModBlocks.POOL_BLOCK_STAIRS);
            itemGroup.add(ModBlocks.POOL_BLOCK_WALLS);

            itemGroup.add(ModBlocks.DARK_POOL_BLOCK);
            itemGroup.add(ModBlocks.DARK_POOL_BLOCK_SLAB);
            itemGroup.add(ModBlocks.DARK_POOL_BLOCK_STAIRS);
            itemGroup.add(ModBlocks.DARK_POOL_BLOCK_WALLS);

            itemGroup.add(ModBlocks.GREEN_POOL_BLOCK);
            itemGroup.add(ModBlocks.GREEN_POOL_BLOCK_SLAB);
            itemGroup.add(ModBlocks.GREEN_POOL_BLOCK_STAIRS);
            itemGroup.add(ModBlocks.GREEN_POOL_BLOCK_WALLS);

            itemGroup.add(ModBlocks.RED_POOL_BLOCK);
            itemGroup.add(ModBlocks.RED_POOL_BLOCK_SLAB);
            itemGroup.add(ModBlocks.RED_POOL_BLOCK_STAIRS);
            itemGroup.add(ModBlocks.RED_POOL_BLOCK_WALLS);

            itemGroup.add(ModBlocks.POOL_GRID);

            itemGroup.add(ModBlocks.STARTING_LINE_BLOCK);

            itemGroup.add(ModBlocks.JUMP_BLOCK);

            itemGroup.add(ModBlocks.GEYSER);

            itemGroup.add(ModItems.POOL_NOODLE);

            itemGroup.add(ModItems.COCONUT);
        });
    }

    public static final RegistryKey<ItemGroup> DYNAWAVE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(MOD_ID, "item_group"));
    public static final ItemGroup DYNAWAVE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.POOL_BLOCK))
            .displayName(Text.translatable("itemGroup.dynawave"))
            .build();
}
