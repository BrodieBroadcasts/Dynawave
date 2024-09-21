package blueprint.dynawave.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PoolNoodleItem extends Item {
    public PoolNoodleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient && attacker.getEquippedStack(EquipmentSlot.MAINHAND).isOf(this)) {
            target.takeKnockback(4, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());

            target.velocityModified = true;
        }
        return super.postHit(stack, target, attacker);
    }
}
