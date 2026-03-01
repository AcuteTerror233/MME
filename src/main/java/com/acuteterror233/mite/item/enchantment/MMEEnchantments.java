package com.acuteterror233.mite.item.enchantment;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public final class MMEEnchantments {
    public static final ResourceKey<Enchantment> BUTCHERING = key("butchering");
    public static final ResourceKey<Enchantment> SLAYING = key("slaying");
    public static final ResourceKey<Enchantment> CLEAVING = key("cleaving");
    public static final ResourceKey<Enchantment> HARVESTING = key("harvesting");
    public static final ResourceKey<Enchantment> FERTILITY = key("fertility");
    public static final ResourceKey<Enchantment> PIERCING = key("piercing");
    public static final ResourceKey<Enchantment> SPEED = key("speed");
    public static final ResourceKey<Enchantment> ENDURANCE = key("endurance");

    public static void bootstrap(BootstrapContext<Enchantment> bootstrapContext) {
        HolderGetter<DamageType> damageTypeGetter = bootstrapContext.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> enchantmentGetter = bootstrapContext.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> itemGetter = bootstrapContext.lookup(Registries.ITEM);
        HolderGetter<Block> blockGetter = bootstrapContext.lookup(Registries.BLOCK);
        HolderGetter<EntityType<?>> entityTypeGetter = bootstrapContext.lookup(Registries.ENTITY_TYPE);
        register(
                bootstrapContext,//上下文
                BUTCHERING,//注册名字
                Enchantment.enchantment(
                        Enchantment.definition(
                                itemGetter.getOrThrow(MMEItemTags.DAGGER_ENCHANTABLE),//可应用物品
                                2,//选择权重
                                3,//最高等级
                                Enchantment.dynamicCost(20, 8),//修正附魔等级范围
                                Enchantment.dynamicCost(70, 8),
                                4,//铁砧成本
                                EquipmentSlotGroup.MAINHAND//生效槽位
                        )
                ).withEffect(
                        EnchantmentEffectComponents.EQUIPMENT_DROPS,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,
                        new AddValue(LevelBasedValue.perLevel(0.01F)),
                        LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeGetter, EntityType.PLAYER))
                        )
                )
        );
    }
    private static void register(BootstrapContext<Enchantment> bootstrapContext, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder) {
        bootstrapContext.register(resourceKey, builder.build(resourceKey.location()));
    }

    private static ResourceKey<Enchantment> key(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
    }
}
