package com.acuteterror233.mite.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the generated overrides that hide the vanilla {@code minecraft:story}
 * advancement tab.
 *
 * <p>MME rebuilds the whole story progression under {@code mme:story} while reusing
 * vanilla translation keys ({@code advancements.story.*}); to keep the vanilla story
 * tab from coexisting, datagen emits a same-path display-less override (impossible
 * criterion) for every {@code minecraft:story/*} advancement. The authoritative ID
 * list lives in {@code MMEAdvancementProvider#VANILLA_STORY_OVERRIDE_IDS} (client
 * source set, invisible to this test source set) — the hardcoded list below must be
 * kept in sync with it.</p>
 */
class VanillaStoryOverrideTest {

    /** Mirror of {@code MMEAdvancementProvider#VANILLA_STORY_OVERRIDE_IDS} (keep in sync). */
    private static final List<String> VANILLA_STORY_IDS = List.of(
            "root", "mine_stone", "upgrade_tools", "smelt_iron", "obtain_armor",
            "iron_tools", "form_obsidian", "mine_diamond", "enchant_item", "shiny_gear",
            "lava_bucket", "enter_the_nether", "follow_ender_eye", "enter_the_end",
            "deflect_arrow", "cure_zombie_villager"
    );

    /** Generated overrides for the vanilla story advancements. */
    private static final Path VANILLA_STORY_DIR =
            Paths.get("src/main/generated/data/minecraft/advancement/story");
    /** Generated MME story advancements that replace the vanilla ones. */
    private static final Path MME_STORY_DIR =
            Paths.get("src/main/generated/data/mme/advancement/story");
    /** The only vanilla story advancement with no MME counterpart of its own. */
    private static final String NO_MME_COUNTERPART = "deflect_arrow";

    private static final String VANILLA_KEY_PREFIX = "advancements.story.";

    /**
     * The generated {@code data/minecraft/advancement/story} directory must contain
     * exactly the vanilla 26.3 story advancement set — no more, no less.
     */
    @Test
    void overrideFiles_matchVanillaStorySetExactly() throws IOException {
        assertTrue(Files.isDirectory(VANILLA_STORY_DIR),
                "Missing generated vanilla story overrides — run runDatagen");
        assertEquals(Set.copyOf(VANILLA_STORY_IDS), generatedIds(VANILLA_STORY_DIR),
                "Generated minecraft:story overrides must cover the vanilla 26.3 story set exactly");
    }

    /**
     * Every override must be JSON-parseable, carry an {@code impossible} criterion and
     * have no {@code display} section (the mechanism that hides the vanilla tab).
     */
    @Test
    void overrideJson_isDisplaylessWithImpossibleCriterion() throws IOException {
        for (String id : VANILLA_STORY_IDS) {
            Path file = VANILLA_STORY_DIR.resolve(id + ".json");
            JsonObject root = parse(file);
            assertTrue(root.has("criteria"), id + " override must define criteria");
            JsonObject criteria = root.getAsJsonObject("criteria");
            assertTrue(criteria.has("impossible"), id + " override must use the impossible criterion");
            assertFalse(root.has("display"), id + " override must be display-less");
        }
    }

    /**
     * Every overridden vanilla story advancement must have a same-named MME counterpart
     * (so the progression content still exists), except the exempt {@code deflect_arrow}.
     */
    @Test
    void everyVanillaStoryIdHasMmeCounterpartExceptExempt() {
        for (String id : VANILLA_STORY_IDS) {
            if (NO_MME_COUNTERPART.equals(id)) {
                continue;
            }
            Path mmeFile = MME_STORY_DIR.resolve(id + ".json");
            assertTrue(Files.isRegularFile(mmeFile),
                    "Vanilla story id '" + id + "' is overridden but has no mme:story counterpart");
        }
    }

    /**
     * MME story advancements may reuse vanilla {@code advancements.story.*} translation
     * keys only for story IDs that are actually overridden — otherwise a vanilla
     * advancement with the same key would still be visible in its own tab.
     */
    @Test
    void mmeStoryTranslations_onlyReuseOverriddenVanillaKeys() throws IOException {
        assertTrue(Files.isDirectory(MME_STORY_DIR), "Missing generated mme story advancements");
        try (Stream<Path> files = Files.list(MME_STORY_DIR)) {
            for (Path file : files.collect(Collectors.toList())) {
                JsonObject root = parse(file);
                if (!root.has("display")) {
                    continue;
                }
                JsonObject display = root.getAsJsonObject("display");
                for (String field : List.of("title", "description")) {
                    if (!display.has(field)) {
                        continue;
                    }
                    JsonElement translate = display.getAsJsonObject(field).get("translate");
                    if (translate == null) {
                        continue;
                    }
                    String key = translate.getAsString();
                    if (!key.startsWith(VANILLA_KEY_PREFIX)) {
                        continue;
                    }
                    String id = key.substring(VANILLA_KEY_PREFIX.length())
                            .replaceAll("\\.(title|description)$", "");
                    assertTrue(VANILLA_STORY_IDS.contains(id),
                            file.getFileName() + " reuses vanilla key '" + key
                                    + "' whose story id '" + id + "' is not overridden");
                }
            }
        }
    }

    /** Lists the JSON file basenames (without extension) of the given directory. */
    private static Set<String> generatedIds(Path dir) throws IOException {
        try (Stream<Path> files = Files.list(dir)) {
            return files.map(p -> p.getFileName().toString())
                    .filter(name -> name.endsWith(".json"))
                    .map(name -> name.substring(0, name.length() - ".json".length()))
                    .collect(Collectors.toSet());
        }
    }

    /** Parses a generated advancement JSON file into a {@link JsonObject}. */
    private static JsonObject parse(Path file) throws IOException {
        assertTrue(Files.isRegularFile(file), "Missing generated JSON: " + file);
        String raw = Files.readString(file, StandardCharsets.UTF_8);
        return JsonParser.parseString(raw).getAsJsonObject();
    }
}
