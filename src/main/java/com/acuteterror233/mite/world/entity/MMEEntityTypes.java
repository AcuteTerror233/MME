package com.acuteterror233.mite.world.entity;

import com.acuteterror233.mite.world.entity.monster.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * MME mod entity type registration center.
 * Registers all custom entities (ghoul, shadow, wight, fire elemental, infernal creeper, etc.) and their default attributes.
 */
public class MMEEntityTypes {
    /** Ground monster, standard humanoid footprint; spawns via {@link Monster#checkMonsterSpawnRules}. */
    public static final EntityType<Ghoul> GHOUL = register(
            MMEEntityTypeIds.GHOUL,
            EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: lurking humanoid that blends into darkness. */
    public static final EntityType<Shadow> SHADOW = register(
            MMEEntityTypeIds.SHADOW,
            EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: undead spellcaster. */
    public static final EntityType<Wight> WIGHT = register(
            MMEEntityTypeIds.WIGHT,
            EntityType.Builder.of(Wight::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: humanoid that is invisible to players until it attacks. */
    public static final EntityType<InvisibleStalker> INVISIBLE_STALKER = register(
            MMEEntityTypeIds.INVISIBLE_STALKER,
            EntityType.Builder.of(InvisibleStalker::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: large spider with demon traits. */
    public static final EntityType<DemonSpider> DEMON_SPIDER = register(
            MMEEntityTypeIds.DEMON_SPIDER,
            EntityType.Builder.of(DemonSpider::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F)
                    .eyeHeight(0.65F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: small spider that teleports/phases toward its target. */
    public static final EntityType<PhaseSpider> PHASE_SPIDER = register(
            MMEEntityTypeIds.PHASE_SPIDER,
            EntityType.Builder.of(PhaseSpider::new, MobCategory.MONSTER)
                    .sized(0.7F, 0.5F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: creeper variant with an explosive fire blast. */
    public static final EntityType<InfernalCreeper> INFERNAL_CREEPER = register(
            MMEEntityTypeIds.INFERNAL_CREEPER,
            EntityType.Builder.of(InfernalCreeper::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .eyeHeight(1.62F)
                    .passengerAttachments(1.2F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Ground monster: fire-immune elemental that hurls fire and seeks lava. */
    public static final EntityType<FireElemental> FIRE_ELEMENTAL = register(
            MMEEntityTypeIds.FIRE_ELEMENTAL,
            EntityType.Builder.of(FireElemental::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .fireImmune()
                    .notInPeaceful()
    );
    /** Flying blood-drinking bat; unrestricted spawn placement ({@code NO_RESTRICTIONS}) with custom spawn rules. */
    public static final EntityType<VampireBat> VAMPIRE_BAT = register(
            MMEEntityTypeIds.VAMPIRE_BAT,
            EntityType.Builder.of(VampireBat::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Flying night predator; unrestricted spawn placement with {@link VampireBat} spawn rules. */
    public static final EntityType<Nightwing> NIGHTWING = register(
            MMEEntityTypeIds.NIGHTWING,
            EntityType.Builder.of(Nightwing::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.45F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );
    /** Enlarged vampire bat boss variant; same unrestricted spawn placement as {@link VampireBat}. */
    public static final EntityType<GiantVampireBat> GIANT_VAMPIRE_BAT = register(
            MMEEntityTypeIds.GIANT_VAMPIRE_BAT,
            EntityType.Builder.of(GiantVampireBat::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F)
                    .eyeHeight(0.6749999821186066F)
                    .clientTrackingRange(8)
                    .notInPeaceful()
    );

    /** Builds the entity type from the builder and registers it under {@code key}. */
    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    /**
     * Entry hook: wires default attributes for every custom entity and registers spawn
     * placements — ground monsters use {@code ON_GROUND} + {@link Monster#checkMonsterSpawnRules},
     * flying bats use {@code NO_RESTRICTIONS} + {@link VampireBat#checkVampireBatSpawnRules}.
     */
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