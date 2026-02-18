package com.acuteterror233.mite.mixin.world.entity.animal;

import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMETags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractCow;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin extends Animal {
    @Unique
    private int recoveryCounter = 0;
    @Unique
    private static final int maxMilkCounter = 4;
    @Unique
    private int milkCounter = maxMilkCounter;
    protected AbstractCowMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
    /**
     * @author AcuteError233
     * @reason 修改牛的交互
     */
    @Overwrite
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!this.isBaby()) {
            if (itemStack.is(MMETags.BUCKET) && this.milkCounter == 4){
                this.milkCounter-=4;
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                Optional<Item> milk = BuiltInRegistries.ITEM.getOptional(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).withPrefix("milk_"));
                if (milk.isPresent()) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, milk.get().getDefaultInstance());
                    player.setItemInHand(interactionHand, itemStack2);
                    return InteractionResult.SUCCESS;
                }else {
                    return super.mobInteract(player, interactionHand);
                }
            }if (itemStack.is(Items.BOWL) && this.milkCounter >= 1){
                this.milkCounter-=1;
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                itemStack.consume(1, player);
                player.getInventory().add(MMEItems.BOWL_MILK.getDefaultInstance());
                return InteractionResult.SUCCESS;
            }else {
                return super.mobInteract(player, interactionHand);
            }
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }
    @Override
    public void tick(){
        super.tick();
        if (this.recoveryCounter <= 12000 && this.milkCounter < this.maxMilkCounter){
            this.recoveryCounter++;
        }
        if (this.recoveryCounter > 12000){
            this.milkCounter++;
            this.recoveryCounter-=12000;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("RecoveryCounter", recoveryCounter);
        compoundTag.putInt("MilkCounter", milkCounter);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.recoveryCounter = compoundTag.getInt("RecoveryCounter").orElse(0);
        this.milkCounter = compoundTag.getInt("MilkCounter").orElse(recoveryCounter);
    }
}
