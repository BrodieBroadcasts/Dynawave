package blueprint.dynawave.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int checkpointType = state.get(CHECKPOINT);

        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
