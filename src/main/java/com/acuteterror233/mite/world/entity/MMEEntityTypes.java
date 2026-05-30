package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.entity.monster.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * MME 模组实体类型注册中心。
 * 注册所有自定义实体（食尸鬼、暗影、尸鬼、火元素、炼狱苦力怕等）及其默认属性。
 */
public class MMEEntityTypes {
    public static final EntityType<Ghoul> GHOUL = register(
            "ghoul",
            EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<Shadow> SHADOW = register(
            "shadow",
            EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<Wight> WIGHT = register(
            "wight",
            EntityType.Builder.of(Wight::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<InvisibleStalker> INVISIBLE_STALKER = register(
            "invisible_stalker",
            EntityType.Builder.of(InvisibleStalker::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<DemonSpider> DEMON_SPIDER = register(
            "demon_spider",
            EntityType.Builder.of(DemonSpider::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<PhaseSpider> PHASE_SPIDER = register(
            "phase_spider",
            EntityType.Builder.of(PhaseSpider::new, MobCategory.MONSTER)
                    .sized(0.7F, 0.5F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<InfernalCreeper> INFERNAL_CREEPER = register(
            "infernal_creeper",
            EntityType.Builder.of(InfernalCreeper::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .eyeHeight(1.62F)
                    .passengerAttachments(1.2F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<FireElemental> FIRE_ELEMENTAL = register(
            "fire_elemental",
            EntityType.Builder.of(FireElemental::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .fireImmune()
    );
    public static final EntityType<VampireBat> VAMPIRE_BAT = register(
            "vampire_bat",
            EntityType.Builder.of(VampireBat::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<Nightwing> NIGHTWING = register(
            "nightwing",
            EntityType.Builder.of(Nightwing::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
    );
    public static final EntityType<GiantVampireBat> GIANT_VAMPIRE_BAT = register(
            "giant_vampire_bat",
            EntityType.Builder.of(GiantVampireBat::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.6749999821186066F)
                    .clientTrackingRange(8)
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.GHOUL, Ghoul.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.SHADOW, Shadow.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.WIGHT, Wight.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.INVISIBLE_STALKER, InvisibleStalker.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.DEMON_SPIDER, DemonSpider.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.PHASE_SPIDER, PhaseSpider.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.INFERNAL_CREEPER, InfernalCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.FIRE_ELEMENTAL, FireElemental.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.VAMPIRE_BAT, VampireBat.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.NIGHTWING, Nightwing.createAttributes());
        FabricDefaultAttributeRegistry.register(MMEEntityTypes.GIANT_VAMPIRE_BAT, GiantVampireBat.createAttributes());

        SpawnPlacements.register(MMEEntityTypes.GHOUL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.SHADOW, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.WIGHT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.INVISIBLE_STALKER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.DEMON_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.PHASE_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.INFERNAL_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.VAMPIRE_BAT, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, VampireBat::checkVampireBatSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.NIGHTWING, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, VampireBat::checkVampireBatSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.GIANT_VAMPIRE_BAT, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, VampireBat::checkVampireBatSpawnRules);
        SpawnPlacements.register(MMEEntityTypes.FIRE_ELEMENTAL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }
}