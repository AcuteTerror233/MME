package com.acuteterror233.mite;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class At_mite implements ModInitializer {
	public static final String MOD_ID = "at_mite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier BASE_BLOCK_INTERACTION_RANGE = Identifier.ofVanilla("block_interaction_range");
	public static final Identifier BASE_ENTITY_INTERACTION_RANGE = Identifier.ofVanilla("entity_interaction_range");
    //block是按照方块继承分类最大堆叠的
    public static final Set<Class<?>> COUNT1_BLOCK = new HashSet<>(Arrays.asList(
            FenceGateBlock.class,
            BedBlock.class,
            HeavyCoreBlock.class,
            ShulkerBoxBlock.class
    ));
    public static final Set<Class<?>> COUNT8_BLOCK = new HashSet<>(Arrays.asList(
            SlabBlock.class,
            WallBlock.class,
            DoorBlock.class,
            ConcretePowderBlock.class,
            TerracottaBlock.class,
            TintedParticleLeavesBlock.class,
            TransparentBlock.class,
            KelpBlock.class,
            KelpPlantBlock.class,
            PointedDripstoneBlock.class,
            LadderBlock.class,
            SeaPickleBlock.class,
            CandleBlock.class,
            LanternBlock.class,
            SignBlock.class,
            HangingSignBlock.class,
            ChainBlock.class,
            StainedGlassBlock.class,
            FenceBlock.class,
            OxidizableSlabBlock.class
    ));
    public static final Set<Class<?>> COUNT16_BLOCK = new HashSet<>(Arrays.asList(
            PaneBlock.class,
            RailBlock.class,
            ButtonBlock.class,
            PressurePlateBlock.class,
            SaplingBlock.class,
            CarpetBlock.class,
            AbstractCoralBlock.class,
            NetherWartBlock.class,
            FlowerbedBlock.class,
            ScaffoldingBlock.class,
            SnowBlock.class,
            TorchBlock.class,
            MossBlock.class,
            RedstoneTorchBlock.class,
            PoweredRailBlock.class,
            DetectorRailBlock.class,
            SugarCaneBlock.class,
            BambooBlock.class,
            StainedGlassPaneBlock.class
    ));
    public static final Set<Class<?>> COUNT32_BLOCK = new HashSet<>(Collections.singletonList(
            FungusBlock.class
    ));
    //item的依赖名字分类最大堆叠
    public static final Set<String> COUNT1_ITEM = new HashSet<>(Arrays.asList(
            "heart_of_the_sea",
            "nether_star"
    ));
    public static final Set<String> COUNT8_ITEM = new HashSet<>(Arrays.asList(
            "flint",
            "leather",
            "rabbit_hide",
            "honeycomb",
            "turtle_scute",
            "armadillo_scute",
            "blaze_rod",
            "breeze_rod",
            "shulker_shell",
            "blaze_powder",
            "sugar",
            "rabbit_foot",
            "glistering_melon_slice"
    ));
    public static final Set<String> COUNT16_ITEM = new HashSet<>(Arrays.asList(
            "iron_ingot",
            "copper_ingot",
            "gold_ingot",
            "netherite_ingot",
            "coal",
            "charcoal",
            "emerald",
            "diamond",
            "amethyst_shard",
            "netherite_scrap",
            "wheat",
            "clay_ball",
            "ender_eye",
            "bowl",
            "brick",
            "nether_brick",
            "resin_brick",
            "book",
            "glass_bottle",
            "nether_wart",
            "gunpowder",
            "experience_bottle",
            "wind_charge",
            "lead",
            "firework_rocket",
            "slime_ball",
            "raw_copper",
            "raw_gold",
            "raw_iron",
            "resin_clump"

    ));
    public static final Set<String> COUNT32_ITEM = new HashSet<>(Arrays.asList(
            "lapis_lazuli",
            "quartz",
            "iron_nugget",
            "gold_nugget",
            "stick",
            "bone",
            "bone_meal",
            "string",
            "feather",
            "cnowball",
            "paper",
            "redstone",
            "glowstone_dust"
    ));
	@Override
	public void onInitialize() {
		LOGGER.info("mext");
		AtItems.init();
		AtBlocks.init();
	}
}