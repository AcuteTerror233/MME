package com.acuteterror233.mite.mixin.world.item;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.item.enchantment.MMEEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin extends Item {
    public HoeItemMixin(Properties properties) {
        super(properties);
    }
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;hoe(Lnet/minecraft/world/item/ToolMaterial;FF)Lnet/minecraft/world/item/Item$Properties;"))
    private static Properties init(Properties instance, ToolMaterial toolMaterial, float f, float g) {
        return instance;
    }

    @Overwrite
    public static Consumer<UseOnContext> changeIntoState(BlockState blockState) {
        return useOnContext -> {
            useOnContext.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, useOnContext.getClickedPos(), GameEvent.Context.of(useOnContext.getPlayer(), blockState));
            if (blockState.is(Blocks.FARMLAND)) {
                for (Object2IntMap.Entry<Holder<Enchantment>> entry : useOnContext.getItemInHand().getEnchantments().entrySet()) {
                    if (entry.getKey().is(MMEEnchantments.FERTILITY)) {
                        double ratio = 5;
                        ratio *= entry.getIntValue();
                        RandomSource random = useOnContext.getLevel().getRandom();
                        int i = random.nextInt(100);
                        if (i <= ratio) {
                            useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), blockState.setValue(MMEBlockStateProperties.FERTILE, true), 11);
                            return;
                        }
                    }
                }
            }
            useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), blockState, 11);
        };
    }
}
