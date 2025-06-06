package com.acuteterror233.mite.mixin.item;

import com.acuteterror233.mite.atinterface.ItemSettingsExtension;
import com.acuteterror233.mite.component.At_DataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "net.minecraft.item.Item$Settings")
public abstract class MixinItemSettings implements net.fabricmc.fabric.api.item.v1.FabricItem.Settings,ItemSettingsExtension{

    @Shadow public abstract Item.Settings maxDamage(int maxDamage);
    @Shadow public abstract Item.Settings sword(ToolMaterial material, float attackDamage, float attackSpeed);

    @Overwrite
    public Item.Settings tool(ToolMaterial material, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        return material.applyToolSettings(((Item.Settings) (Object) this), effectiveBlocks, attackDamage, attackSpeed, disableBlockingForSeconds);
    }

    @Overwrite
    public Item.Settings pickaxe(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability() * 3);
        return this.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Overwrite
    public Item.Settings axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability() * 3);
        return this.tool(material, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 5.0F);
    }

    @Overwrite
    public Item.Settings hoe(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 0.5f);
        this.maxDamage(material.durability() * 2);
        return this.tool(material, BlockTags.HOE_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Overwrite
    public Item.Settings shovel(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability());
        return this.tool(material, BlockTags.SHOVEL_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Override
    @Unique
    public Item.Settings hand_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 0.5f);
        this.maxDamage(material.durability());
        return this.tool(material, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Override
    @Unique
    public Item.Settings mattock(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability() * 4);
        return this.tool(material, BlockTags.SHOVEL_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Override
    @Unique
    public Item.Settings war_hammer(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability() * 5);
        return this.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
    }

    @Override
    @Unique
    public Item.Settings dagger(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 0.5f);
        this.maxDamage(material.durability());
        return this.sword(material, attackDamage, attackSpeed);
    }

    @Override
    @Unique
    public Item.Settings battle_axe(ToolMaterial material, float attackDamage, float attackSpeed) {
        ((Item.Settings) (Object) this).component(At_DataComponentTypes.INTERACTION_DISTANCE, 1.0f);
        this.maxDamage(material.durability() * 4);
        return this.sword(material, attackDamage, attackSpeed);
    }
}
