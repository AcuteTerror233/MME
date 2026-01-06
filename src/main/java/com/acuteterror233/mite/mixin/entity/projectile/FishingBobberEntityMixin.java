package com.acuteterror233.mite.mixin.entity.projectile;

import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FishingHook.class)
public abstract class FishingBobberEntityMixin extends Projectile {
    public FishingBobberEntityMixin(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * @author AcuteTerror233
     * @reason 修改钓鱼竿浮标判断
     */
    @Overwrite
    private boolean shouldStopFishing(Player player) {
        ItemStack itemStack = player.getMainHandItem();
        ItemStack itemStack2 = player.getOffhandItem();
        boolean bl = itemStack.is(MMETags.FISHING_RODS);
        boolean bl2 = itemStack2.is(MMETags.FISHING_RODS);
        if (!player.isRemoved() && player.isAlive() && (bl || bl2) && !(this.distanceToSqr(player) > 1024.0)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }
}
