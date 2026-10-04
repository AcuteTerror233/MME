# 原版进度（advancement）未覆盖问题修复计划

## Context 与根因分析

**问题**：MME 在 `mme:story` / `mme:underground` 命名空间重建了完整的进度树（MITE 式进阶），但游戏内原版 `minecraft:story` 页签仍然存在，两棵 story 树并存——mod 的进度更新从未"覆盖"原版进度。

**用户五点排查的对应结论**：
1. 数据存储机制缺陷 → **确认**：datagen 只输出 `data/mme/advancement/`，`data/minecraft/advancement/` 覆盖完全缺失（生成目录与手工 resources 目录均无）；没有任何机制抑制原版 story 进度
2. 异步更新顺序 → 排除：进度加载为数据包同步加载，mod 数据包同路径文件优先于原版，问题不在加载时序
3. 比较算法 → 确认为 **ID 对齐缺失**：MME 复用原版翻译键（如 `advancements.story.smelt_iron.title`）在 mme 命名空间重建，但原版侧 `minecraft:story/*` 的 17 个 JSON 原样保留
4. 缓存/状态 → 排除：`src/main/generated` 已提交且 datagen 零漂移，非缓存问题
5. 附带确认：34 个被 RecipeMapMixin 过滤的原版配方，其解锁进度（本就无 display）指向不存在的配方，授权时静默失败，无可见危害，不在本次范围

**佐证**：MME story 链 16 个进度与原版 17 个 story 文件一一对应（仅 `deflect_arrow` 无 mme 对应物）；原版 26.3 jar 实测清单（data/minecraft/advancement/story/ 共 17 文件）。

## 修复方案

用 datagen 生成对原版 story 进度的**同路径覆盖**：将原版 story 页签整体隐藏，让 `mme:story` 成为唯一 story 页签。

**隐藏原理**：原版 `recipes/root` 即"无 display 的合法进度"先例——覆盖 JSON 去掉 `display` 字段后该进度不渲染；无 display 的祖先链下的子进度同样不入页签（配方子树即此模式）。用 `minecraft:impossible` 触发器作判据，保证永不完成、无副作用。

### 修改文件

1. **[MMEAdvancementProvider.java](file:///z:/ModProjects/MME/src/client/java/com/acuteterror233/mite/datagen/MMEAdvancementProvider.java)**：
   - 新增公开常量 `VANILLA_STORY_OVERRIDE_IDS`（17 个：root、mine_stone、upgrade_tools、smelt_iron、iron_tools、mine_diamond、obtain_armor、enchant_item、lava_bucket、form_obsidian、shiny_gear、enter_the_nether、follow_ender_eye、enter_the_end、cure_zombie_villager、deflect_arrow 及 root 本身——以 jar 实测清单为准）
   - `generateAdvancement` 尾部新增 `overrideVanillaStory()`：对每个 ID 用 `Advancement.Builder.advancement().addCriterion("impossible", ImpossibleTrigger.TriggerInstance.impossible())` 以 `Identifier.withDefaultNamespace("story/<id>")` 保存（无 display）。若 FabricAdvancementProvider 拒绝非 mod 命名空间，则回退为同 provider 内经 `FabricPackOutput` 直接写 JSON（实现期确认）
   - 类 Javadoc 更新说明覆盖机制
2. **`src/main/generated/data/minecraft/advancement/story/*.json`**（17 个新文件，datagen 产出并提交）
3. **新增测试 `src/test/java/com/acuteterror233/mite/advancement/VanillaStoryOverrideTest.java`**：
   - 覆盖完整性：`VANILLA_STORY_OVERRIDE_IDS` 与 `src/main/generated/data/minecraft/advancement/story/` 实际文件集合一致（不多不少）
   - JSON 合法性：每个文件 Gson 解析通过、含 `criteria`、**不含 `display`**（隐藏语义）
   - 对应关系：除 `deflect_arrow`（无 mme 对应，注释说明）外，每个被覆盖 ID 在 `data/mme/advancement/story/` 有同名 mme 进度
   - 防回归：任何新 mme story 进度若使用原版翻译键 `advancements.story.*`，必须出现在覆盖清单中（扫描 mme story JSON 的 translation key 归属）

## 验证

1. `.\gradlew runDatagen` → 确认 17 个 `data/minecraft/advancement/story/` JSON 生成、git diff 符合预期
2. `.\gradlew test --tests "...VanillaStoryOverrideTest"` 通过
3. `.\gradlew build` 全绿
4. 人工验证（需客户端）：游戏内进度界面只显示 `mme:story` 页签，原版 story 页签消失；nether/end/adventure/husbandry 页签不受影响；旧存档（已获原版 story 进度）不报错
