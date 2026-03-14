package com.acuteterror233.mite.mixin.world.entity.npc;

import com.acuteterror233.mite.item.MMEItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.providers.TradeRebalanceEnchantmentProviders;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mixin(VillagerTrades.class)
public class VillagerTradesMixin {
    @Shadow
    @Final
    @Mutable
    public static Map<ResourceKey<VillagerProfession>, Int2ObjectMap<VillagerTrades.ItemListing[]>> TRADES = Util.make(
            Maps.<ResourceKey<VillagerProfession>, Int2ObjectMap<VillagerTrades.ItemListing[]>>newHashMap(),
            hashMap -> {
                hashMap.put(
                        VillagerProfession.FARMER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.WHEAT, 6, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Items.POTATO, 6, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Items.CARROT, 6, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Items.BEETROOT, 6, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.BREAD, 1, 3, 16, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Blocks.PUMPKIN, 2, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Items.PUMPKIN_PIE, 1, 2, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.APPLE, 1, 2, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Blocks.MELON, 2, 12, 20),
                                                new VillagerTrades.ItemsForEmeralds(Items.COOKIE, 3, 18, 10)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CAKE, 1, 1, 12, 15),
                                                new VillagerTrades.SuspiciousStewForEmerald(MobEffects.NIGHT_VISION, 100, 15),
                                                new VillagerTrades.SuspiciousStewForEmerald(MobEffects.JUMP_BOOST, 160, 15),
                                                new VillagerTrades.SuspiciousStewForEmerald(MobEffects.WEAKNESS, 140, 15),
                                                new VillagerTrades.SuspiciousStewForEmerald(MobEffects.BLINDNESS, 120, 15),
                                                new VillagerTrades.SuspiciousStewForEmerald(MobEffects.POISON, 280, 15)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.GOLDEN_CARROT, 1, 2, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.GLISTERING_MELON_SLICE, 1, 2, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.GOLDEN_APPLE, 2, 1, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.FISHERMAN,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.PUFFERFISH, 1, 16, 1),
                                                new VillagerTrades.EmeraldForItems(Items.TROPICAL_FISH, 2, 16, 1),
                                                new VillagerTrades.EmeraldForItems(Items.COD, 4, 16, 1),
                                                new VillagerTrades.EmeraldForItems(Items.SALMON, 4, 16, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.CAMPFIRE, 1, 1, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.COOKED_COD, 1, 6, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.COOKED_SALMON, 1, 6, 5),
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.IRON_FISHING_ROD, 1, 3, 10, 0.2F)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsAndEmeraldsToItems(Items.COD, 16, 1, Items.COOKED_COD, 16, 16, 5, 0.05F),
                                                new VillagerTrades.ItemsAndEmeraldsToItems(Items.SALMON, 16, 1, Items.COOKED_SALMON, 16, 16, 5, 0.05F),
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_FISHING_ROD, 3, 3, 10, 0.2F),
                                                new VillagerTrades.EmeraldsForVillagerTypeItem(
                                                        1,
                                                        12,
                                                        30,
                                                        ImmutableMap.<ResourceKey<VillagerType>, Item>builder()
                                                                .put(VillagerType.PLAINS, Items.OAK_BOAT)
                                                                .put(VillagerType.TAIGA, Items.SPRUCE_BOAT)
                                                                .put(VillagerType.SNOW, Items.SPRUCE_BOAT)
                                                                .put(VillagerType.DESERT, Items.JUNGLE_BOAT)
                                                                .put(VillagerType.JUNGLE, Items.JUNGLE_BOAT)
                                                                .put(VillagerType.SAVANNA, Items.ACACIA_BOAT)
                                                                .put(VillagerType.SWAMP, Items.DARK_OAK_BOAT)
                                                                .build()
                                                )
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.SHEPHERD,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Blocks.WHITE_WOOL, 4, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Blocks.BROWN_WOOL, 4, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Blocks.BLACK_WOOL, 4, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Blocks.GRAY_WOOL, 4, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(MMEItems.COPPER_SHEARS, 1, 1, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.WHITE_DYE, 12, 16, 10),
                                                new VillagerTrades.EmeraldForItems(Items.GRAY_DYE, 12, 16, 10),
                                                new VillagerTrades.EmeraldForItems(Items.BLACK_DYE, 12, 16, 10),
                                                new VillagerTrades.EmeraldForItems(Items.LIGHT_BLUE_DYE, 12, 16, 10),
                                                new VillagerTrades.EmeraldForItems(Items.LIME_DYE, 12, 16, 10),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.WHITE_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.ORANGE_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.MAGENTA_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.YELLOW_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIME_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PINK_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GRAY_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CYAN_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PURPLE_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLUE_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BROWN_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GREEN_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.RED_WOOL, 1, 1, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLACK_WOOL, 1, 1, 16, 5),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.WHITE_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.ORANGE_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.MAGENTA_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.YELLOW_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIME_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PINK_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GRAY_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CYAN_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PURPLE_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLUE_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BROWN_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GREEN_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.RED_CARPET, 1, 4, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLACK_CARPET, 1, 4, 16, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.YELLOW_DYE, 12, 16, 20),
                                                new VillagerTrades.EmeraldForItems(Items.LIGHT_GRAY_DYE, 12, 16, 20),
                                                new VillagerTrades.EmeraldForItems(Items.ORANGE_DYE, 12, 16, 20),
                                                new VillagerTrades.EmeraldForItems(Items.RED_DYE, 12, 16, 20),
                                                new VillagerTrades.EmeraldForItems(Items.PINK_DYE, 12, 16, 20),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.WHITE_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.YELLOW_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.RED_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLACK_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLUE_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BROWN_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CYAN_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GRAY_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GREEN_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_BLUE_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_GRAY_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIME_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.MAGENTA_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.ORANGE_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PINK_BED, 2, 1, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PURPLE_BED, 2, 1, 12, 10)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.BROWN_DYE, 12, 16, 30),
                                                new VillagerTrades.EmeraldForItems(Items.PURPLE_DYE, 12, 16, 30),
                                                new VillagerTrades.EmeraldForItems(Items.BLUE_DYE, 12, 16, 30),
                                                new VillagerTrades.EmeraldForItems(Items.GREEN_DYE, 12, 16, 30),
                                                new VillagerTrades.EmeraldForItems(Items.MAGENTA_DYE, 12, 16, 30),
                                                new VillagerTrades.EmeraldForItems(Items.CYAN_DYE, 12, 16, 30),

                                                new VillagerTrades.ItemsForEmeralds(Items.WHITE_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.BLUE_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.RED_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.PINK_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.GREEN_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.LIME_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.GRAY_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.BLACK_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.PURPLE_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.MAGENTA_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.CYAN_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.BROWN_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.YELLOW_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.ORANGE_BANNER, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 1, 1, 12, 15)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.PAINTING, 1, 4, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.FLETCHER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.STICK, 32, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.ARROW, 1, 8, 1),
                                                new VillagerTrades.ItemsAndEmeraldsToItems(Items.FLINT, 1, 1, Items.ARROW, 16, 12, 1, 0.05F)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.FLINT, 8, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Items.BOW, 2, 1, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.STRING, 16, 16, 20),
                                                new VillagerTrades.ItemsForEmeralds(Items.CROSSBOW, 3, 1, 10)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.FEATHER, 24, 16, 30),
                                                new VillagerTrades.EnchantedItemForEmeralds(Items.BOW, 2, 3, 15)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.TRIPWIRE_HOOK, 4, 12, 30),
                                                new VillagerTrades.EnchantedItemForEmeralds(Items.CROSSBOW, 3, 3, 15),
                                                new VillagerTrades.TippedArrowForItemsAndEmeralds(Items.ARROW, 1, Items.TIPPED_ARROW, 16, 1, 12, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.LIBRARIAN,
                        toIntMap(
                                ImmutableMap.<Integer, VillagerTrades.ItemListing[]>builder()
                                        .put(
                                                1,
                                                new VillagerTrades.ItemListing[]{
                                                        new VillagerTrades.EmeraldForItems(Items.PAPER, 16, 16, 2),
                                                        new VillagerTrades.EnchantBookForEmeralds(1, 1, 1, EnchantmentTags.TRADEABLE),
                                                        new VillagerTrades.ItemsForEmeralds(Blocks.BOOKSHELF, 6, 1, 12, 1)
                                                }
                                        )
                                        .put(
                                                2,
                                                new VillagerTrades.ItemListing[]{
                                                        new VillagerTrades.EmeraldForItems(Items.BOOK, 4, 12, 10),
                                                        new VillagerTrades.EnchantBookForEmeralds(5, 1, 3, EnchantmentTags.TRADEABLE),
                                                        new VillagerTrades.ItemsForEmeralds(Items.LANTERN, 1, 2, 5)
                                                }
                                        )
                                        .put(
                                                3,
                                                new VillagerTrades.ItemListing[]{
                                                        new VillagerTrades.EmeraldForItems(Items.INK_SAC, 6, 12, 20),
                                                        new VillagerTrades.EnchantBookForEmeralds(10, 1, 5, EnchantmentTags.TRADEABLE),
                                                        new VillagerTrades.ItemsForEmeralds(Items.GLASS, 1, 4, 10)
                                                }
                                        )
                                        .put(
                                                4,
                                                new VillagerTrades.ItemListing[]{
                                                        new VillagerTrades.EmeraldForItems(Items.WRITABLE_BOOK, 1, 12, 30),
                                                        new VillagerTrades.EnchantBookForEmeralds(15, 3, 5, EnchantmentTags.TRADEABLE),
                                                        new VillagerTrades.ItemsForEmeralds(Items.CLOCK, 3, 1, 15),
                                                        new VillagerTrades.ItemsForEmeralds(Items.COMPASS, 3, 1, 15)
                                                }
                                        )
                                        .put(
                                                5,
                                                new VillagerTrades.ItemListing[]{
                                                        new VillagerTrades.ItemsForEmeralds(Items.NAME_TAG, 16, 1, 30)
                                                }
                                        )
                                        .build()
                        )
                );
                hashMap.put(
                        VillagerProfession.CARTOGRAPHER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.PAPER, 16, 12, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.MAP, 3, 1, 12, 1, 0.05F)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.GLASS_PANE, 12, 12, 10),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(8, StructureTags.ON_TAIGA_VILLAGE_MAPS, "filled_map.village_taiga", MapDecorationTypes.TAIGA_VILLAGE, 12, 5),
                                                        VillagerType.SWAMP,
                                                        VillagerType.SNOW,
                                                        VillagerType.PLAINS
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(8, StructureTags.ON_SWAMP_EXPLORER_MAPS, "filled_map.explorer_swamp", MapDecorationTypes.SWAMP_HUT, 12, 5),
                                                        VillagerType.TAIGA,
                                                        VillagerType.SNOW,
                                                        VillagerType.JUNGLE
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(8, StructureTags.ON_SNOWY_VILLAGE_MAPS, "filled_map.village_snowy", MapDecorationTypes.SNOWY_VILLAGE, 12, 5),
                                                        VillagerType.TAIGA,
                                                        VillagerType.SWAMP
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(
                                                                8, StructureTags.ON_SAVANNA_VILLAGE_MAPS, "filled_map.village_savanna", MapDecorationTypes.SAVANNA_VILLAGE, 12, 5
                                                        ),
                                                        VillagerType.PLAINS,
                                                        VillagerType.JUNGLE,
                                                        VillagerType.DESERT
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(
                                                                8, StructureTags.ON_PLAINS_VILLAGE_MAPS, "filled_map.village_plains", MapDecorationTypes.PLAINS_VILLAGE, 12, 5
                                                        ),
                                                        VillagerType.TAIGA,
                                                        VillagerType.SNOW,
                                                        VillagerType.SAVANNA,
                                                        VillagerType.DESERT
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(
                                                                8, StructureTags.ON_JUNGLE_EXPLORER_MAPS, "filled_map.explorer_jungle", MapDecorationTypes.JUNGLE_TEMPLE, 12, 5
                                                        ),
                                                        VillagerType.SWAMP,
                                                        VillagerType.SAVANNA,
                                                        VillagerType.DESERT
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.TreasureMapForEmeralds(
                                                                8, StructureTags.ON_DESERT_VILLAGE_MAPS, "filled_map.village_desert", MapDecorationTypes.DESERT_VILLAGE, 12, 5
                                                        ),
                                                        VillagerType.SAVANNA,
                                                        VillagerType.JUNGLE
                                                )
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.COMPASS, 2, 12, 20),
                                                new VillagerTrades.TreasureMapForEmeralds(
                                                        7, StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapDecorationTypes.OCEAN_MONUMENT, 12, 10
                                                ),
                                                new VillagerTrades.TreasureMapForEmeralds(
                                                        6, StructureTags.ON_TRIAL_CHAMBERS_MAPS, "filled_map.trial_chambers", MapDecorationTypes.TRIAL_CHAMBERS, 12, 10
                                                )
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.ITEM_FRAME, 1, 3, 12, 15, 0.05F),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.BLUE_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SNOW, VillagerType.TAIGA
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.WHITE_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SNOW, VillagerType.PLAINS
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.RED_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SNOW, VillagerType.SAVANNA
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.GREEN_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.DESERT, VillagerType.SAVANNA, VillagerType.JUNGLE
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.LIME_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.DESERT, VillagerType.TAIGA
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.PURPLE_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.TAIGA, VillagerType.SWAMP
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.CYAN_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.DESERT, VillagerType.SNOW
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.YELLOW_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.PLAINS, VillagerType.JUNGLE
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.ORANGE_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SAVANNA, VillagerType.DESERT
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.BROWN_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.PLAINS, VillagerType.JUNGLE
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(new VillagerTrades.ItemsForEmeralds(Items.MAGENTA_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SAVANNA),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SNOW, VillagerType.SWAMP
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                        new VillagerTrades.ItemsForEmeralds(Items.PINK_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.TAIGA, VillagerType.PLAINS
                                                ),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(new VillagerTrades.ItemsForEmeralds(Items.GRAY_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.DESERT),
                                                VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(new VillagerTrades.ItemsForEmeralds(Items.BLACK_BANNER, 2, 1, 12, 15, 0.05F), VillagerType.SWAMP)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.GLOBE_BANNER_PATTERN, 4, 1, 12, 30, 0.05F),
                                                new VillagerTrades.TreasureMapForEmeralds(7, StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapDecorationTypes.WOODLAND_MANSION, 12, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.CLERIC,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.ROTTEN_FLESH, 32, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.GLASS_BOTTLE, 1, 16, 1),
                                                new VillagerTrades.ItemsForEmeralds(Items.REDSTONE, 1, 4, 1),
                                                new VillagerTrades.ItemsForEmeralds(Items.SUGAR, 1, 4, 1),
                                                new VillagerTrades.ItemsForEmeralds(Items.RABBIT_FOOT, 1, 4, 1),
                                                new VillagerTrades.ItemsForEmeralds(Items.SPIDER_EYE, 1, 4, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.GOLD_INGOT, 2, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(Items.LAPIS_LAZULI, 1, 4, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.GLOWSTONE_DUST, 1, 4, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.GLISTERING_MELON_SLICE, 1, 4, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.PUFFERFISH, 1, 1, 5),
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.FERMENTED_SPIDER_EYE, 2, 1, 10),
                                                new VillagerTrades.ItemsForEmeralds(Items.MAGMA_CREAM, 1, 4, 10),
                                                new VillagerTrades.ItemsForEmeralds(Items.BLAZE_POWDER, 1, 2, 10),
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.TURTLE_SCUTE, 2, 12, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.ENDER_PEARL, 5, 1, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.GUNPOWDER, 1, 4, 15),
                                                new VillagerTrades.ItemsForEmeralds(Items.GHAST_TEAR, 1, 4, 15),
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.NETHER_WART, 8, 12, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.EXPERIENCE_BOTTLE, 1, 2, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.DRAGON_BREATH, 1, 2, 30),
                                                new VillagerTrades.ItemsForEmeralds(Items.GOLDEN_CARROT, 1, 4, 30),
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.ARMORER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.COAL, 4, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_LEGGINGS), 7, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_BOOTS), 4, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_HELMET), 5, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_CHESTPLATE), 9, 1, 12, 1, 0.2F)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.IRON_INGOT, 4, 12, 10),
                                                new VillagerTrades.EmeraldForItems(Items.COPPER_INGOT, 8, 12, 10),
                                                new VillagerTrades.EmeraldForItems(MMEItems.SILVER_INGOT, 8, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.BELL), 4, 1, 12, 5, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.CHAINMAIL_BOOTS), 1, 1, 12, 5, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.CHAINMAIL_LEGGINGS), 3, 1, 12, 5, 0.2F)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.LAVA_BUCKET, 1, 12, 20),
                                                new VillagerTrades.EmeraldForItems(Items.DIAMOND, 2, 12, 20),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.CHAINMAIL_HELMET), 1, 1, 12, 10, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 4, 1, 12, 10, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.SHIELD), 3, 1, 12, 10, 0.2F)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_CHAINMAIL_LEGGINGS, 14, 3, 15, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_CHAINMAIL_BOOTS, 8, 3, 15, 0.2F)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_CHAINMAIL_HELMET, 8, 3, 30, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_CHAINMAIL_CHESTPLATE, 16, 3, 30, 0.2F)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.WEAPONSMITH,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.COAL, 4, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(Items.IRON_SWORD, 2, 3, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.IRON_INGOT, 4, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.BELL), 4, 1, 12, 5, 0.2F)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.FLINT, 8, 12, 20)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.DIAMOND, 2, 1, 30),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_AXE, 12, 3, 15, 0.2F)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_SWORD, 8, 3, 30, 0.2F)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.TOOLSMITH,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.COAL, 1, 3, 1),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_AXE), 2, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_SHOVEL), 2, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_PICKAXE), 2, 1, 12, 1, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.IRON_HOE), 2, 1, 12, 1, 0.2F)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.IRON_INGOT, 4, 12, 10),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.BELL), 4, 1, 12, 5, 0.2F)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.FLINT, 8, 12, 20),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_AXE, 1, 3, 10, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_SHOVEL, 2, 3, 10, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_PICKAXE, 3, 3, 10, 0.2F),
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(MMEItems.MITHRIL_HOE), 6, 1, 3, 10, 0.2F)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Items.DIAMOND, 2, 1, 15),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_BATTLE_AXE, 12, 3, 15, 0.2F),
                                                new VillagerTrades.EnchantedItemForEmeralds(MMEItems.MITHRIL_WAR_HAMMER, 5, 3, 15, 0.2F)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(MMEItems.MITHRIL_INGOT, 4, 1, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.BUTCHER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.CHICKEN, 3, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Items.PORKCHOP, 2, 16, 2),
                                                new VillagerTrades.EmeraldForItems(Items.RABBIT, 2, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.RABBIT_STEW, 1, 1, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.COAL, 4, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.COOKED_PORKCHOP, 2, 3, 16, 5),
                                                new VillagerTrades.ItemsForEmeralds(Items.COOKED_CHICKEN, 2, 4, 16, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.MUTTON, 3, 16, 20),
                                                new VillagerTrades.EmeraldForItems(Items.BEEF, 2, 16, 20)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.DRIED_KELP_BLOCK, 10, 12, 30)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.SWEET_BERRIES, 25, 12, 30),
                                                new VillagerTrades.EmeraldForItems(MMEItems.BLUE_BERRIE, 25, 12, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.LEATHERWORKER,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.LEATHER, 3, 16, 2),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_LEGGINGS, 1),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.FLINT, 4, 12, 10),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_HELMET, 1, 12, 5),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_BOOTS, 1, 12, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.RABBIT_HIDE, 9, 12, 20),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 1)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.TURTLE_SCUTE, 4, 12, 30),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_HORSE_ARMOR, 1, 12, 15)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2F),
                                                new VillagerTrades.DyedArmorForEmeralds(Items.LEATHER_HELMET, 1, 12, 30)
                                        }
                                )
                        )
                );
                hashMap.put(
                        VillagerProfession.MASON,
                        toIntMap(
                                ImmutableMap.of(
                                        1,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.CLAY_BALL, 8, 16, 2),
                                                new VillagerTrades.ItemsForEmeralds(Items.BRICK, 1, 12, 16, 1)
                                        },
                                        2,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Blocks.STONE, 4, 16, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5)
                                        },
                                        3,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Blocks.GRANITE, 4, 4, 20),
                                                new VillagerTrades.EmeraldForItems(Blocks.ANDESITE, 4, 4, 20),
                                                new VillagerTrades.EmeraldForItems(Blocks.DIORITE, 4, 4, 20),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.DRIPSTONE_BLOCK, 1, 4, 16, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.POLISHED_ANDESITE, 1, 4, 8, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.POLISHED_DIORITE, 1, 4, 8, 10),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.POLISHED_GRANITE, 1, 4, 8, 10)
                                        },
                                        4,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.EmeraldForItems(Items.QUARTZ, 4, 8, 30),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.RED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15),

                                                new VillagerTrades.ItemsForEmeralds(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15)
                                        },
                                        5,
                                        new VillagerTrades.ItemListing[]{
                                                new VillagerTrades.ItemsForEmeralds(Blocks.QUARTZ_PILLAR, 1, 4, 12, 30),
                                                new VillagerTrades.ItemsForEmeralds(Blocks.QUARTZ_BLOCK, 1, 4, 12, 30)
                                        }
                                )
                        )
                );
            }
    );
    @Shadow
    @Final
    @Mutable
    public static List<Pair<VillagerTrades.ItemListing[], Integer>> WANDERING_TRADER_TRADES = ImmutableList.<Pair<VillagerTrades.ItemListing[], Integer>>builder()
            .add(
                    Pair.of(
                            new VillagerTrades.ItemListing[]{
                                    new VillagerTrades.EmeraldForItems(potionCost(Potions.WATER), 2, 1, 1),
                                    new VillagerTrades.EmeraldForItems(Items.WATER_BUCKET, 1, 2, 1, 2),
                                    new VillagerTrades.EmeraldForItems(Items.MILK_BUCKET, 1, 2, 1, 2),
                                    new VillagerTrades.EmeraldForItems(Items.FERMENTED_SPIDER_EYE, 1, 2, 1, 3),
                                    new VillagerTrades.EmeraldForItems(Items.BAKED_POTATO, 4, 2, 1),
                                    new VillagerTrades.EmeraldForItems(Items.HAY_BLOCK, 1, 2, 1)
                            },
                            2
                    )
            )
            .add(
                    Pair.of(
                            new VillagerTrades.ItemListing[]{
                                    new VillagerTrades.ItemsForEmeralds(Items.PACKED_ICE, 1, 1, 6, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.GUNPOWDER, 1, 4, 2, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.ACACIA_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.BIRCH_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.DARK_OAK_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.JUNGLE_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.OAK_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.SPRUCE_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.CHERRY_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.MANGROVE_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Blocks.PALE_OAK_LOG, 1, 2, 4, 1),
                                    new VillagerTrades.EnchantedItemForEmeralds(MMEItems.ADAMANTIUM_PICKAXE, 1, 1, 1, 0.2F),
                                    new VillagerTrades.ItemsForEmeralds(potion(Potions.LONG_INVISIBILITY), 5, 1, 1, 1)
                            },
                            2
                    )
            )
            .add(
                    Pair.of(
                            new VillagerTrades.ItemListing[]{
                                    new VillagerTrades.ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 3, 1, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 3, 1, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SEA_PICKLE, 2, 1, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.FERN, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SUGAR_CANE, 1, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PUMPKIN, 1, 1, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.KELP, 3, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.CACTUS, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.DANDELION, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.POPPY, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BLUE_ORCHID, 1, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.ALLIUM, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.AZURE_BLUET, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.RED_TULIP, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.ORANGE_TULIP, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.WHITE_TULIP, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PINK_TULIP, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.OXEYE_DAISY, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.CORNFLOWER, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.OPEN_EYEBLOSSOM, 1, 1, 7, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.WHEAT_SEEDS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BEETROOT_SEEDS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PUMPKIN_SEEDS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.MELON_SEEDS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.ACACIA_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BIRCH_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.DARK_OAK_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.JUNGLE_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.OAK_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SPRUCE_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.CHERRY_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PALE_OAK_SAPLING, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.MANGROVE_PROPAGULE, 5, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.RED_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.WHITE_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BLUE_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PINK_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BLACK_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.GREEN_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.MAGENTA_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.YELLOW_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.GRAY_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PURPLE_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.LIME_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.ORANGE_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BROWN_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.CYAN_DYE, 1, 3, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.VINE, 1, 3, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PALE_HANGING_MOSS, 1, 3, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.BROWN_MUSHROOM, 1, 3, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.RED_MUSHROOM, 1, 3, 4, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.LILY_PAD, 1, 5, 2, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SMALL_DRIPLEAF, 1, 2, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.SAND, 1, 4, 8, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.POINTED_DRIPSTONE, 1, 2, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.ROOTED_DIRT, 1, 2, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.MOSS_BLOCK, 1, 2, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.PALE_MOSS_BLOCK, 1, 2, 5, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.WILDFLOWERS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.DRY_TALL_GRASS, 1, 1, 12, 1),
                                    new VillagerTrades.ItemsForEmeralds(Items.FIREFLY_BUSH, 3, 1, 12, 1)
                            },
                            5
                    )
            )
            .build();
    @Shadow
    @Mutable
    @Final
    public static Map<ResourceKey<VillagerProfession>, Int2ObjectMap<VillagerTrades.ItemListing[]>> EXPERIMENTAL_TRADES = Map.of(
            VillagerProfession.LIBRARIAN,
            Objects.requireNonNull(toIntMap(
                    ImmutableMap.<Integer, VillagerTrades.ItemListing[]>builder()
                            .put(
                                    1,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.PAPER, 16, 16, 2),
                                            commonBooks(1),
                                            new VillagerTrades.ItemsForEmeralds(Blocks.BOOKSHELF, 6, 1, 12, 1)
                                    }
                            )
                            .put(
                                    2,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.BOOK, 4, 12, 10),
                                            commonBooks(5),
                                            new VillagerTrades.ItemsForEmeralds(Items.LANTERN, 1, 2, 5)
                                    }
                            )
                            .put(
                                    3,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.INK_SAC, 6, 12, 20),
                                            commonBooks(10),
                                            new VillagerTrades.ItemsForEmeralds(Items.GLASS, 1, 4, 10)
                                    }
                            )
                            .put(
                                    4,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.WRITABLE_BOOK, 1, 12, 30),
                                            new VillagerTrades.ItemsForEmeralds(Items.CLOCK, 3, 1, 15),
                                            new VillagerTrades.ItemsForEmeralds(Items.COMPASS, 3, 1, 15)
                                    }
                            )
                            .put(
                                    5,
                                    new VillagerTrades.ItemListing[]{
                                            specialBooks(),
                                            new VillagerTrades.ItemsForEmeralds(Items.NAME_TAG, 16, 1, 30)
                                    }
                            )
                            .build()
            )),
            VillagerProfession.ARMORER,
            Objects.requireNonNull(toIntMap(
                    ImmutableMap.<Integer, VillagerTrades.ItemListing[]>builder()
                            .put(
                                    1,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.COAL, 4, 12, 2),
                                            new VillagerTrades.EmeraldForItems(Items.IRON_INGOT, 4, 12, 2)
                                    }
                            )
                            .put(
                                    2,
                                    new VillagerTrades.ItemListing[]{
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_BOOTS, 4, 1, 12, 5, 0.05F),
                                                    VillagerType.DESERT,
                                                    VillagerType.PLAINS,
                                                    VillagerType.SAVANNA,
                                                    VillagerType.SNOW,
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 4, 1, 12, 5, 0.05F), VillagerType.JUNGLE, VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_HELMET, 5, 1, 12, 5, 0.05F),
                                                    VillagerType.DESERT,
                                                    VillagerType.PLAINS,
                                                    VillagerType.SAVANNA,
                                                    VillagerType.SNOW,
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_HELMET, 5, 1, 12, 5, 0.05F), VillagerType.JUNGLE, VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_LEGGINGS, 7, 1, 12, 5, 0.05F),
                                                    VillagerType.DESERT,
                                                    VillagerType.PLAINS,
                                                    VillagerType.SAVANNA,
                                                    VillagerType.SNOW,
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_LEGGINGS, 7, 1, 12, 5, 0.05F), VillagerType.JUNGLE, VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_CHESTPLATE, 9, 1, 12, 5, 0.05F),
                                                    VillagerType.DESERT,
                                                    VillagerType.PLAINS,
                                                    VillagerType.SAVANNA,
                                                    VillagerType.SNOW,
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_CHESTPLATE, 9, 1, 12, 5, 0.05F), VillagerType.JUNGLE, VillagerType.SWAMP
                                            )
                                    }
                            )
                            .put(
                                    3,
                                    new VillagerTrades.ItemListing[]{
                                            new VillagerTrades.EmeraldForItems(Items.LAVA_BUCKET, 1, 12, 20),
                                            new VillagerTrades.ItemsForEmeralds(Items.SHIELD, 5, 1, 12, 10, 0.05F),
                                            new VillagerTrades.ItemsForEmeralds(Items.BELL, 36, 1, 12, 10, 0.2F)
                                    }
                            )
                            .put(
                                    4,
                                    new VillagerTrades.ItemListing[]{
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_BOOTS, 8, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_BOOTS_4),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_HELMET, 9, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_HELMET_4),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_LEGGINGS, 11, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_LEGGINGS_4),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_CHESTPLATE, 13, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_CHESTPLATE_4),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_BOOTS, 8, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_BOOTS_4),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_HELMET, 9, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_HELMET_4),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_LEGGINGS, 11, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_LEGGINGS_4),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_CHESTPLATE, 13, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_CHESTPLATE_4),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_BOOTS, 2, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_BOOTS_4),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_HELMET, 3, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_HELMET_4),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_LEGGINGS, 5, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_LEGGINGS_4),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_CHESTPLATE, 7, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_CHESTPLATE_4),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_BOOTS, 8, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SNOW_ARMORER_BOOTS_4),
                                                    VillagerType.SNOW
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.IRON_HELMET, 9, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SNOW_ARMORER_HELMET_4),
                                                    VillagerType.SNOW
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 8, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_BOOTS_4),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_HELMET, 9, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_HELMET_4),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_LEGGINGS, 11, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_LEGGINGS_4),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(
                                                            Items.CHAINMAIL_CHESTPLATE, 13, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_CHESTPLATE_4
                                                    ),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 8, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_BOOTS_4),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_HELMET, 9, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_HELMET_4),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_LEGGINGS, 11, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_LEGGINGS_4),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(
                                                            Items.CHAINMAIL_CHESTPLATE, 13, 1, 3, 15, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_CHESTPLATE_4
                                                    ),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(MMEItems.MITHRIL_BOOTS, 1, 4, MMEItems.MITHRIL_LEGGINGS, 1, 3, 15, 0.05F), VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(MMEItems.MITHRIL_LEGGINGS, 1, 4, MMEItems.MITHRIL_CHESTPLATE, 1, 3, 15, 0.05F), VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(MMEItems.MITHRIL_HELMET, 1, 4, MMEItems.MITHRIL_BOOTS, 1, 3, 15, 0.05F), VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(MMEItems.MITHRIL_CHESTPLATE, 1, 2, MMEItems.MITHRIL_HELMET, 1, 3, 15, 0.05F), VillagerType.TAIGA
                                            )
                                    }
                            )
                            .put(
                                    5,
                                    new VillagerTrades.ItemListing[]{
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 4), 16, new ItemStack(MMEItems.MITHRIL_CHESTPLATE, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_CHESTPLATE_5)
                                                    ),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 3), 16, new ItemStack(MMEItems.MITHRIL_LEGGINGS, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_DESERT_ARMORER_LEGGINGS_5)
                                                    ),
                                                    VillagerType.DESERT
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 3), 16, new ItemStack(MMEItems.MITHRIL_LEGGINGS, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_LEGGINGS_5)
                                                    ),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 2), 12, new ItemStack(MMEItems.MITHRIL_BOOTS, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_PLAINS_ARMORER_BOOTS_5)
                                                    ),
                                                    VillagerType.PLAINS
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 2), 6, new ItemStack(MMEItems.MITHRIL_HELMET, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_HELMET_5)
                                                    ),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 3), 8, new ItemStack(MMEItems.MITHRIL_CHESTPLATE, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_SAVANNA_ARMORER_CHESTPLATE_5)
                                                    ),
                                                    VillagerType.SAVANNA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 2), 12, new ItemStack(MMEItems.MITHRIL_BOOTS, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_SNOW_ARMORER_BOOTS_5)
                                                    ),
                                                    VillagerType.SNOW
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 3), 12, new ItemStack(MMEItems.MITHRIL_HELMET, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_SNOW_ARMORER_HELMET_5)
                                                    ),
                                                    VillagerType.SNOW
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_HELMET, 9, 1, 3, 30, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_HELMET_5),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 8, 1, 3, 30, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_JUNGLE_ARMORER_BOOTS_5),
                                                    VillagerType.JUNGLE
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_HELMET, 9, 1, 3, 30, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_HELMET_5),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 8, 1, 3, 30, 0.05F, TradeRebalanceEnchantmentProviders.TRADES_SWAMP_ARMORER_BOOTS_5),
                                                    VillagerType.SWAMP
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 4), 18, new ItemStack(MMEItems.MITHRIL_CHESTPLATE, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_TAIGA_ARMORER_CHESTPLATE_5)
                                                    ),
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.ItemsAndEmeraldsToItems(
                                                            new ItemCost(Items.DIAMOND, 3), 18, new ItemStack(MMEItems.MITHRIL_LEGGINGS, 1), 3, 30, 0.05F, Optional.of(TradeRebalanceEnchantmentProviders.TRADES_TAIGA_ARMORER_LEGGINGS_5)
                                                    ),
                                                    VillagerType.TAIGA
                                            ),
                                            VillagerTrades.TypeSpecificTrade.oneTradeInBiomes(
                                                    new VillagerTrades.EmeraldForItems(Items.IRON_INGOT, 4, 12, 30, 1),
                                                    VillagerType.DESERT,
                                                    VillagerType.JUNGLE,
                                                    VillagerType.PLAINS,
                                                    VillagerType.SAVANNA,
                                                    VillagerType.SNOW,
                                                    VillagerType.SWAMP,
                                                    VillagerType.TAIGA
                                            )
                                    }
                            )
                            .build()
            ))
    );
    @Shadow
    private static VillagerTrades.ItemListing specialBooks() {
        return null;
    }
    @Shadow
    private static VillagerTrades.ItemListing commonBooks(int i) {
        return null;
    }
    @Shadow
    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> immutableMap){
        return null;
    }
    @Shadow
    private static ItemStack potion(Holder<Potion> holder) {
        return null;
    }
    @Shadow
    private static ItemCost potionCost(Holder<Potion> holder) {
        return null;
    }
}
