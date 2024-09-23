package blueprint.dynawave.item;

import blueprint.dynawave.entity.CoconutProjectile;
import blueprint.dynawave.particle.ModParticles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CoconutItem extends Item {
    public CoconutItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL,
                0.5F, 1F); // plays a globalSoundEvent
		/*
		user.getItemCooldownManager().set(this, 5);
		Optionally, you can add a cooldown to your item's right-click use, similar to Ender Pearls.
		*/
        if (!world.isClient) {
            CoconutProjectile coconutProjectile = new CoconutProjectile(user, world);
            coconutProjectile.setItem(itemStack);
            coconutProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(coconutProjectile); // spawns entity
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
