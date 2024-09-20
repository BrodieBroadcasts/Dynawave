package blueprint.dynawave.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class StartingLineBlock extends Block {
    public StartingLineBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(CHECKPOINT, 0));
    }

    public static final IntProperty CHECKPOINT = IntProperty.of("checkpoint", 0, 9);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHECKPOINT);
    }
}
