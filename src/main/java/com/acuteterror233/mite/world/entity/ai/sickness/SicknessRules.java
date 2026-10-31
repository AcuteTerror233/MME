package com.acuteterror233.mite.world.entity.ai.sickness;

import com.acuteterror233.mite.MME;
import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Data-driven configuration for the animal sickness system. On first run the bundled
 * template ({@code data/mme/animal_sickness_rules.json} inside the mod jar) is exported
 * to {@code config/MME/animal_sickness_rules.json} in the game directory; from then on
 * the config file is the single source of truth and can be edited by players. A missing
 * or broken config file falls back to the defaults below.
 */
public final class SicknessRules {
    /** Path of the bundled template json inside the mod jar. */
    private static final String BUNDLED_TEMPLATE_PATH = "/data/mme/animal_sickness_rules.json";
    /** File name of the player-editable config inside {@code config/MME}. */
    private static final String CONFIG_FILE_NAME = "animal_sickness_rules.json";

    /** How a large animal satisfies its grazing need. */
    public enum GrassKind {
        /** No grazing need (chicken, rabbit). */
        NONE,
        /** Eats a grown grass block, turning it into dirt (sheep, pig). */
        GRASS_BLOCK,
        /** Eats short or tall grass plants (cow, mooshroom). */
        GRASS_PLANT;

        /** @return the kind named by {@code name}, or {@link #NONE} when unknown. */
        public static GrassKind of(@Nullable String name) {
            if (name == null) {
                return NONE;
            }
            try {
                return valueOf(name.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                return NONE;
            }
        }
    }

    /** Ticks between two sickness checks (staggered per entity id). */
    public int checkIntervalTicks = 40;
    /** Progress at which a need makes the animal sick. */
    public int sickThreshold = 600;
    /** Progress below which a sick animal recovers (hysteresis band). */
    public int recoveredThreshold = 400;
    /** Progress at which the animal starts actively seeking to fulfil a need. */
    public int goalTriggerThreshold = 240;
    /** Minimum ticks between falling sick and dying of the untreated sickness. */
    public long deathDelayMinTicks = 24000L;
    /** Maximum ticks between falling sick and dying of the untreated sickness. */
    public long deathDelayMaxTicks = 36000L;
    /** Progress gained per check while a need is unmet. */
    public int progressGainPerCheck = 1;
    /** Progress gained per check under heavy crowding. */
    public int crowdHeavyGainPerCheck = 2;
    /** Progress lost per check while a need is met. */
    public int progressRecoverPerCheck = 2;
    /** Radius (blocks) of the crowding census around each animal. */
    public double crowdRadius = 5.0D;
    /** Animal count that starts accumulating crowding progress. */
    public int crowdLimit = 8;
    /** Animal count that switches crowding to the heavy rate. */
    public int crowdHeavyLimit = 16;
    /** Minimum local raw brightness for light to count as present at night. */
    public int nightLightBrightness = 9;
    /** Horizontal search range (blocks) for the water-drinking goal. */
    public int waterSearchRange = 8;
    /** Ticks a drink quenches thirst for. */
    public int thirstQuenchTicks = 12000;
    /** Ticks a meal satisfies the grazing need for. */
    public int grazeQuenchTicks = 12000;
    /** Speed modifier for the need-seeking goals. */
    public double needGoalSpeed = 1.0D;
    /** Enabled animals by registry id mapped to their grazing kind. */
    public Map<String, GrassKind> animals = new HashMap<>();

    private static final SicknessRules INSTANCE = load();

    /** @return the shared rules instance. */
    public static SicknessRules get() {
        return INSTANCE;
    }

    private static SicknessRules load() {
        Gson gson = new Gson();
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("MME").resolve(CONFIG_FILE_NAME);
        // First run: export the bundled template so players have a file to edit
        if (Files.notExists(configFile)) {
            exportTemplate(configFile);
        }
        if (Files.isRegularFile(configFile)) {
            try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(configFile), StandardCharsets.UTF_8)) {
                SicknessRules rules = gson.fromJson(reader, SicknessRules.class);
                if (rules != null) {
                    rules.normalize();
                    MME.LOGGER.info("Loaded animal sickness rules from {}", configFile);
                    return rules;
                }
            } catch (Exception exception) {
                MME.LOGGER.error("Failed to load {} from config/MME, using bundled defaults", CONFIG_FILE_NAME, exception);
            }
        }
        SicknessRules fallback = new SicknessRules();
        fallback.normalize();
        return fallback;
    }

    /** Copies the bundled template json into {@code config/MME}, tolerating a read-only setup. */
    private static void exportTemplate(Path configFile) {
        try (InputStream stream = SicknessRules.class.getResourceAsStream(BUNDLED_TEMPLATE_PATH)) {
            if (stream == null) {
                MME.LOGGER.warn("Bundled {} template missing, no config/MME file is created", BUNDLED_TEMPLATE_PATH);
                return;
            }
            Files.createDirectories(configFile.getParent());
            Files.copy(stream, configFile);
            MME.LOGGER.info("Exported animal sickness rules template to {}", configFile);
        } catch (Exception exception) {
            MME.LOGGER.warn("Could not export {} to config/MME", CONFIG_FILE_NAME, exception);
        }
    }

    /** Fills in the vanilla animal set when the json omits it, and repairs the interval,
     *  threshold and death-delay fields when their values are invalid. */
    private void normalize() {
        if (animals == null) {
            animals = new HashMap<>();
        }
        putIfAbsent(animals, "minecraft:chicken", GrassKind.NONE);
        putIfAbsent(animals, "minecraft:rabbit", GrassKind.NONE);
        putIfAbsent(animals, "minecraft:pig", GrassKind.GRASS_BLOCK);
        putIfAbsent(animals, "minecraft:sheep", GrassKind.GRASS_BLOCK);
        putIfAbsent(animals, "minecraft:cow", GrassKind.GRASS_PLANT);
        putIfAbsent(animals, "minecraft:mooshroom", GrassKind.GRASS_PLANT);
        if (checkIntervalTicks <= 0) {
            checkIntervalTicks = 40;
        }
        if (sickThreshold <= 0) {
            sickThreshold = 600;
        }
        if (recoveredThreshold >= sickThreshold) {
            recoveredThreshold = sickThreshold - 200;
        }
        if (goalTriggerThreshold < 0) {
            goalTriggerThreshold = 240;
        }
        if (deathDelayMinTicks <= 0L) {
            deathDelayMinTicks = 24000L;
        }
        if (deathDelayMaxTicks < deathDelayMinTicks) {
            deathDelayMaxTicks = deathDelayMinTicks;
        }
    }

    private static void putIfAbsent(Map<String, GrassKind> animals, String id, GrassKind kind) {
        animals.putIfAbsent(id, kind);
    }

    /** @return the grazing kind configured for the given animal's registry id. */
    public GrassKind grassKind(Identifier animalId) {
        return animals.getOrDefault(animalId.toString(), GrassKind.NONE);
    }

    /** @return whether the animal id is enabled for the sickness system. */
    public boolean isEnabled(Identifier animalId) {
        return animals.containsKey(animalId.toString());
    }
}
