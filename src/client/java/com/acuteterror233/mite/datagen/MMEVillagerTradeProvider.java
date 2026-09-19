package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.item.MMEItems;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.component.predicates.VillagerTypePredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.PotionTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MME villager trade definition. Each trade is declared with a static {@code ResourceKey},
 * registered to the villager_trade registry via {@link #register} in {@link #bootstrap},
 * and output to {@code data/mme/villager_trade/}.
 * Content sourced from villager-trades-EDIT.md.
 */
public class MMEVillagerTradeProvider {
	public static final ResourceKey<VillagerTrade> FARMER_1_BEETROOT_EMERALD = resourceKey("farmer/1/beetroot_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_1_BLUE_BERRY_EMERALD = resourceKey("farmer/1/blue_berry_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_1_CARROT_EMERALD = resourceKey("farmer/1/carrot_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_1_EMERALD_BREAD = resourceKey("farmer/1/emerald_bread");
	public static final ResourceKey<VillagerTrade> FARMER_1_POTATO_EMERALD = resourceKey("farmer/1/potato_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_1_WHEAT_EMERALD = resourceKey("farmer/1/wheat_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_2_EMERALD_APPLE = resourceKey("farmer/2/emerald_apple");
	public static final ResourceKey<VillagerTrade> FARMER_2_EMERALD_PUMPKIN_PIE = resourceKey("farmer/2/emerald_pumpkin_pie");
	public static final ResourceKey<VillagerTrade> FARMER_2_PUMPKIN_EMERALD = resourceKey("farmer/2/pumpkin_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_3_EMERALD_COOKIE = resourceKey("farmer/3/emerald_cookie");
	public static final ResourceKey<VillagerTrade> FARMER_3_MELON_EMERALD = resourceKey("farmer/3/melon_emerald");
	public static final ResourceKey<VillagerTrade> FARMER_4_EMERALD_CAKE = resourceKey("farmer/4/emerald_cake");
	public static final ResourceKey<VillagerTrade> FARMER_4_EMERALD_SUSPICIOUS_STEW = resourceKey("farmer/4/emerald_suspicious_stew");
	public static final ResourceKey<VillagerTrade> FARMER_5_EMERALD_GLISTENING_MELON_SLICE = resourceKey("farmer/5/emerald_glistening_melon_slice");
	public static final ResourceKey<VillagerTrade> FARMER_5_EMERALD_GOLDEN_CARROT = resourceKey("farmer/5/emerald_golden_carrot");
	public static final ResourceKey<VillagerTrade> FISHERMAN_1_COAL_EMERALD = resourceKey("fisherman/1/coal_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_COD_COPPER_BUCKET = resourceKey("fisherman/1/emerald_cod_copper_bucket");
	public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_COD_SILVER_BUCKET = resourceKey("fisherman/1/emerald_cod_silver_bucket");
	public static final ResourceKey<VillagerTrade> FISHERMAN_1_STRING_EMERALD = resourceKey("fisherman/1/string_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_2_COD_12_EMERALD = resourceKey("fisherman/2/cod_12_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_2_SALMON_12_EMERALD = resourceKey("fisherman/2/salmon_12_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_2_EMERALD_CAMPFIRE = resourceKey("fisherman/2/emerald_campfire");
	public static final ResourceKey<VillagerTrade> FISHERMAN_3_EMERALD_ENCHANTED_FISHING_ROD = resourceKey("fisherman/3/emerald_enchanted_fishing_rod");
	public static final ResourceKey<VillagerTrade> FISHERMAN_3_TROPICAL_FISH_12_EMERALD = resourceKey("fisherman/3/tropical_fish_12_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_3_SALMON_AND_EMERALD_COOKED_SALMON = resourceKey("fisherman/3/salmon_and_emerald_cooked_salmon");
	public static final ResourceKey<VillagerTrade> FISHERMAN_3_COD_AND_EMERALD_COOKED_COD = resourceKey("fisherman/3/cod_and_emerald_cooked_cod");
	public static final ResourceKey<VillagerTrade> FISHERMAN_4_EMERALD_BUCKET = resourceKey("fisherman/4/emerald_bucket");
	public static final ResourceKey<VillagerTrade> FISHERMAN_4_EMERALD_MITHRIL_BUCKET = resourceKey("fisherman/4/emerald_mithril_bucket");
	public static final ResourceKey<VillagerTrade> FISHERMAN_4_PUFFERFISH_EMERALD = resourceKey("fisherman/4/pufferfish_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_ACACIA_BOAT_EMERALD = resourceKey("fisherman/5/acacia_boat_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_DARK_OAK_BOAT_EMERALD = resourceKey("fisherman/5/dark_oak_boat_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_EMERALD_SUPERIOR_ENCHANTED_FISHING_ROD = resourceKey("fisherman/5/emerald_superior_enchanted_fishing_rod");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_JUNGLE_BOAT_EMERALD = resourceKey("fisherman/5/jungle_boat_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_OAK_BOAT_EMERALD = resourceKey("fisherman/5/oak_boat_emerald");
	public static final ResourceKey<VillagerTrade> FISHERMAN_5_SPRUCE_BOAT_EMERALD = resourceKey("fisherman/5/spruce_boat_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_BLACK_WOOL_EMERALD = resourceKey("shepherd/1/black_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_BLUE_WOOL_EMERALD = resourceKey("shepherd/1/blue_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_BROWN_WOOL_EMERALD = resourceKey("shepherd/1/brown_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_CYAN_WOOL_EMERALD = resourceKey("shepherd/1/cyan_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_EMERALD_COPPER_SHEARS = resourceKey("shepherd/1/emerald_copper_shears");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_EMERALD_SILVER_SHEARS = resourceKey("shepherd/1/emerald_silver_shears");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_GRAY_WOOL_EMERALD = resourceKey("shepherd/1/gray_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_GREEN_WOOL_EMERALD = resourceKey("shepherd/1/green_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_LIGHT_BLUE_WOOL_EMERALD = resourceKey("shepherd/1/light_blue_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_LIGHT_GRAY_WOOL_EMERALD = resourceKey("shepherd/1/light_gray_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_LIME_WOOL_EMERALD = resourceKey("shepherd/1/lime_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_MAGENTA_WOOL_EMERALD = resourceKey("shepherd/1/magenta_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_ORANGE_WOOL_EMERALD = resourceKey("shepherd/1/orange_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_PINK_WOOL_EMERALD = resourceKey("shepherd/1/pink_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_PURPLE_WOOL_EMERALD = resourceKey("shepherd/1/purple_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_RED_WOOL_EMERALD = resourceKey("shepherd/1/red_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_WHITE_WOOL_EMERALD = resourceKey("shepherd/1/white_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_1_YELLOW_WOOL_EMERALD = resourceKey("shepherd/1/yellow_wool_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_BLUE_DYE_EMERALD = resourceKey("shepherd/2/blue_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_BLACK_CARPET = resourceKey("shepherd/2/emerald_black_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_BLUE_CARPET = resourceKey("shepherd/2/emerald_blue_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_BROWN_CARPET = resourceKey("shepherd/2/emerald_brown_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_CYAN_CARPET = resourceKey("shepherd/2/emerald_cyan_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_GRAY_CARPET = resourceKey("shepherd/2/emerald_gray_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_GREEN_CARPET = resourceKey("shepherd/2/emerald_green_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_LIGHT_BLUE_CARPET = resourceKey("shepherd/2/emerald_light_blue_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_LIGHT_GRAY_CARPET = resourceKey("shepherd/2/emerald_light_gray_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_LIME_CARPET = resourceKey("shepherd/2/emerald_lime_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_MAGENTA_CARPET = resourceKey("shepherd/2/emerald_magenta_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_ORANGE_CARPET = resourceKey("shepherd/2/emerald_orange_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_PINK_CARPET = resourceKey("shepherd/2/emerald_pink_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_PURPLE_CARPET = resourceKey("shepherd/2/emerald_purple_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_RED_CARPET = resourceKey("shepherd/2/emerald_red_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_WHITE_CARPET = resourceKey("shepherd/2/emerald_white_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_EMERALD_YELLOW_CARPET = resourceKey("shepherd/2/emerald_yellow_carpet");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_GREEN_DYE_EMERALD = resourceKey("shepherd/2/green_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_RED_DYE_EMERALD = resourceKey("shepherd/2/red_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_2_YELLOW_DYE_EMERALD = resourceKey("shepherd/2/yellow_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_SHEARS = resourceKey("shepherd/3/emerald_shears");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_BLACK_BED = resourceKey("shepherd/3/emerald_black_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_BLUE_BED = resourceKey("shepherd/3/emerald_blue_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_BROWN_BED = resourceKey("shepherd/3/emerald_brown_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_CYAN_BED = resourceKey("shepherd/3/emerald_cyan_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_GRAY_BED = resourceKey("shepherd/3/emerald_gray_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_GREEN_BED = resourceKey("shepherd/3/emerald_green_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_LIGHT_BLUE_BED = resourceKey("shepherd/3/emerald_light_blue_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_LIGHT_GRAY_BED = resourceKey("shepherd/3/emerald_light_gray_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_LIME_BED = resourceKey("shepherd/3/emerald_lime_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_MAGENTA_BED = resourceKey("shepherd/3/emerald_magenta_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_ORANGE_BED = resourceKey("shepherd/3/emerald_orange_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_PINK_BED = resourceKey("shepherd/3/emerald_pink_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_PURPLE_BED = resourceKey("shepherd/3/emerald_purple_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_RED_BED = resourceKey("shepherd/3/emerald_red_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_WHITE_BED = resourceKey("shepherd/3/emerald_white_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_EMERALD_YELLOW_BED = resourceKey("shepherd/3/emerald_yellow_bed");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_BLACK_DYE_EMERALD = resourceKey("shepherd/3/black_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_BROWN_DYE_EMERALD = resourceKey("shepherd/3/brown_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_CYAN_DYE_EMERALD = resourceKey("shepherd/3/cyan_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_GRAY_DYE_EMERALD = resourceKey("shepherd/3/gray_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_LIGHT_BLUE_DYE_EMERALD = resourceKey("shepherd/3/light_blue_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_3_LIME_DYE_EMERALD = resourceKey("shepherd/3/lime_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_BLACK_BANNER = resourceKey("shepherd/4/emerald_black_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_BLUE_BANNER = resourceKey("shepherd/4/emerald_blue_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_BROWN_BANNER = resourceKey("shepherd/4/emerald_brown_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_CYAN_BANNER = resourceKey("shepherd/4/emerald_cyan_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_GRAY_BANNER = resourceKey("shepherd/4/emerald_gray_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_GREEN_BANNER = resourceKey("shepherd/4/emerald_green_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_LIGHT_BLUE_BANNER = resourceKey("shepherd/4/emerald_light_blue_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_LIGHT_GRAY_BANNER = resourceKey("shepherd/4/emerald_light_gray_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_LIME_BANNER = resourceKey("shepherd/4/emerald_lime_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_MAGENTA_BANNER = resourceKey("shepherd/4/emerald_magenta_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_ORANGE_BANNER = resourceKey("shepherd/4/emerald_orange_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_PINK_BANNER = resourceKey("shepherd/4/emerald_pink_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_PURPLE_BANNER = resourceKey("shepherd/4/emerald_purple_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_RED_BANNER = resourceKey("shepherd/4/emerald_red_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_WHITE_BANNER = resourceKey("shepherd/4/emerald_white_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_EMERALD_YELLOW_BANNER = resourceKey("shepherd/4/emerald_yellow_banner");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_LIGHT_GRAY_DYE_EMERALD = resourceKey("shepherd/4/light_gray_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_MAGENTA_DYE_EMERALD = resourceKey("shepherd/4/magenta_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_ORANGE_DYE_EMERALD = resourceKey("shepherd/4/orange_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_PINK_DYE_EMERALD = resourceKey("shepherd/4/pink_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_PURPLE_DYE_EMERALD = resourceKey("shepherd/4/purple_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_4_WHITE_DYE_EMERALD = resourceKey("shepherd/4/white_dye_emerald");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_BLACK_WOOL = resourceKey("shepherd/5/emerald_black_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_BLUE_WOOL = resourceKey("shepherd/5/emerald_blue_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_BROWN_WOOL = resourceKey("shepherd/5/emerald_brown_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_CYAN_WOOL = resourceKey("shepherd/5/emerald_cyan_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_GRAY_WOOL = resourceKey("shepherd/5/emerald_gray_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_GREEN_WOOL = resourceKey("shepherd/5/emerald_green_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_LIME_WOOL = resourceKey("shepherd/5/emerald_lime_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_MAGENTA_WOOL = resourceKey("shepherd/5/emerald_magenta_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_ORANGE_WOOL = resourceKey("shepherd/5/emerald_orange_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_PINK_WOOL = resourceKey("shepherd/5/emerald_pink_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_PURPLE_WOOL = resourceKey("shepherd/5/emerald_purple_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_RED_WOOL = resourceKey("shepherd/5/emerald_red_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_WHITE_WOOL = resourceKey("shepherd/5/emerald_white_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_YELLOW_WOOL = resourceKey("shepherd/5/emerald_yellow_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_LIGHT_BLUE_WOOL = resourceKey("shepherd/5/emerald_light_blue_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_LIGHT_GRAY_WOOL = resourceKey("shepherd/5/emerald_light_gray_wool");
	public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_PAINTING = resourceKey("shepherd/5/emerald_painting");
	public static final ResourceKey<VillagerTrade> FLETCHER_1_EMERALD_ARROW = resourceKey("fletcher/1/emerald_arrow");
	public static final ResourceKey<VillagerTrade> FLETCHER_1_GRAVEL_AND_EMERALD_FLINT = resourceKey("fletcher/1/gravel_and_emerald_flint");
	public static final ResourceKey<VillagerTrade> FLETCHER_1_STICK_EMERALD = resourceKey("fletcher/1/stick_emerald");
	public static final ResourceKey<VillagerTrade> FLETCHER_2_EMERALD_BOW = resourceKey("fletcher/2/emerald_bow");
	public static final ResourceKey<VillagerTrade> FLETCHER_2_FLINT_EMERALD = resourceKey("fletcher/2/flint_emerald");
	public static final ResourceKey<VillagerTrade> FLETCHER_3_EMERALD_CROSSBOW = resourceKey("fletcher/3/emerald_crossbow");
	public static final ResourceKey<VillagerTrade> FLETCHER_3_STRING_EMERALD = resourceKey("fletcher/3/string_emerald");
	public static final ResourceKey<VillagerTrade> FLETCHER_4_EMERALD_ENCHANTED_BOW = resourceKey("fletcher/4/emerald_enchanted_bow");
	public static final ResourceKey<VillagerTrade> FLETCHER_4_FEATHER_EMERALD = resourceKey("fletcher/4/feather_emerald");
	public static final ResourceKey<VillagerTrade> FLETCHER_5_ARROW_AND_EMERALD_TIPPED_ARROW = resourceKey("fletcher/5/arrow_and_emerald_tipped_arrow");
	public static final ResourceKey<VillagerTrade> FLETCHER_5_EMERALD_ENCHANTED_CROSSBOW = resourceKey("fletcher/5/emerald_enchanted_crossbow");
	public static final ResourceKey<VillagerTrade> FLETCHER_5_TRIPWIRE_HOOK_EMERALD = resourceKey("fletcher/5/tripwire_hook_emerald");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_1_EMERALD_AND_BOOK_ENCHANTED_BOOK = resourceKey("librarian/1/emerald_and_book_enchanted_book");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_1_EMERALD_BOOKSHELF = resourceKey("librarian/1/emerald_bookshelf");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_1_PAPER_EMERALD = resourceKey("librarian/1/paper_emerald");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_2_BOOK_EMERALD = resourceKey("librarian/2/book_emerald");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_2_EMERALD_AND_BOOK_ENCHANTED_BOOK = resourceKey("librarian/2/emerald_and_book_enchanted_book");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_2_EMERALD_LANTERN = resourceKey("librarian/2/emerald_lantern");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_3_EMERALD_AND_BOOK_ENCHANTED_BOOK = resourceKey("librarian/3/emerald_and_book_enchanted_book");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_3_EMERALD_GLASS = resourceKey("librarian/3/emerald_glass");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_3_INK_SAC_EMERALD = resourceKey("librarian/3/ink_sac_emerald");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_4_EMERALD_BOOK_AND_ENCHANTED_BOOK = resourceKey("librarian/4/emerald_book_and_enchanted_book");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_4_EMERALD_CLOCK = resourceKey("librarian/4/emerald_clock");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_4_EMERALD_COMPASS = resourceKey("librarian/4/emerald_compass");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_4_WRITABLE_BOOK_EMERALD = resourceKey("librarian/4/writable_book_emerald");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_BLACK_CANDLE = resourceKey("librarian/5/emerald_black_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_BLUE_CANDLE = resourceKey("librarian/5/emerald_blue_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_BROWN_CANDLE = resourceKey("librarian/5/emerald_brown_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_CYAN_CANDLE = resourceKey("librarian/5/emerald_cyan_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_GRAY_CANDLE = resourceKey("librarian/5/emerald_gray_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_GREEN_CANDLE = resourceKey("librarian/5/emerald_green_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_LIGHT_BLUE_CANDLE = resourceKey("librarian/5/emerald_light_blue_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_LIGHT_GRAY_CANDLE = resourceKey("librarian/5/emerald_light_gray_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_LIME_CANDLE = resourceKey("librarian/5/emerald_lime_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_MAGENTA_CANDLE = resourceKey("librarian/5/emerald_magenta_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_ORANGE_CANDLE = resourceKey("librarian/5/emerald_orange_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_PINK_CANDLE = resourceKey("librarian/5/emerald_pink_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_PURPLE_CANDLE = resourceKey("librarian/5/emerald_purple_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_RED_CANDLE = resourceKey("librarian/5/emerald_red_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_WHITE_CANDLE = resourceKey("librarian/5/emerald_white_candle");
	public static final ResourceKey<VillagerTrade> LIBRARIAN_5_EMERALD_YELLOW_CANDLE = resourceKey("librarian/5/emerald_yellow_candle");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_1_EMERALD_MAP = resourceKey("cartographer/1/emerald_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_1_PAPER_EMERALD = resourceKey("cartographer/1/paper_emerald");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_EXPLORER_JUNGLE_MAP = resourceKey("cartographer/2/emerald_and_compass_explorer_jungle_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_EXPLORER_SWAMP_MAP = resourceKey("cartographer/2/emerald_and_compass_explorer_swamp_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_DESERT_MAP = resourceKey("cartographer/2/emerald_and_compass_village_desert_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_PLAINS_MAP = resourceKey("cartographer/2/emerald_and_compass_village_plains_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_SAVANNA_MAP = resourceKey("cartographer/2/emerald_and_compass_village_savanna_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_SNOWY_MAP = resourceKey("cartographer/2/emerald_and_compass_village_snowy_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_TAIGA_MAP = resourceKey("cartographer/2/emerald_and_compass_village_taiga_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_2_GLASS_PANE_EMERALD = resourceKey("cartographer/2/glass_pane_emerald");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_3_COMPASS_EMERALD = resourceKey("cartographer/3/compass_emerald");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_3_EMERALD_AND_COMPASS_OCEAN_EXPLORER_MAP = resourceKey("cartographer/3/emerald_and_compass_ocean_explorer_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_3_EMERALD_AND_COMPASS_TRIAL_CHAMBER_MAP = resourceKey("cartographer/3/emerald_and_compass_trial_chamber_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_BLACK_BANNER = resourceKey("cartographer/4/emerald_black_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_BLUE_BANNER = resourceKey("cartographer/4/emerald_blue_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_BROWN_BANNER = resourceKey("cartographer/4/emerald_brown_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_CYAN_BANNER = resourceKey("cartographer/4/emerald_cyan_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_GRAY_BANNER = resourceKey("cartographer/4/emerald_gray_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_GREEN_BANNER = resourceKey("cartographer/4/emerald_green_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_ITEM_FRAME = resourceKey("cartographer/4/emerald_item_frame");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_LIGHT_BLUE_BANNER = resourceKey("cartographer/4/emerald_light_blue_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_LIME_BANNER = resourceKey("cartographer/4/emerald_lime_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_MAGENTA_BANNER = resourceKey("cartographer/4/emerald_magenta_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_ORANGE_BANNER = resourceKey("cartographer/4/emerald_orange_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_PINK_BANNER = resourceKey("cartographer/4/emerald_pink_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_PURPLE_BANNER = resourceKey("cartographer/4/emerald_purple_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_RED_BANNER = resourceKey("cartographer/4/emerald_red_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_WHITE_BANNER = resourceKey("cartographer/4/emerald_white_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_4_EMERALD_YELLOW_BANNER = resourceKey("cartographer/4/emerald_yellow_banner");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_5_EMERALD_AND_COMPASS_WOODLAND_MANSION_MAP = resourceKey("cartographer/5/emerald_and_compass_woodland_mansion_map");
	public static final ResourceKey<VillagerTrade> CARTOGRAPHER_5_EMERALD_GLOBE_BANNER_PATTERN = resourceKey("cartographer/5/emerald_globe_banner_pattern");
	public static final ResourceKey<VillagerTrade> CLERIC_1_EMERALD_REDSTONE = resourceKey("cleric/1/emerald_redstone");
	public static final ResourceKey<VillagerTrade> CLERIC_1_ROTTEN_FLESH_EMERALD = resourceKey("cleric/1/rotten_flesh_emerald");
	public static final ResourceKey<VillagerTrade> CLERIC_2_EMERALD_LAPIS_LAZULI = resourceKey("cleric/2/emerald_lapis_lazuli");
	public static final ResourceKey<VillagerTrade> CLERIC_2_GOLD_INGOT_EMERALD = resourceKey("cleric/2/gold_ingot_emerald");
	public static final ResourceKey<VillagerTrade> CLERIC_3_EMERALD_GLOWSTONE = resourceKey("cleric/3/emerald_glowstone");
	public static final ResourceKey<VillagerTrade> CLERIC_3_RABBIT_FOOT_EMERALD = resourceKey("cleric/3/rabbit_foot_emerald");
	public static final ResourceKey<VillagerTrade> CLERIC_4_EMERALD_ENDER_PEARL = resourceKey("cleric/4/emerald_ender_pearl");
	public static final ResourceKey<VillagerTrade> CLERIC_4_GLASS_BOTTLE_EMERALD = resourceKey("cleric/4/glass_bottle_emerald");
	public static final ResourceKey<VillagerTrade> CLERIC_4_TURTLE_SCUTE_EMERALD = resourceKey("cleric/4/turtle_scute_emerald");
	public static final ResourceKey<VillagerTrade> CLERIC_5_EMERALD_EXPERIENCE_BOTTLE = resourceKey("cleric/5/emerald_experience_bottle");
	public static final ResourceKey<VillagerTrade> CLERIC_5_NETHER_WART_EMERALD = resourceKey("cleric/5/nether_wart_emerald");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_IRON_BOOTS = resourceKey("armorer/1/emerald_iron_boots");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_IRON_CHESTPLATE = resourceKey("armorer/1/emerald_iron_chestplate");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_IRON_HELMET = resourceKey("armorer/1/emerald_iron_helmet");
	public static final ResourceKey<VillagerTrade> ARMORER_1_EMERALD_IRON_LEGGINGS = resourceKey("armorer/1/emerald_iron_leggings");
	public static final ResourceKey<VillagerTrade> ARMORER_2_EMERALD_MITHRIL_CHAINMAIL_BOOTS = resourceKey("armorer/2/emerald_mithril_chainmail_boots");
	public static final ResourceKey<VillagerTrade> ARMORER_2_EMERALD_MITHRIL_CHAINMAIL_LEGGINGS = resourceKey("armorer/2/emerald_mithril_chainmail_leggings");
	public static final ResourceKey<VillagerTrade> ARMORER_3_DIAMOND_EMERALD = resourceKey("armorer/3/diamond_emerald");
	public static final ResourceKey<VillagerTrade> ARMORER_3_EMERALD_MITHRIL_CHAINMAIL_CHESTPLATE = resourceKey("armorer/3/emerald_mithril_chainmail_chestplate");
	public static final ResourceKey<VillagerTrade> ARMORER_3_EMERALD_MITHRIL_CHAINMAIL_HELMET = resourceKey("armorer/3/emerald_mithril_chainmail_helmet");
	public static final ResourceKey<VillagerTrade> ARMORER_3_EMERALD_SHIELD = resourceKey("armorer/3/emerald_shield");
	public static final ResourceKey<VillagerTrade> ARMORER_4_DIAMOND_ENCHANTED_MITHRIL_BOOTS = resourceKey("armorer/4/diamond_enchanted_mithril_boots");
	public static final ResourceKey<VillagerTrade> ARMORER_4_DIAMOND_ENCHANTED_MITHRIL_LEGGINGS = resourceKey("armorer/4/diamond_enchanted_mithril_leggings");
	public static final ResourceKey<VillagerTrade> ARMORER_5_DIAMOND_ENCHANTED_MITHRIL_CHESTPLATE = resourceKey("armorer/5/diamond_enchanted_mithril_chestplate");
	public static final ResourceKey<VillagerTrade> ARMORER_5_DIAMOND_ENCHANTED_MITHRIL_HELMET = resourceKey("armorer/5/diamond_enchanted_mithril_helmet");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_1_COAL_EMERALD = resourceKey("weaponsmith/1/coal_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_1_EMERALD_IRON_SWORD = resourceKey("weaponsmith/1/emerald_iron_sword");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_1_EMERALD_IRON_DAGGER = resourceKey("weaponsmith/1/emerald_iron_dagger");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_2_COPPER_EMERALD = resourceKey("weaponsmith/2/copper_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_2_SILVER_EMERALD = resourceKey("weaponsmith/2/silver_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_2_EMERALD_BELL = resourceKey("weaponsmith/2/emerald_bell");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_IRON_INGOT_EMERALD = resourceKey("weaponsmith/3/iron_ingot_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_FLINT_EMERALD = resourceKey("weaponsmith/3/flint_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_4_DIAMOND_EMERALD = resourceKey("weaponsmith/4/diamond_emerald");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_5_DIAMOND_MITHRIL_SWORD = resourceKey("weaponsmith/5/diamond_mithril_sword");
	public static final ResourceKey<VillagerTrade> WEAPONSMITH_5_DIAMOND_MITHRIL_DAGGER = resourceKey("weaponsmith/5/diamond_mithril_dagger");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_COAL_EMERALD = resourceKey("toolsmith/1/coal_emerald");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_AXE = resourceKey("toolsmith/1/emerald_iron_axe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_BATTLE_AXE = resourceKey("toolsmith/1/emerald_iron_battle_axe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_HATCHET = resourceKey("toolsmith/1/emerald_iron_hatchet");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_HOE = resourceKey("toolsmith/1/emerald_iron_hoe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_MATTOCK = resourceKey("toolsmith/1/emerald_iron_mattock");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_PICKAXE = resourceKey("toolsmith/1/emerald_iron_pickaxe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_SCYTHE = resourceKey("toolsmith/1/emerald_iron_scythe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_SHOVEL = resourceKey("toolsmith/1/emerald_iron_shovel");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_1_EMERALD_IRON_WAR_HAMMER = resourceKey("toolsmith/1/emerald_iron_war_hammer");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_2_IRON_INGOT_EMERALD = resourceKey("toolsmith/2/iron_ingot_emerald");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_2_EMERALD_BELL = resourceKey("toolsmith/2/emerald_bell");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_3_DIAMOND_MITHRIL_AXE = resourceKey("toolsmith/3/diamond_mithril_axe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_3_DIAMOND_MITHRIL_PICKAXE = resourceKey("toolsmith/3/diamond_mithril_pickaxe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_3_DIAMOND_MITHRIL_SHOVEL = resourceKey("toolsmith/3/diamond_mithril_shovel");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_3_DIAMOND_MITHRIL_HOE = resourceKey("toolsmith/3/diamond_mithril_hoe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_3_FLINT_EMERALD = resourceKey("toolsmith/3/flint_emerald");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_4_DIAMOND_EMERALD = resourceKey("toolsmith/4/diamond_emerald");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_4_DIAMOND_ENCHANTED_MITHRIL_HATCHET = resourceKey("toolsmith/4/diamond_enchanted_mithril_hatchet");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_4_DIAMOND_ENCHANTED_MITHRIL_MATTOCK = resourceKey("toolsmith/4/diamond_enchanted_mithril_mattock");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_5_DIAMOND_MITHRIL_INGOT = resourceKey("toolsmith/5/diamond_mithril_ingot");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_BATTLE_AXE = resourceKey("toolsmith/5/diamond_enchanted_mithril_battle_axe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_SCYTHE = resourceKey("toolsmith/5/diamond_enchanted_mithril_scythe");
	public static final ResourceKey<VillagerTrade> TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_WAR_HAMMER = resourceKey("toolsmith/5/diamond_enchanted_mithril_war_hammer");
	public static final ResourceKey<VillagerTrade> BUTCHER_1_CHICKEN_EMERALD = resourceKey("butcher/1/chicken_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_1_EMERALD_RABBIT_STEW = resourceKey("butcher/1/emerald_rabbit_stew");
	public static final ResourceKey<VillagerTrade> BUTCHER_1_PORKCHOP_EMERALD = resourceKey("butcher/1/porkchop_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_1_RABBIT_EMERALD = resourceKey("butcher/1/rabbit_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_2_CHARCOAL_EMERALD = resourceKey("butcher/2/charcoal_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_2_EMERALD_COOKED_CHICKEN = resourceKey("butcher/2/emerald_cooked_chicken");
	public static final ResourceKey<VillagerTrade> BUTCHER_2_EMERALD_COOKED_PORKCHOP = resourceKey("butcher/2/emerald_cooked_porkchop");
	public static final ResourceKey<VillagerTrade> BUTCHER_3_BEEF_EMERALD = resourceKey("butcher/3/beef_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_3_MUTTON_EMERALD = resourceKey("butcher/3/mutton_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_4_CHICKEN_AND_CHARCOAL_COOKED_CHICKEN = resourceKey("butcher/4/chicken_and_charcoal_cooked_chicken");
	public static final ResourceKey<VillagerTrade> BUTCHER_4_MUTTON_AND_CHARCOAL_COOKED_MUTTON = resourceKey("butcher/4/mutton_and_charcoal_cooked_mutton");
	public static final ResourceKey<VillagerTrade> BUTCHER_4_RABBIT_AND_CHARCOAL_COOKED_RABBIT = resourceKey("butcher/4/rabbit_and_charcoal_cooked_rabbit");
	public static final ResourceKey<VillagerTrade> BUTCHER_4_DRIED_KELP_BLOCK_EMERALD = resourceKey("butcher/4/dried_kelp_block_emerald");
	public static final ResourceKey<VillagerTrade> BUTCHER_5_PORKCHOP_AND_CHARCOAL_COOKED_PORKCHOP = resourceKey("butcher/5/porkchop_and_charcoal_cooked_porkchop");
	public static final ResourceKey<VillagerTrade> BUTCHER_5_BEEF_AND_CHARCOAL_COOKED_BEEF = resourceKey("butcher/5/beef_and_charcoal_cooked_beef");
	public static final ResourceKey<VillagerTrade> BUTCHER_5_SWEET_BERRIES_EMERALD = resourceKey("butcher/5/sweet_berries_emerald");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_1_EMERALD_DYED_LEATHER_CHESTPLATE = resourceKey("leatherworker/1/emerald_dyed_leather_chestplate");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_1_EMERALD_DYED_LEATHER_LEGGINGS = resourceKey("leatherworker/1/emerald_dyed_leather_leggings");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_1_LEATHER_EMERALD = resourceKey("leatherworker/1/leather_emerald");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_2_EMERALD_DYED_LEATHER_BOOTS = resourceKey("leatherworker/2/emerald_dyed_leather_boots");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_2_EMERALD_DYED_LEATHER_HELMET = resourceKey("leatherworker/2/emerald_dyed_leather_helmet");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_2_FLINT_EMERALD = resourceKey("leatherworker/2/flint_emerald");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_3_EMERALD_DYED_LEATHER_CHESTPLATE = resourceKey("leatherworker/3/emerald_dyed_leather_chestplate");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_3_RABBIT_HIDE_EMERALD = resourceKey("leatherworker/3/rabbit_hide_emerald");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_4_EMERALD_DYED_LEATHER_HORSE_ARMOR = resourceKey("leatherworker/4/emerald_dyed_leather_horse_armor");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_4_TURTLE_SCUTE_EMERALD = resourceKey("leatherworker/4/turtle_scute_emerald");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_5_EMERALD_DYED_LEATHER_HELMET = resourceKey("leatherworker/5/emerald_dyed_leather_helmet");
	public static final ResourceKey<VillagerTrade> LEATHERWORKER_5_EMERALD_SADDLE = resourceKey("leatherworker/5/emerald_saddle");
	public static final ResourceKey<VillagerTrade> MASON_1_CLAY_BALL_EMERALD = resourceKey("mason/1/clay_ball_emerald");
	public static final ResourceKey<VillagerTrade> MASON_1_EMERALD_BRICK = resourceKey("mason/1/emerald_brick");
	public static final ResourceKey<VillagerTrade> MASON_2_EMERALD_STONE_BRICKS = resourceKey("mason/2/emerald_stone_bricks");
	public static final ResourceKey<VillagerTrade> MASON_2_COBBLESTONE_EMERALD = resourceKey("mason/2/cobblestone_emerald");
	public static final ResourceKey<VillagerTrade> MASON_3_ANDESITE_AMETHYST_SHARD = resourceKey("mason/3/andesite_amethyst_shard");
	public static final ResourceKey<VillagerTrade> MASON_3_DIORITE_AMETHYST_SHARD = resourceKey("mason/3/diorite_amethyst_shard");
	public static final ResourceKey<VillagerTrade> MASON_3_EMERALD_DRIPSTONE_BLOCK = resourceKey("mason/3/emerald_dripstone_block");
	public static final ResourceKey<VillagerTrade> MASON_3_EMERALD_POLISHED_ANDESITE = resourceKey("mason/3/emerald_polished_andesite");
	public static final ResourceKey<VillagerTrade> MASON_3_EMERALD_POLISHED_DIORITE = resourceKey("mason/3/emerald_polished_diorite");
	public static final ResourceKey<VillagerTrade> MASON_3_EMERALD_POLISHED_GRANITE = resourceKey("mason/3/emerald_polished_granite");
	public static final ResourceKey<VillagerTrade> MASON_3_GRANITE_AMETHYST_SHARD = resourceKey("mason/3/granite_amethyst_shard");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BLACK_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_black_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BLACK_TERRACOTTA = resourceKey("mason/4/emerald_black_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BLUE_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_blue_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BLUE_TERRACOTTA = resourceKey("mason/4/emerald_blue_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BROWN_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_brown_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_BROWN_TERRACOTTA = resourceKey("mason/4/emerald_brown_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_CYAN_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_cyan_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_CYAN_TERRACOTTA = resourceKey("mason/4/emerald_cyan_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_GRAY_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_gray_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_GRAY_TERRACOTTA = resourceKey("mason/4/emerald_gray_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_GREEN_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_green_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_GREEN_TERRACOTTA = resourceKey("mason/4/emerald_green_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIGHT_BLUE_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_light_blue_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIGHT_BLUE_TERRACOTTA = resourceKey("mason/4/emerald_light_blue_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIGHT_GRAY_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_light_gray_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIGHT_GRAY_TERRACOTTA = resourceKey("mason/4/emerald_light_gray_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIME_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_lime_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_LIME_TERRACOTTA = resourceKey("mason/4/emerald_lime_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_MAGENTA_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_magenta_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_MAGENTA_TERRACOTTA = resourceKey("mason/4/emerald_magenta_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_ORANGE_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_orange_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_ORANGE_TERRACOTTA = resourceKey("mason/4/emerald_orange_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_PINK_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_pink_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_PINK_TERRACOTTA = resourceKey("mason/4/emerald_pink_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_PURPLE_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_purple_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_PURPLE_TERRACOTTA = resourceKey("mason/4/emerald_purple_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_RED_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_red_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_RED_TERRACOTTA = resourceKey("mason/4/emerald_red_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_WHITE_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_white_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_WHITE_TERRACOTTA = resourceKey("mason/4/emerald_white_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_YELLOW_GLAZED_TERRACOTTA = resourceKey("mason/4/emerald_yellow_glazed_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_YELLOW_TERRACOTTA = resourceKey("mason/4/emerald_yellow_terracotta");
	public static final ResourceKey<VillagerTrade> MASON_4_EMERALD_QUARTZ = resourceKey("mason/4/emerald_quartz");
	public static final ResourceKey<VillagerTrade> MASON_5_EMERALD_QUARTZ_BLOCK = resourceKey("mason/5/emerald_quartz_block");
	public static final ResourceKey<VillagerTrade> MASON_5_EMERALD_QUARTZ_PILLAR = resourceKey("mason/5/emerald_quartz_pillar");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_BAKED_POTATO_EMERALD = resourceKey("wandering_trader/baked_potato_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ACACIA_LOG = resourceKey("wandering_trader/emerald_acacia_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ACACIA_SAPLING = resourceKey("wandering_trader/emerald_acacia_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ALLIUM = resourceKey("wandering_trader/emerald_allium");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_AZURE_BLUET = resourceKey("wandering_trader/emerald_azure_bluet");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BEETROOT_SEEDS = resourceKey("wandering_trader/emerald_beetroot_seeds");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BIRCH_LOG = resourceKey("wandering_trader/emerald_birch_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BIRCH_SAPLING = resourceKey("wandering_trader/emerald_birch_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BLACK_DYE = resourceKey("wandering_trader/emerald_black_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BLUE_DYE = resourceKey("wandering_trader/emerald_blue_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BLUE_ICE = resourceKey("wandering_trader/emerald_blue_ice");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BLUE_ORCHID = resourceKey("wandering_trader/emerald_blue_orchid");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BRAIN_CORAL_BLOCK = resourceKey("wandering_trader/emerald_brain_coral_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BROWN_DYE = resourceKey("wandering_trader/emerald_brown_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BROWN_MUSHROOM = resourceKey("wandering_trader/emerald_brown_mushroom");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_BUBBLE_CORAL_BLOCK = resourceKey("wandering_trader/emerald_bubble_coral_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CACTUS = resourceKey("wandering_trader/emerald_cactus");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CHERRY_LOG = resourceKey("wandering_trader/emerald_cherry_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CHERRY_SAPLING = resourceKey("wandering_trader/emerald_cherry_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CORNFLOWER = resourceKey("wandering_trader/emerald_cornflower");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CYAN_DYE = resourceKey("wandering_trader/emerald_cyan_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_DANDELION = resourceKey("wandering_trader/emerald_dandelion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_DARK_OAK_LOG = resourceKey("wandering_trader/emerald_dark_oak_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_DARK_OAK_SAPLING = resourceKey("wandering_trader/emerald_dark_oak_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_DRY_TALL_GRASS = resourceKey("wandering_trader/emerald_dry_tall_grass");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ENCHANTED_IRON_PICKAXE = resourceKey("wandering_trader/emerald_enchanted_iron_pickaxe");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_FERN = resourceKey("wandering_trader/emerald_fern");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_FIRE_CORAL_BLOCK = resourceKey("wandering_trader/emerald_fire_coral_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_FIREFLY_BUSH = resourceKey("wandering_trader/emerald_firefly_bush");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_FISH_BUCKET = resourceKey("wandering_trader/emerald_fish_bucket");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GLOWSTONE = resourceKey("wandering_trader/emerald_glowstone");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GOLDEN_DANDELION = resourceKey("wandering_trader/emerald_golden_dandelion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GRAY_DYE = resourceKey("wandering_trader/emerald_gray_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GREEN_DYE = resourceKey("wandering_trader/emerald_green_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_GUNPOWDER = resourceKey("wandering_trader/emerald_gunpowder");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_HORN_CORAL_BLOCK = resourceKey("wandering_trader/emerald_horn_coral_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_JUNGLE_LOG = resourceKey("wandering_trader/emerald_jungle_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_JUNGLE_SAPLING = resourceKey("wandering_trader/emerald_jungle_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_KELP = resourceKey("wandering_trader/emerald_kelp");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LIGHT_BLUE_DYE = resourceKey("wandering_trader/emerald_light_blue_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LIGHT_GRAY_DYE = resourceKey("wandering_trader/emerald_light_gray_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LILY_OF_THE_VALLEY = resourceKey("wandering_trader/emerald_lily_of_the_valley");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LILY_PAD = resourceKey("wandering_trader/emerald_lily_pad");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LIME_DYE = resourceKey("wandering_trader/emerald_lime_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LONG_FIRE_RESISTANCE_POTION = resourceKey("wandering_trader/emerald_long_fire_resistance_potion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LONG_INVISIBILITY_POTION = resourceKey("wandering_trader/emerald_long_invisibility_potion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_LONG_NIGHT_VISION_POTION = resourceKey("wandering_trader/emerald_long_night_vision_potion");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MAGENTA_DYE = resourceKey("wandering_trader/emerald_magenta_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MANGROVE_LOG = resourceKey("wandering_trader/emerald_mangrove_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MANGROVE_PROPAGULE = resourceKey("wandering_trader/emerald_mangrove_propagule");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MELON_SEEDS = resourceKey("wandering_trader/emerald_melon_seeds");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MOSS_BLOCK = resourceKey("wandering_trader/emerald_moss_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_NAME_TAG = resourceKey("wandering_trader/emerald_name_tag");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_NAUTILUS_SHELL = resourceKey("wandering_trader/emerald_nautilus_shell");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_OAK_LOG = resourceKey("wandering_trader/emerald_oak_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_OAK_SAPLING = resourceKey("wandering_trader/emerald_oak_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_OPEN_EYEBLOSSOM = resourceKey("wandering_trader/emerald_open_eyeblossom");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ORANGE_DYE = resourceKey("wandering_trader/emerald_orange_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ORANGE_TULIP = resourceKey("wandering_trader/emerald_orange_tulip");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_OXEYE_DAISY = resourceKey("wandering_trader/emerald_oxeye_daisy");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PACKED_ICE = resourceKey("wandering_trader/emerald_packed_ice");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PALE_HANGING_MOSS = resourceKey("wandering_trader/emerald_pale_hanging_moss");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PALE_MOSS_BLOCK = resourceKey("wandering_trader/emerald_pale_moss_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PALE_OAK_LOG = resourceKey("wandering_trader/emerald_pale_oak_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PALE_OAK_SAPLING = resourceKey("wandering_trader/emerald_pale_oak_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PINK_DYE = resourceKey("wandering_trader/emerald_pink_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PINK_TULIP = resourceKey("wandering_trader/emerald_pink_tulip");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PODZOL = resourceKey("wandering_trader/emerald_podzol");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_POINTED_DRIPSTONE = resourceKey("wandering_trader/emerald_pointed_dripstone");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_POPPY = resourceKey("wandering_trader/emerald_poppy");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PUFFERFISH_BUCKET = resourceKey("wandering_trader/emerald_pufferfish_bucket");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PUMPKIN = resourceKey("wandering_trader/emerald_pumpkin");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PUMPKIN_SEEDS = resourceKey("wandering_trader/emerald_pumpkin_seeds");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_PURPLE_DYE = resourceKey("wandering_trader/emerald_purple_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_DYE = resourceKey("wandering_trader/emerald_red_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_MUSHROOM = resourceKey("wandering_trader/emerald_red_mushroom");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_SAND = resourceKey("wandering_trader/emerald_red_sand");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_RED_TULIP = resourceKey("wandering_trader/emerald_red_tulip");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_ROOTED_DIRT = resourceKey("wandering_trader/emerald_rooted_dirt");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SAND = resourceKey("wandering_trader/emerald_sand");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SEA_PICKLE = resourceKey("wandering_trader/emerald_sea_pickle");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SLIME_BALL = resourceKey("wandering_trader/emerald_slime_ball");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SMALL_DRIPLEAF = resourceKey("wandering_trader/emerald_small_dripleaf");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SPRUCE_LOG = resourceKey("wandering_trader/emerald_spruce_log");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SPRUCE_SAPLING = resourceKey("wandering_trader/emerald_spruce_sapling");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SUGAR_CANE = resourceKey("wandering_trader/emerald_sugar_cane");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_TUBE_CORAL_BLOCK = resourceKey("wandering_trader/emerald_tube_coral_block");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_VINE = resourceKey("wandering_trader/emerald_vine");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WHEAT_SEEDS = resourceKey("wandering_trader/emerald_wheat_seeds");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WHITE_DYE = resourceKey("wandering_trader/emerald_white_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WHITE_TULIP = resourceKey("wandering_trader/emerald_white_tulip");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_WILDFLOWERS = resourceKey("wandering_trader/emerald_wildflowers");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_YELLOW_DYE = resourceKey("wandering_trader/emerald_yellow_dye");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_FERMENTED_SPIDER_EYE_EMERALD = resourceKey("wandering_trader/fermented_spider_eye_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_HAY_BLOCK_EMERALD = resourceKey("wandering_trader/hay_block_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_MILK_BUCKET_EMERALD = resourceKey("wandering_trader/milk_bucket_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_MILK_COPPER_BUCKET_EMERALD = resourceKey("wandering_trader/milk_copper_bucket_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_MILK_SILVER_BUCKET_EMERALD = resourceKey("wandering_trader/milk_silver_bucket_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_WATER_BOTTLE_EMERALD = resourceKey("wandering_trader/water_bottle_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_WATER_BUCKET_EMERALD = resourceKey("wandering_trader/water_bucket_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_WATER_COPPER_BUCKET_EMERALD = resourceKey("wandering_trader/water_copper_bucket_emerald");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_WATER_SILVER_BUCKET_EMERALD = resourceKey("wandering_trader/water_silver_bucket_emerald");

	private record TradeLookups(
		HolderGetter<Item> items,
		Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment,
		Optional<HolderSet<Enchantment>> enchantmentsForBooks,
		Optional<HolderSet<Enchantment>> doubleTradePrice,
		Optional<HolderSet<Potion>> potionsForTippedArrows,
		HolderGetter<VillagerType> villagerVariants
	) {
		static TradeLookups of(final BootstrapContext<VillagerTrade> context) {
			HolderGetter<Item> items = context.lookup(Registries.ITEM);
			Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment = context.lookup(Registries.ENCHANTMENT)
				.get(EnchantmentTags.ON_TRADED_EQUIPMENT)
				.map(named -> (HolderSet<Enchantment>)named);
			Optional<HolderSet<Enchantment>> enchantmentsForBooks = context.lookup(Registries.ENCHANTMENT)
				.get(EnchantmentTags.TRADEABLE)
				.map(named -> (HolderSet<Enchantment>)named);
			Optional<HolderSet<Enchantment>> doubleTradePrice = context.lookup(Registries.ENCHANTMENT)
				.get(EnchantmentTags.DOUBLE_TRADE_PRICE)
				.map(named -> (HolderSet<Enchantment>)named);
			Optional<HolderSet<Potion>> potionsForTippedArrows = context.lookup(Registries.POTION)
				.get(PotionTags.TRADEABLE)
				.map(named -> (HolderSet<Potion>)named);
			HolderGetter<VillagerType> villagerVariants = context.lookup(Registries.VILLAGER_TYPE);
			return new TradeLookups(items, enchantmentsForTradedEquipment, enchantmentsForBooks, doubleTradePrice, potionsForTippedArrows, villagerVariants);
		}
	}

	public static Holder<VillagerTrade> bootstrap(final BootstrapContext<VillagerTrade> context) {
		TradeLookups lookups = TradeLookups.of(context);
		// Farmer
		registerFarmer(context, lookups);
		// Fisherman
		registerFisherman(context, lookups);
		// Shepherd
		registerShepherd(context, lookups);
		// Fletcher
		registerFletcher(context, lookups);
		// Librarian
		registerLibrarian(context, lookups);
		// Cartographer
		registerCartographer(context, lookups);
		// Cleric
		registerCleric(context, lookups);
		// Armorer
		registerArmorer(context, lookups);
		// Weaponsmith
		registerWeaponsmith(context, lookups);
		// Toolsmith
		registerToolsmith(context, lookups);
		// Butcher
		registerButcher(context, lookups);
		// Leatherworker
		registerLeatherworker(context, lookups);
		// Mason
		registerMason(context, lookups);
		// Wandering Trader
		return registerWanderingTrader(context, lookups);
	}
	private static void registerFarmer(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			FARMER_1_BEETROOT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.BEETROOT, ContextIntProviders.between(6, 9)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FARMER_1_BLUE_BERRY_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.BLUE_BERRIE, ContextIntProviders.between(12, 24)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FARMER_1_CARROT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.CARROT, ContextIntProviders.between(6, 9)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FARMER_1_EMERALD_BREAD,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BREAD, 7), 16, 1, 0.05F).build()
		);
		register(
			context,
			FARMER_1_POTATO_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.POTATO, ContextIntProviders.between(6, 9)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FARMER_1_WHEAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WHEAT, ContextIntProviders.between(3, 5)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FARMER_2_EMERALD_APPLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.APPLE, 4), 12, 5, 0.05F).build()
		);
		register(
			context,
			FARMER_2_EMERALD_PUMPKIN_PIE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PUMPKIN_PIE, 4), 12, 5, 0.05F).build()
		);
		register(
			context,
			FARMER_2_PUMPKIN_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.PUMPKIN, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			FARMER_3_EMERALD_COOKIE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.COOKIE, 14), 12, 10, 0.05F).build()
		);
		register(
			context,
			FARMER_3_MELON_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.MELON, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			FARMER_4_EMERALD_CAKE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.CAKE, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			FARMER_4_EMERALD_SUSPICIOUS_STEW,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 1),
				new ItemStackTemplate(Items.SUSPICIOUS_STEW),
				12,
				15,
				0.05F
			)
			.addModifiers(List.of(
					new SetStewEffectFunction.Builder()
						.withEffect(MobEffects.NIGHT_VISION, ContextIntProviders.exactly(5))
						.withEffect(MobEffects.JUMP_BOOST, ContextIntProviders.exactly(8))
						.withEffect(MobEffects.WEAKNESS, ContextIntProviders.exactly(7))
						.withEffect(MobEffects.BLINDNESS, ContextIntProviders.exactly(6))
						.withEffect(MobEffects.POISON, ContextIntProviders.exactly(14))
						.withEffect(MobEffects.SATURATION, ContextIntProviders.exactly(7))
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			FARMER_5_EMERALD_GLISTENING_MELON_SLICE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 4), new ItemStackTemplate(Items.GLISTERING_MELON_SLICE, 3), 12, 30, 0.05F).build()
		);
		register(
			context,
			FARMER_5_EMERALD_GOLDEN_CARROT,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.GOLDEN_CARROT, 3), 12, 30, 0.05F).build()
		);
	}
	private static void registerFisherman(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			FISHERMAN_1_COAL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COAL, ContextIntProviders.between(2, 3)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_1_EMERALD_COD_COPPER_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(MMEItems.COPPER_COD_BUCKET, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_1_EMERALD_COD_SILVER_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(MMEItems.SILVER_COD_BUCKET, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_1_STRING_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.STRING, 20), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_2_COD_12_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COD, 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_2_SALMON_12_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.SALMON, 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_2_EMERALD_CAMPFIRE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.CAMPFIRE, 1), 12, 5, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_3_EMERALD_ENCHANTED_FISHING_ROD,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 3),
				new ItemStackTemplate(Items.FISHING_ROD),
				3,
				10,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), Items.FISHING_ROD))
			.build()
		);
		register(
			context,
			FISHERMAN_3_TROPICAL_FISH_12_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.TROPICAL_FISH, ContextIntProviders.between(7, 9)), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_3_SALMON_AND_EMERALD_COOKED_SALMON,
			VillagerTrade.builder(
				new TradeCost(Items.SALMON, 10),
				new TradeCost(Items.EMERALD, 1),
				new ItemStackTemplate(Items.COOKED_SALMON, 10),
				12,
				20,
				0.05F
			).build()
		);
		register(
			context,
			FISHERMAN_3_COD_AND_EMERALD_COOKED_COD,
			VillagerTrade.builder(
				new TradeCost(Items.COD, 10),
				new TradeCost(Items.EMERALD, 1),
				new ItemStackTemplate(Items.COOKED_COD, 10),
				12,
				20,
				0.05F
			).build()
		);
		register(
			context,
			FISHERMAN_4_EMERALD_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BUCKET, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_4_EMERALD_MITHRIL_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(6, 7)), new ItemStackTemplate(MMEItems.MITHRIL_BUCKET, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_4_PUFFERFISH_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.PUFFERFISH, ContextIntProviders.between(4, 6)), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			FISHERMAN_5_ACACIA_BOAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.ACACIA_BOAT, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SAVANNA))))
			.build()
		);
		register(
			context,
			FISHERMAN_5_DARK_OAK_BOAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DARK_OAK_BOAT, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SWAMP))))
			.build()
		);
		register(
			context,
			FISHERMAN_5_EMERALD_SUPERIOR_ENCHANTED_FISHING_ROD,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, ContextIntProviders.between(12, 16)),
				new ItemStackTemplate(Items.FISHING_ROD),
				3,
				30,
				0.2F
			)
			.addModifiers(superiorEnchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), Items.FISHING_ROD))
			.build()
		);
		register(
			context,
			FISHERMAN_5_JUNGLE_BOAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.JUNGLE_BOAT, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.DESERT, VillagerType.JUNGLE))))
			.build()
		);
		register(
			context,
			FISHERMAN_5_OAK_BOAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.OAK_BOAT, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.PLAINS))))
			.build()
		);
		register(
			context,
			FISHERMAN_5_SPRUCE_BOAT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.SPRUCE_BOAT, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.SNOW))))
			.build()
		);
	}
	private static void registerShepherd(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			SHEPHERD_1_BLACK_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.black(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_BLUE_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.blue(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_BROWN_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.brown(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_CYAN_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.cyan(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_EMERALD_COPPER_SHEARS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(1, 2)), new ItemStackTemplate(MMEItems.COPPER_SHEARS, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_EMERALD_SILVER_SHEARS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(1, 2)), new ItemStackTemplate(MMEItems.SILVER_SHEARS, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_GRAY_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.gray(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_GREEN_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.green(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_LIGHT_BLUE_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.lightBlue(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_LIGHT_GRAY_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.lightGray(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_LIME_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.lime(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_MAGENTA_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.magenta(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_ORANGE_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.orange(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_PINK_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.pink(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_PURPLE_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.purple(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_RED_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.red(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_WHITE_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.white(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_1_YELLOW_WOOL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WOOL.yellow(), 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_BLUE_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.blue(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_BLACK_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.black(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_BLUE_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.blue(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_BROWN_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.brown(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_CYAN_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.cyan(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_GRAY_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.gray(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_GREEN_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.green(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_LIGHT_BLUE_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.lightBlue(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_LIGHT_GRAY_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.lightGray(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_LIME_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.lime(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_MAGENTA_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.magenta(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_ORANGE_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.orange(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_PINK_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.pink(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_PURPLE_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.purple(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_RED_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.red(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_WHITE_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.white(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_EMERALD_YELLOW_CARPET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CARPET.yellow(), 16), 12, 5, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_GREEN_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.green(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_RED_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.red(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_2_YELLOW_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.yellow(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_SHEARS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(3, 4)), new ItemStackTemplate(Items.SHEARS, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_BLACK_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.black(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_BLUE_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.blue(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_BROWN_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.brown(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_CYAN_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.cyan(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_GRAY_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.gray(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_GREEN_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.green(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_LIGHT_BLUE_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.lightBlue(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_LIGHT_GRAY_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.lightGray(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_LIME_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.lime(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_MAGENTA_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.magenta(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_ORANGE_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.orange(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_PINK_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.pink(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_PURPLE_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.purple(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_RED_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.red(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_WHITE_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.white(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_EMERALD_YELLOW_BED,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BED.yellow(), 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_BLACK_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.black(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_BROWN_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.brown(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_CYAN_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.cyan(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_GRAY_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.gray(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_LIGHT_BLUE_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.lightBlue(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_3_LIME_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.lime(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_BLACK_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.black(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_BLUE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.blue(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_BROWN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.brown(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_CYAN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.cyan(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_GRAY_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.gray(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_GREEN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.green(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_LIGHT_BLUE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.lightBlue(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_LIGHT_GRAY_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.lightGray(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_LIME_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.lime(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_MAGENTA_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.magenta(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_ORANGE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.orange(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_PINK_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.pink(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_PURPLE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.purple(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_RED_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.red(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_WHITE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.white(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_EMERALD_YELLOW_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BANNER.yellow(), 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_LIGHT_GRAY_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.lightGray(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_MAGENTA_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.magenta(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_ORANGE_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.orange(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_PINK_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.pink(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_PURPLE_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.purple(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_4_WHITE_DYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.white(), 12), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_BLACK_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.black(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_BLUE_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.blue(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_BROWN_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.brown(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_CYAN_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.cyan(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_GRAY_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.gray(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_GREEN_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.green(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_LIME_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.lime(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_MAGENTA_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.magenta(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_ORANGE_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.orange(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_PINK_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.pink(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_PURPLE_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.purple(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_RED_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.red(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_WHITE_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.white(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_YELLOW_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.yellow(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_LIGHT_BLUE_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.lightBlue(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_LIGHT_GRAY_WOOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WOOL.lightGray(), 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			SHEPHERD_5_EMERALD_PAINTING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PAINTING, 4), 12, 30, 0.05F).build()
		);
	}
	private static void registerFletcher(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			FLETCHER_1_EMERALD_ARROW,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ARROW, 8), 16, 1, 0.05F).build()
		);
		register(
			context,
			FLETCHER_1_GRAVEL_AND_EMERALD_FLINT,
			VillagerTrade.builder(
				new TradeCost(Items.GRAVEL, 4),
				new TradeCost(Items.EMERALD, 1),
				new ItemStackTemplate(Items.FLINT, 4),
				16,
				2,
				0.05F
			).build()
		);
		register(
			context,
			FLETCHER_1_STICK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.STICK, 32), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			FLETCHER_2_EMERALD_BOW,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BOW, 1), 12, 5, 0.05F).build()
		);
		register(
			context,
			FLETCHER_2_FLINT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FLINT, 8), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			FLETCHER_3_EMERALD_CROSSBOW,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.CROSSBOW, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			FLETCHER_3_STRING_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.STRING, ContextIntProviders.between(6, 8)), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			FLETCHER_4_EMERALD_ENCHANTED_BOW,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 2),
				new ItemStackTemplate(Items.BOW),
				3,
				15,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), Items.BOW))
			.build()
		);
		register(
			context,
			FLETCHER_4_FEATHER_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FEATHER, ContextIntProviders.between(12, 16)), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			FLETCHER_5_ARROW_AND_EMERALD_TIPPED_ARROW,
			VillagerTrade.builder(
				new TradeCost(Items.ARROW, 8),
				new TradeCost(Items.EMERALD, 1),
				new ItemStackTemplate(Items.TIPPED_ARROW, 8),
				12,
				30,
				0.05F
			)
			.addModifiers(List.of(SetRandomPotionFunction.fromTagKey(lookups.potionsForTippedArrows().get()).build()).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			FLETCHER_5_EMERALD_ENCHANTED_CROSSBOW,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 3),
				new ItemStackTemplate(Items.CROSSBOW),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), Items.CROSSBOW))
			.build()
		);
		register(
			context,
			FLETCHER_5_TRIPWIRE_HOOK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.TRIPWIRE_HOOK, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
	}
	private static void registerLibrarian(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			LIBRARIAN_1_EMERALD_AND_BOOK_ENCHANTED_BOOK,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 1),
				new TradeCost(Items.BOOK, 1),
				new ItemStackTemplate(Items.ENCHANTED_BOOK),
				16,
				1,
				0.2F
			)
			.addModifiers(enchantedBook(lookups.items(), lookups.enchantmentsForBooks()))
			.doubleTradePriceEnchantments(lookups.doubleTradePrice().get())
			.build()
		);
		register(
			context,
			LIBRARIAN_1_EMERALD_BOOKSHELF,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BOOKSHELF, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_1_PAPER_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.PAPER, ContextIntProviders.between(13, 18)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_2_BOOK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.BOOK, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_2_EMERALD_AND_BOOK_ENCHANTED_BOOK,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 1),
				new TradeCost(Items.BOOK, 1),
				new ItemStackTemplate(Items.ENCHANTED_BOOK),
				12,
				5,
				0.2F
			)
			.addModifiers(enchantedBook(lookups.items(), lookups.enchantmentsForBooks()))
			.doubleTradePriceEnchantments(lookups.doubleTradePrice().get())
			.build()
		);
		register(
			context,
			LIBRARIAN_2_EMERALD_LANTERN,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.LANTERN, 4), 12, 5, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_3_EMERALD_AND_BOOK_ENCHANTED_BOOK,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 1),
				new TradeCost(Items.BOOK, 1),
				new ItemStackTemplate(Items.ENCHANTED_BOOK),
				12,
				10,
				0.2F
			)
			.addModifiers(enchantedBook(lookups.items(), lookups.enchantmentsForBooks()))
			.doubleTradePriceEnchantments(lookups.doubleTradePrice().get())
			.build()
		);
		register(
			context,
			LIBRARIAN_3_EMERALD_GLASS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLASS, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_3_INK_SAC_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DYE.black(), 3), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_4_EMERALD_BOOK_AND_ENCHANTED_BOOK,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 1),
				new TradeCost(Items.BOOK, 1),
				new ItemStackTemplate(Items.ENCHANTED_BOOK),
				12,
				15,
				0.2F
			)
			.addModifiers(enchantedBook(lookups.items(), lookups.enchantmentsForBooks()))
			.doubleTradePriceEnchantments(lookups.doubleTradePrice().get())
			.build()
		);
		register(
			context,
			LIBRARIAN_4_EMERALD_CLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.CLOCK, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_4_EMERALD_COMPASS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.COMPASS, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_4_WRITABLE_BOOK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WRITABLE_BOOK, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_BLACK_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.black(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_BLUE_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.blue(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_BROWN_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.brown(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_CYAN_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.cyan(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_GRAY_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.gray(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_GREEN_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.green(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_LIGHT_BLUE_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.lightBlue(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_LIGHT_GRAY_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.lightGray(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_LIME_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.lime(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_MAGENTA_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.magenta(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_ORANGE_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.orange(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_PINK_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.pink(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_PURPLE_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.purple(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_RED_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.red(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_WHITE_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.white(), 6), 12, 30, 0.05F).build()
		);
		register(
			context,
			LIBRARIAN_5_EMERALD_YELLOW_CANDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.DYED_CANDLE.yellow(), 6), 12, 30, 0.05F).build()
		);
	}
	private static void registerCartographer(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			CARTOGRAPHER_1_EMERALD_MAP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.MAP, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			CARTOGRAPHER_1_PAPER_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.PAPER, ContextIntProviders.between(10, 14)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_EXPLORER_JUNGLE_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SWAMP, VillagerType.SAVANNA, VillagerType.DESERT))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_JUNGLE_PYRAMID_MAPS))
						.setMapDecoration(MapDecorationTypes.JUNGLE_TEMPLE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.explorer_jungle"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_EXPLORER_SWAMP_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.SNOW, VillagerType.JUNGLE))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_SWAMP_HUT_MAPS))
						.setMapDecoration(MapDecorationTypes.SWAMP_HUT)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.explorer_swamp"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_DESERT_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SAVANNA, VillagerType.JUNGLE))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_DESERT_VILLAGE_MAPS))
						.setMapDecoration(MapDecorationTypes.DESERT_VILLAGE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.village_desert"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_PLAINS_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.SNOW, VillagerType.SAVANNA, VillagerType.DESERT))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_PLAINS_VILLAGE_MAPS))
						.setMapDecoration(MapDecorationTypes.PLAINS_VILLAGE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.village_plains"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_SAVANNA_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.PLAINS, VillagerType.JUNGLE, VillagerType.DESERT))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_SAVANNA_VILLAGE_MAPS))
						.setMapDecoration(MapDecorationTypes.SAVANNA_VILLAGE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.village_savanna"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_SNOWY_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.SWAMP))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_SNOWY_VILLAGE_MAPS))
						.setMapDecoration(MapDecorationTypes.SNOWY_VILLAGE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.village_snowy"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_EMERALD_AND_COMPASS_VILLAGE_TAIGA_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 8),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				5,
				0.2F
			)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SWAMP, VillagerType.SNOW, VillagerType.PLAINS))))
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_TAIGA_VILLAGE_MAPS))
						.setMapDecoration(MapDecorationTypes.TAIGA_VILLAGE)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.village_taiga"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_2_GLASS_PANE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.GLASS_PANE, ContextIntProviders.between(8, 12)), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			CARTOGRAPHER_3_COMPASS_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COMPASS, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			CARTOGRAPHER_3_EMERALD_AND_COMPASS_OCEAN_EXPLORER_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 13),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				10,
				0.2F
			)
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_OCEAN_MONUMENT_MAPS))
						.setMapDecoration(MapDecorationTypes.OCEAN_MONUMENT)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.monument"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_3_EMERALD_AND_COMPASS_TRIAL_CHAMBER_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 12),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				10,
				0.2F
			)
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_BURIED_TRIAL_CHAMBERS_MAPS))
						.setMapDecoration(MapDecorationTypes.TRIAL_CHAMBERS)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.trial_chambers"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_BLACK_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.black(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SWAMP))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_BLUE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.blue(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SNOW, VillagerType.TAIGA))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_BROWN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.brown(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.PLAINS, VillagerType.JUNGLE))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_CYAN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.cyan(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.DESERT, VillagerType.SNOW))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_GRAY_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.gray(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.DESERT))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_GREEN_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.green(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.DESERT, VillagerType.SAVANNA, VillagerType.JUNGLE))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_ITEM_FRAME,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ITEM_FRAME, 3), 12, 15, 0.05F).build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_LIGHT_BLUE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.lightBlue(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SNOW, VillagerType.SWAMP))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_LIME_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.lime(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.DESERT, VillagerType.TAIGA))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_MAGENTA_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.magenta(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SAVANNA))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_ORANGE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.orange(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SAVANNA, VillagerType.DESERT))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_PINK_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.pink(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.PLAINS))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_PURPLE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.purple(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.TAIGA, VillagerType.SWAMP))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_RED_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.red(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SNOW, VillagerType.SAVANNA))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_WHITE_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.white(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.SNOW, VillagerType.PLAINS))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_4_EMERALD_YELLOW_BANNER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BANNER.yellow(), 1), 12, 15, 0.05F)
			.merchantPredicate(villagerTypeRestriction(villagerTypeHolderSet(lookups.villagerVariants(), List.of(VillagerType.PLAINS, VillagerType.JUNGLE))))
			.build()
		);
		register(
			context,
			CARTOGRAPHER_5_EMERALD_AND_COMPASS_WOODLAND_MANSION_MAP,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 14),
				new TradeCost(Items.COMPASS, 1),
				new ItemStackTemplate(Items.MAP),
				12,
				30,
				0.2F
			)
			.addModifiers(List.of(
					ExplorationMapFunction.makeExplorationMap(context.lookup(Registries.STRUCTURE).getOrThrow(StructureTags.ON_WOODLAND_MANSION_MAPS))
						.setMapDecoration(MapDecorationTypes.WOODLAND_MANSION)
						.setSearchRadius(100)
						.setSkipKnownStructures(true)
						.build(),
					SetNameFunction.setName(Component.translatable("filled_map.mansion"), SetNameFunction.Target.ITEM_NAME).build(),
					FilteredFunction.filtered(
							new ItemPredicate.Builder()
								.of(lookups.items(), Items.FILLED_MAP)
								.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build())
								.build()
						)
						.onFail(DiscardItem.discardItem().build())
						.build()
				).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			CARTOGRAPHER_5_EMERALD_GLOBE_BANNER_PATTERN,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 8), new ItemStackTemplate(Items.GLOBE_BANNER_PATTERN, 1), 12, 30, 0.05F).build()
		);
	}
	private static void registerCleric(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			CLERIC_1_EMERALD_REDSTONE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.REDSTONE, 5), 16, 1, 0.05F).build()
		);
		register(
			context,
			CLERIC_1_ROTTEN_FLESH_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.ROTTEN_FLESH, ContextIntProviders.between(8, 10)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			CLERIC_2_EMERALD_LAPIS_LAZULI,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.LAPIS_LAZULI, 7), 12, 5, 0.05F).build()
		);
		register(
			context,
			CLERIC_2_GOLD_INGOT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.GOLD_INGOT, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			CLERIC_3_EMERALD_GLOWSTONE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLOWSTONE, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			CLERIC_3_RABBIT_FOOT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.RABBIT_FOOT, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 20, 0.05F).build()
		);
		register(
			context,
			CLERIC_4_EMERALD_ENDER_PEARL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(2, 3)), new ItemStackTemplate(Items.ENDER_PEARL, 1), 12, 15, 0.05F).build()
		);
		register(
			context,
			CLERIC_4_GLASS_BOTTLE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.GLASS_BOTTLE, ContextIntProviders.between(4, 6)), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			CLERIC_4_TURTLE_SCUTE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.TURTLE_SCUTE, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			CLERIC_5_EMERALD_EXPERIENCE_BOTTLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.EXPERIENCE_BOTTLE, 3), 12, 30, 0.05F).build()
		);
		register(
			context,
			CLERIC_5_NETHER_WART_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.NETHER_WART, ContextIntProviders.between(4, 6)), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
	}
	private static void registerArmorer(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			ARMORER_1_EMERALD_IRON_BOOTS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.IRON_BOOTS, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			ARMORER_1_EMERALD_IRON_CHESTPLATE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 7), new ItemStackTemplate(Items.IRON_CHESTPLATE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			ARMORER_1_EMERALD_IRON_HELMET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.IRON_HELMET, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			ARMORER_1_EMERALD_IRON_LEGGINGS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 5), new ItemStackTemplate(Items.IRON_LEGGINGS, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			ARMORER_2_EMERALD_MITHRIL_CHAINMAIL_BOOTS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 4), new ItemStackTemplate(MMEItems.MITHRIL_CHAINMAIL_BOOTS, 1), 12, 5, 0.2F).build()
		);
		register(
			context,
			ARMORER_2_EMERALD_MITHRIL_CHAINMAIL_LEGGINGS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 10), new ItemStackTemplate(MMEItems.MITHRIL_CHAINMAIL_LEGGINGS, 1), 12, 5, 0.2F).build()
		);
		register(
			context,
			ARMORER_3_DIAMOND_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 10, 0.2F).build()
		);
		register(
			context,
			ARMORER_3_EMERALD_MITHRIL_CHAINMAIL_CHESTPLATE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 14), new ItemStackTemplate(MMEItems.MITHRIL_CHAINMAIL_CHESTPLATE, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			ARMORER_3_EMERALD_MITHRIL_CHAINMAIL_HELMET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 6), new ItemStackTemplate(MMEItems.MITHRIL_CHAINMAIL_HELMET, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			ARMORER_3_EMERALD_SHIELD,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.SHIELD, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			ARMORER_4_DIAMOND_ENCHANTED_MITHRIL_BOOTS,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, 10),
				new ItemStackTemplate(MMEItems.MITHRIL_BOOTS),
				3,
				15,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_BOOTS))
			.build()
		);
		register(
			context,
			ARMORER_4_DIAMOND_ENCHANTED_MITHRIL_LEGGINGS,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, 14),
				new ItemStackTemplate(MMEItems.MITHRIL_LEGGINGS),
				3,
				15,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_LEGGINGS))
			.build()
		);
		register(
			context,
			ARMORER_5_DIAMOND_ENCHANTED_MITHRIL_CHESTPLATE,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, 16),
				new ItemStackTemplate(MMEItems.MITHRIL_CHESTPLATE),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_CHESTPLATE))
			.build()
		);
		register(
			context,
			ARMORER_5_DIAMOND_ENCHANTED_MITHRIL_HELMET,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, 10),
				new ItemStackTemplate(MMEItems.MITHRIL_HELMET),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_HELMET))
			.build()
		);
	}
	private static void registerWeaponsmith(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			WEAPONSMITH_1_COAL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COAL, ContextIntProviders.between(4, 6)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_1_EMERALD_IRON_SWORD,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.IRON_SWORD, 1), 4, 1, 0.2F).build()
		);
		register(
			context,
			WEAPONSMITH_1_EMERALD_IRON_DAGGER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_DAGGER, 1), 4, 1, 0.2F).build()
		);
		register(
			context,
			WEAPONSMITH_2_COPPER_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COPPER_INGOT, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_2_SILVER_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.SILVER_INGOT, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_2_EMERALD_BELL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(4, 6)), new ItemStackTemplate(Items.BELL, 1), 12, 5, 0.2F).build()
		);
		register(
			context,
			WEAPONSMITH_3_IRON_INGOT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.IRON_INGOT, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_3_FLINT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FLINT, 8), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_4_DIAMOND_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 30, 0.05F).build()
		);
		register(
			context,
			WEAPONSMITH_5_DIAMOND_MITHRIL_SWORD,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 2), new ItemStackTemplate(MMEItems.MITHRIL_SWORD, 1), 12, 30, 0.2F).build()
		);
		register(
			context,
			WEAPONSMITH_5_DIAMOND_MITHRIL_DAGGER,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 1), new ItemStackTemplate(MMEItems.MITHRIL_DAGGER, 1), 12, 30, 0.2F).build()
		);
	}
	private static void registerToolsmith(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			TOOLSMITH_1_COAL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COAL, ContextIntProviders.between(7, 10)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_AXE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.IRON_AXE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_BATTLE_AXE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_BATTLE_AXE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_HATCHET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_HATCHET, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_HOE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.IRON_HOE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_MATTOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_MATTOCK, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_PICKAXE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.IRON_PICKAXE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_SCYTHE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_SCYTHE, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_SHOVEL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.IRON_SHOVEL, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_1_EMERALD_IRON_WAR_HAMMER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MMEItems.IRON_WAR_HAMMER, 1), 16, 1, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_2_IRON_INGOT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.IRON_INGOT, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			TOOLSMITH_2_EMERALD_BELL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(3, 5)), new ItemStackTemplate(Items.BELL, 1), 12, 5, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_3_DIAMOND_MITHRIL_AXE,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, ContextIntProviders.between(4, 6)), new ItemStackTemplate(MMEItems.MITHRIL_AXE, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_3_DIAMOND_MITHRIL_PICKAXE,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, ContextIntProviders.between(4, 6)), new ItemStackTemplate(MMEItems.MITHRIL_PICKAXE, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_3_DIAMOND_MITHRIL_SHOVEL,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, ContextIntProviders.between(4, 6)), new ItemStackTemplate(MMEItems.MITHRIL_SHOVEL, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_3_DIAMOND_MITHRIL_HOE,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, ContextIntProviders.between(4, 6)), new ItemStackTemplate(MMEItems.MITHRIL_HOE, 1), 12, 10, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_3_FLINT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FLINT, 8), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			TOOLSMITH_4_DIAMOND_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 15, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_4_DIAMOND_ENCHANTED_MITHRIL_HATCHET,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, ContextIntProviders.between(8, 12)),
				new ItemStackTemplate(MMEItems.MITHRIL_HATCHET),
				3,
				15,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_HATCHET))
			.build()
		);
		register(
			context,
			TOOLSMITH_4_DIAMOND_ENCHANTED_MITHRIL_MATTOCK,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, ContextIntProviders.between(8, 12)),
				new ItemStackTemplate(MMEItems.MITHRIL_MATTOCK),
				3,
				15,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_MATTOCK))
			.build()
		);
		register(
			context,
			TOOLSMITH_5_DIAMOND_MITHRIL_INGOT,
			VillagerTrade.builder(new TradeCost(Items.DIAMOND, 2), new ItemStackTemplate(MMEItems.MITHRIL_INGOT, 1), 12, 30, 0.2F).build()
		);
		register(
			context,
			TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_BATTLE_AXE,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, ContextIntProviders.between(12, 16)),
				new ItemStackTemplate(MMEItems.MITHRIL_BATTLE_AXE),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_BATTLE_AXE))
			.build()
		);
		register(
			context,
			TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_SCYTHE,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, ContextIntProviders.between(12, 16)),
				new ItemStackTemplate(MMEItems.MITHRIL_SCYTHE),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_SCYTHE))
			.build()
		);
		register(
			context,
			TOOLSMITH_5_DIAMOND_ENCHANTED_MITHRIL_WAR_HAMMER,
			VillagerTrade.builder(
				new TradeCost(Items.DIAMOND, ContextIntProviders.between(12, 16)),
				new ItemStackTemplate(MMEItems.MITHRIL_WAR_HAMMER),
				3,
				30,
				0.2F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), MMEItems.MITHRIL_WAR_HAMMER))
			.build()
		);
	}
	private static void registerButcher(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			BUTCHER_1_CHICKEN_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.CHICKEN, 11), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			BUTCHER_1_EMERALD_RABBIT_STEW,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.RABBIT_STEW, 1), 16, 1, 0.05F).build()
		);
		register(
			context,
			BUTCHER_1_PORKCHOP_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.PORKCHOP, 4), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			BUTCHER_1_RABBIT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.RABBIT, ContextIntProviders.between(5, 7)), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			BUTCHER_2_CHARCOAL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.CHARCOAL, ContextIntProviders.between(7, 10)), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			BUTCHER_2_EMERALD_COOKED_CHICKEN,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.COOKED_CHICKEN, 8), 12, 5, 0.05F).build()
		);
		register(
			context,
			BUTCHER_2_EMERALD_COOKED_PORKCHOP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.COOKED_PORKCHOP, 5), 12, 5, 0.05F).build()
		);
		register(
			context,
			BUTCHER_3_BEEF_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.BEEF, 7), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			BUTCHER_3_MUTTON_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.MUTTON, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			BUTCHER_4_CHICKEN_AND_CHARCOAL_COOKED_CHICKEN,
			VillagerTrade.builder(
				new TradeCost(Items.CHICKEN, 16),
				new TradeCost(Items.CHARCOAL, 1),
				new ItemStackTemplate(Items.COOKED_CHICKEN, 16),
				12,
				30,
				0.05F
			).build()
		);
		register(
			context,
			BUTCHER_4_MUTTON_AND_CHARCOAL_COOKED_MUTTON,
			VillagerTrade.builder(
				new TradeCost(Items.MUTTON, 16),
				new TradeCost(Items.CHARCOAL, 1),
				new ItemStackTemplate(Items.COOKED_MUTTON, 16),
				12,
				30,
				0.05F
			).build()
		);
		register(
			context,
			BUTCHER_4_RABBIT_AND_CHARCOAL_COOKED_RABBIT,
			VillagerTrade.builder(
				new TradeCost(Items.RABBIT, 16),
				new TradeCost(Items.CHARCOAL, 1),
				new ItemStackTemplate(Items.COOKED_RABBIT, 16),
				12,
				30,
				0.05F
			).build()
		);
		register(
			context,
			BUTCHER_4_DRIED_KELP_BLOCK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.DRIED_KELP_BLOCK, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			BUTCHER_5_PORKCHOP_AND_CHARCOAL_COOKED_PORKCHOP,
			VillagerTrade.builder(
				new TradeCost(Items.PORKCHOP, 16),
				new TradeCost(Items.CHARCOAL, 1),
				new ItemStackTemplate(Items.COOKED_PORKCHOP, 16),
				12,
				30,
				0.05F
			).build()
		);
		register(
			context,
			BUTCHER_5_BEEF_AND_CHARCOAL_COOKED_BEEF,
			VillagerTrade.builder(
				new TradeCost(Items.BEEF, 16),
				new TradeCost(Items.CHARCOAL, 1),
				new ItemStackTemplate(Items.COOKED_BEEF, 16),
				12,
				30,
				0.05F
			).build()
		);
		register(
			context,
			BUTCHER_5_SWEET_BERRIES_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.SWEET_BERRIES, 10), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
	}
	private static void registerLeatherworker(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			LEATHERWORKER_1_EMERALD_DYED_LEATHER_CHESTPLATE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_CHESTPLATE), 16, 1, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_CHESTPLATE))
			.build()
		);
		register(
			context,
			LEATHERWORKER_1_EMERALD_DYED_LEATHER_LEGGINGS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_LEGGINGS), 16, 1, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_LEGGINGS))
			.build()
		);
		register(
			context,
			LEATHERWORKER_1_LEATHER_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.LEATHER, 3), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			LEATHERWORKER_2_EMERALD_DYED_LEATHER_BOOTS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_BOOTS), 12, 5, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_BOOTS))
			.build()
		);
		register(
			context,
			LEATHERWORKER_2_EMERALD_DYED_LEATHER_HELMET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_HELMET), 12, 5, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_HELMET))
			.build()
		);
		register(
			context,
			LEATHERWORKER_2_FLINT_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FLINT, 8), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			LEATHERWORKER_3_EMERALD_DYED_LEATHER_CHESTPLATE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_CHESTPLATE), 12, 10, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_CHESTPLATE))
			.build()
		);
		register(
			context,
			LEATHERWORKER_3_RABBIT_HIDE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.RABBIT_HIDE, ContextIntProviders.between(3, 4)), new ItemStackTemplate(Items.EMERALD, 1), 12, 20, 0.05F).build()
		);
		register(
			context,
			LEATHERWORKER_4_EMERALD_DYED_LEATHER_HORSE_ARMOR,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_HORSE_ARMOR), 12, 15, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_HORSE_ARMOR))
			.build()
		);
		register(
			context,
			LEATHERWORKER_4_TURTLE_SCUTE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.TURTLE_SCUTE, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 30, 0.05F).build()
		);
		register(
			context,
			LEATHERWORKER_5_EMERALD_DYED_LEATHER_HELMET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.LEATHER_HELMET), 12, 30, 0.2F)
			.addModifiers(dyedItem(lookups.items(), Items.LEATHER_HELMET))
			.build()
		);
		register(
			context,
			LEATHERWORKER_5_EMERALD_SADDLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, ContextIntProviders.between(2, 3)), new ItemStackTemplate(Items.SADDLE, 1), 12, 30, 0.05F).build()
		);
	}
	private static void registerMason(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			MASON_1_CLAY_BALL_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.CLAY_BALL, 8), new ItemStackTemplate(Items.EMERALD, 1), 16, 2, 0.05F).build()
		);
		register(
			context,
			MASON_1_EMERALD_BRICK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BRICK, 6), 16, 1, 0.05F).build()
		);
		register(
			context,
			MASON_2_EMERALD_STONE_BRICKS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.STONE_BRICKS, 4), 12, 5, 0.05F).build()
		);
		register(
			context,
			MASON_2_COBBLESTONE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.COBBLESTONE, 4), new ItemStackTemplate(Items.EMERALD, 1), 12, 10, 0.05F).build()
		);
		register(
			context,
			MASON_3_ANDESITE_AMETHYST_SHARD,
			VillagerTrade.builder(new TradeCost(Items.ANDESITE, 4), new ItemStackTemplate(Items.AMETHYST_SHARD, 2), 12, 20, 0.05F).build()
		);
		register(
			context,
			MASON_3_DIORITE_AMETHYST_SHARD,
			VillagerTrade.builder(new TradeCost(Items.DIORITE, 4), new ItemStackTemplate(Items.AMETHYST_SHARD, 2), 12, 20, 0.05F).build()
		);
		register(
			context,
			MASON_3_EMERALD_DRIPSTONE_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DRIPSTONE_BLOCK, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			MASON_3_EMERALD_POLISHED_ANDESITE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.POLISHED_ANDESITE, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			MASON_3_EMERALD_POLISHED_DIORITE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.POLISHED_DIORITE, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			MASON_3_EMERALD_POLISHED_GRANITE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.POLISHED_GRANITE, 4), 12, 10, 0.05F).build()
		);
		register(
			context,
			MASON_3_GRANITE_AMETHYST_SHARD,
			VillagerTrade.builder(new TradeCost(Items.GRANITE, 4), new ItemStackTemplate(Items.AMETHYST_SHARD, 2), 12, 20, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BLACK_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.black(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BLACK_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.black(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BLUE_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.blue(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BLUE_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.blue(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BROWN_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.brown(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_BROWN_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.brown(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_CYAN_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.cyan(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_CYAN_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.cyan(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_GRAY_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.gray(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_GRAY_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.gray(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_GREEN_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.green(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_GREEN_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.green(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIGHT_BLUE_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.lightBlue(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIGHT_BLUE_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.lightBlue(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIGHT_GRAY_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.lightGray(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIGHT_GRAY_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.lightGray(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIME_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.lime(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_LIME_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.lime(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_MAGENTA_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.magenta(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_MAGENTA_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.magenta(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_ORANGE_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.orange(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_ORANGE_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.orange(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_PINK_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.pink(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_PINK_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.pink(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_PURPLE_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.purple(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_PURPLE_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.purple(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_RED_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.red(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_RED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.red(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_WHITE_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.white(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_WHITE_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.white(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_YELLOW_GLAZED_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GLAZED_TERRACOTTA.yellow(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_YELLOW_TERRACOTTA,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYED_TERRACOTTA.yellow(), 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_4_EMERALD_QUARTZ,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.QUARTZ, 4), 12, 15, 0.05F).build()
		);
		register(
			context,
			MASON_5_EMERALD_QUARTZ_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.QUARTZ_BLOCK, 4), 12, 30, 0.05F).build()
		);
		register(
			context,
			MASON_5_EMERALD_QUARTZ_PILLAR,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.QUARTZ_PILLAR, 4), 12, 30, 0.05F).build()
		);
	}
	private static Holder<VillagerTrade> registerWanderingTrader(final BootstrapContext<VillagerTrade> context, final TradeLookups lookups) {
		register(
			context,
			WANDERING_TRADER_BAKED_POTATO_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.BAKED_POTATO, 2), new ItemStackTemplate(Items.EMERALD, 1), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ACACIA_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ACACIA_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ACACIA_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ACACIA_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ALLIUM,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ALLIUM, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_AZURE_BLUET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.AZURE_BLUET, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BEETROOT_SEEDS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BEETROOT_SEEDS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BIRCH_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BIRCH_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BIRCH_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BIRCH_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BLACK_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.black(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BLUE_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.blue(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BLUE_ICE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.BLUE_ICE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BLUE_ORCHID,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BLUE_ORCHID, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BRAIN_CORAL_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BRAIN_CORAL_BLOCK, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BROWN_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.brown(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BROWN_MUSHROOM,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.BROWN_MUSHROOM, 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_BUBBLE_CORAL_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.BUBBLE_CORAL_BLOCK, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_CACTUS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.CACTUS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_CHERRY_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CHERRY_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_CHERRY_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CHERRY_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_CORNFLOWER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.CORNFLOWER, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_CYAN_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.cyan(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_DANDELION,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DANDELION, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_DARK_OAK_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DARK_OAK_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_DARK_OAK_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DARK_OAK_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_DRY_TALL_GRASS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DRY_TALL_GRASS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ENCHANTED_IRON_PICKAXE,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, ContextIntProviders.between(3, 5)),
				new ItemStackTemplate(Items.IRON_PICKAXE),
				3,
				2,
				0.05F
			)
			.addModifiers(enchantedItem(lookups.items(), lookups.enchantmentsForTradedEquipment(), Items.IRON_PICKAXE))
			.build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_FERN,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.FERN, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_FIRE_CORAL_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.FIRE_CORAL_BLOCK, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_FIREFLY_BUSH,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.FIREFLY_BUSH, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_FISH_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.TROPICAL_FISH_BUCKET, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_GLOWSTONE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.GLOWSTONE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_GOLDEN_DANDELION,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.GOLDEN_DANDELION, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_GRAY_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.gray(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_GREEN_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.green(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_GUNPOWDER,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.GUNPOWDER, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_HORN_CORAL_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.HORN_CORAL_BLOCK, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_JUNGLE_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.JUNGLE_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_JUNGLE_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.JUNGLE_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_KELP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.KELP, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LIGHT_BLUE_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.lightBlue(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LIGHT_GRAY_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.lightGray(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LILY_OF_THE_VALLEY,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.LILY_OF_THE_VALLEY, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LILY_PAD,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.LILY_PAD, 5), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LIME_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.lime(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LONG_FIRE_RESISTANCE_POTION,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 5),
				new ItemStackTemplate(Items.POTION),
				6,
				1,
				0.05F
			)
			.addModifiers(List.of(SetPotionFunction.setPotion(Potions.LONG_FIRE_RESISTANCE).build()).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LONG_INVISIBILITY_POTION,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 5),
				new ItemStackTemplate(Items.POTION),
				6,
				1,
				0.05F
			)
			.addModifiers(List.of(SetPotionFunction.setPotion(Potions.LONG_INVISIBILITY).build()).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_LONG_NIGHT_VISION_POTION,
			VillagerTrade.builder(
				new TradeCost(Items.EMERALD, 6),
				new ItemStackTemplate(Items.POTION),
				6,
				1,
				0.05F
			)
			.addModifiers(List.of(SetPotionFunction.setPotion(Potions.LONG_NIGHT_VISION).build()).stream().map(Holder::direct).toList())
			.build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_MAGENTA_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.magenta(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_MANGROVE_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.MANGROVE_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_MANGROVE_PROPAGULE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.MANGROVE_PROPAGULE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_MELON_SEEDS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.MELON_SEEDS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_MOSS_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.MOSS_BLOCK, 2), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_NAME_TAG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.NAME_TAG, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_NAUTILUS_SHELL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 5), new ItemStackTemplate(Items.NAUTILUS_SHELL, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_OAK_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.OAK_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_OAK_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.OAK_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_OPEN_EYEBLOSSOM,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.OPEN_EYEBLOSSOM, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ORANGE_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.orange(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ORANGE_TULIP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ORANGE_TULIP, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_OXEYE_DAISY,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.OXEYE_DAISY, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PACKED_ICE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PACKED_ICE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PALE_HANGING_MOSS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PALE_HANGING_MOSS, 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PALE_MOSS_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PALE_MOSS_BLOCK, 2), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PALE_OAK_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PALE_OAK_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PALE_OAK_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PALE_OAK_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PINK_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.pink(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PINK_TULIP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PINK_TULIP, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PODZOL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.PODZOL, 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_POINTED_DRIPSTONE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.POINTED_DRIPSTONE, 2), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_POPPY,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.POPPY, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PUFFERFISH_BUCKET,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.PUFFERFISH_BUCKET, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PUMPKIN,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PUMPKIN, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PUMPKIN_SEEDS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.PUMPKIN_SEEDS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_PURPLE_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.purple(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_RED_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.red(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_RED_MUSHROOM,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.RED_MUSHROOM, 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_RED_SAND,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.RED_SAND, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_RED_TULIP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.RED_TULIP, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_ROOTED_DIRT,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.ROOTED_DIRT, 2), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SAND,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.SAND, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SEA_PICKLE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 2), new ItemStackTemplate(Items.SEA_PICKLE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SLIME_BALL,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 4), new ItemStackTemplate(Items.SLIME_BALL, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SMALL_DRIPLEAF,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.SMALL_DRIPLEAF, 2), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SPRUCE_LOG,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.SPRUCE_LOG, 4), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SPRUCE_SAPLING,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.SPRUCE_SAPLING, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_SUGAR_CANE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.SUGAR_CANE, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_TUBE_CORAL_BLOCK,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(Items.TUBE_CORAL_BLOCK, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_VINE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.VINE, 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_WHEAT_SEEDS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WHEAT_SEEDS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_WHITE_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.white(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_WHITE_TULIP,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WHITE_TULIP, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_WILDFLOWERS,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.WILDFLOWERS, 1), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_EMERALD_YELLOW_DYE,
			VillagerTrade.builder(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(Items.DYE.yellow(), 3), 6, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_FERMENTED_SPIDER_EYE_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.FERMENTED_SPIDER_EYE, 1), new ItemStackTemplate(Items.EMERALD, 3), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_HAY_BLOCK_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.HAY_BLOCK, 1), new ItemStackTemplate(Items.EMERALD, 1), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_MILK_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.MILK_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_MILK_COPPER_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.COPPER_MILK_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_MILK_SILVER_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.SILVER_MILK_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_WATER_BOTTLE_EMERALD,
			VillagerTrade.builder(
				new TradeCost(
					Items.POTION.builtInRegistryHolder(),
					ContextIntProviders.exactly(1),
					DataComponentExactPredicate.expect(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER))
				),
				new ItemStackTemplate(Items.EMERALD),
				2,
				1,
				0.05F
			).build()
		);
		register(
			context,
			WANDERING_TRADER_WATER_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(Items.WATER_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
		register(
			context,
			WANDERING_TRADER_WATER_COPPER_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.COPPER_WATER_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
		return register(
			context,
			WANDERING_TRADER_WATER_SILVER_BUCKET_EMERALD,
			VillagerTrade.builder(new TradeCost(MMEItems.SILVER_WATER_BUCKET, 1), new ItemStackTemplate(Items.EMERALD, 2), 12, 1, 0.05F).build()
		);
	}

	public static Holder.Reference<VillagerTrade> register(
		final BootstrapContext<VillagerTrade> context, final ResourceKey<VillagerTrade> resourceKey, final VillagerTrade villagerTrade
	) {
		return context.register(resourceKey, villagerTrade);
	}

	public static ResourceKey<VillagerTrade> resourceKey(final String path) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(MME.MOD_ID, path));
	}

	public static HolderSet<VillagerType> villagerTypeHolderSet(
		final HolderGetter<VillagerType> villagerVariants, final List<ResourceKey<VillagerType>> resourceKeys
	) {
		List<Holder<VillagerType>> villagerTypes = new ArrayList<>();

		for (ResourceKey<VillagerType> resourceKey : resourceKeys) {
			villagerTypes.add(villagerVariants.getOrThrow(resourceKey));
		}

		return HolderSet.direct(villagerTypes);
	}

	public static Holder<LootItemCondition> villagerTypeRestriction(final HolderSet<VillagerType> villagerTypes) {
		return Holder.direct(
			new LootItemEntityPropertyCondition(
				Optional.of(
					EntityPredicate.Builder.entity()
						.components(
							DataComponentMatchers.Builder.components().partial(DataComponentPredicates.VILLAGER_VARIANT, VillagerTypePredicate.villagerTypes(villagerTypes)).build()
						)
						.build()
				),
				LootContext.EntityTarget.THIS
			)
		);
	}

	private static List<Holder<LootItemFunction>> dyedItem(final HolderGetter<Item> items, final Item expectedItem) {
		return List.of(
			addRandomDye(),
			FilteredFunction.filtered(
					new ItemPredicate.Builder()
						.of(items, expectedItem)
						.withComponents(DataComponentMatchers.Builder.components().any(DataComponents.DYED_COLOR).build())
						.build()
				)
				.onFail(DiscardItem.discardItem().build())
				.build()
		).stream().map(Holder::direct).toList();
	}

	private static LootItemFunction addRandomDye() {
		return SetRandomDyesFunction.withCount(
				ContextIntProviders.add(ContextIntProviders.exactly(1), ContextIntProviders.binomial(2, 0.75F))
			)
			.build();
	}

	public static List<Holder<LootItemFunction>> enchantedBook(final HolderGetter<Item> items, final Optional<HolderSet<Enchantment>> options) {
		return List.of(
			new EnchantRandomlyFunction.Builder().withOptions(options.get()).allowingIncompatibleEnchantments().includeAdditionalCostComponent().build(),
			FilteredFunction.filtered(
					new ItemPredicate.Builder()
						.of(items, Items.ENCHANTED_BOOK)
						.withComponents(
							DataComponentMatchers.Builder.components()
								.partial(
									DataComponentPredicates.STORED_ENCHANTMENTS,
									EnchantmentsPredicate.storedEnchantments(List.of(new EnchantmentPredicate(Optional.empty(), MinMaxBounds.Ints.ANY)))
								)
								.build()
						)
						.build()
				)
				.onFail(DiscardItem.discardItem().build())
				.build()
		).stream().map(Holder::direct).toList();
	}

	public static List<Holder<LootItemFunction>> enchantedItem(final HolderGetter<Item> items, final Optional<HolderSet<Enchantment>> options, final Item expectedItem) {
		return List.of(
			new EnchantWithLevelsFunction.Builder(ContextIntProviders.between(5, 19)).withOptions(options).includeAdditionalCostComponent().build(),
			FilteredFunction.filtered(
					new ItemPredicate.Builder()
						.of(items, expectedItem)
						.withComponents(
							DataComponentMatchers.Builder.components()
								.partial(
									DataComponentPredicates.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(Optional.empty(), MinMaxBounds.Ints.ANY)))
								)
								.build()
						)
						.build()
				)
				.onFail(DiscardItem.discardItem().build())
				.build()
		).stream().map(Holder::direct).toList();
	}

	public static List<Holder<LootItemFunction>> superiorEnchantedItem(final HolderGetter<Item> items, final Optional<HolderSet<Enchantment>> options, final Item expectedItem) {
		return List.of(
			new EnchantWithLevelsFunction.Builder(ContextIntProviders.between(30, 50)).withOptions(options).includeAdditionalCostComponent().build(),
			FilteredFunction.filtered(
					new ItemPredicate.Builder()
						.of(items, expectedItem)
						.withComponents(
							DataComponentMatchers.Builder.components()
								.partial(
									DataComponentPredicates.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(Optional.empty(), MinMaxBounds.Ints.ANY)))
								)
								.build()
						)
						.build()
				)
				.onFail(DiscardItem.discardItem().build())
				.build()
		).stream().map(Holder::direct).toList();
	}
}
