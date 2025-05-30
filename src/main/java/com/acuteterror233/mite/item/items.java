package com.acuteterror233.mite.item;

import com.acuteterror233.mite.at_mite;
import com.acuteterror233.mite.item.armor.adamantium;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class items {
//    public static final Item ADAMANTIUM_HELMET = register(
//            "adamantium_helmet",
//            new Item.Settings().armor(adamantium.ADAMANTIUM_ARMORMATERIAL, EquipmentType.HELMET),
//            Item::new
//    );
//    public static final Item ADAMANTIUM_CHESTPLATE = register(
//            "adamantium_chestplate",
//            new Item.Settings().armor(adamantium.ADAMANTIUM_ARMORMATERIAL,EquipmentType.CHESTPLATE),
//            Item::new
//    );
//
//    public static final Item ADAMANTIUM_LEGGINGS = register(
//            "adamantium_leggings",
//            new Item.Settings().armor(adamantium.ADAMANTIUM_ARMORMATERIAL,EquipmentType.LEGGINGS),
//            Item::new
//    );
//
//    public static final Item ADAMANTIUM_BOOTS = register(
//            "adamantium_boots",
//            new Item.Settings().armor(adamantium.ADAMANTIUM_ARMORMATERIAL,EquipmentType.BOOTS),
//            Item::new
//    );
    public static final Item ADAMANTIUM_CHAINMAIL_HELMET = register("cs", new Item.Settings(), Item::new);
    private static Item register(String name, Item.Settings settings, Function<Item.Settings, Item> itemFactory) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(at_mite.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        return Registry.register(Registries.ITEM, itemKey, item);
    }

    public static void init(){

    }
}
