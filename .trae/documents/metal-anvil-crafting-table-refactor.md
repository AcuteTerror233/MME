# 工作台/铁砧 Mixin 化重构 + 金属材料抽象 — 实施计划

> 状态：方案已获批准并实施中。步骤 1、2 已完成，步骤 3 的服务端部分已完成；
> 本版计划整合了当前进度、实施中发现的修正与剩余文件级操作清单。

## Context（背景与目标）

用户要求：

1. 工作台改用 **Mixin 设计模式**实现，**不再单独添加 Menu 组件**（删除 `GRADE_CRAFTING_TABLE` MenuType，改为改造原版 `CraftingMenu`）。
2. 工作台与铁砧**一并重构**，砧同样去掉自定义 MenuType（`GRADE_ANVIL`），改造原版 `AnvilMenu`。
3. 设计**金属材料抽象**（`MetalMaterial`），统一管理工作台与砧的功能效果。
4. 材质可**仅砧 / 仅工作台 / 两者兼有**（现状恰好三种都有：NETHERITE 仅砧、FLINT/OBSIDIAN 仅工作台、其余两者兼有）。

顺带收益：消除 `InventoryMenuMixin` 与 `AbstractGradeCraftingMenu` 约 100 行的重复计时逻辑（此前代码审查已列为 P1 问题）。

## 当前进度（以磁盘实态核对为准）

| 步骤 | 内容                                                                                                     | 状态                              |
| -- | ------------------------------------------------------------------------------------------------------ | ------------------------------- |
| 1  | `MetalMaterial`/`MMEMaterials` + `MetalMenuExtension`/`TimedCraftingMenuExtension` + classTweaker 三行注入 | ✅ 已完成（已核对）                      |
| 2  | `TimedCraftingSession` + `TimedCraftingResultSlot`                                                     | ✅ 已完成（含 1 处编译修正，见下）             |
| 3a | `CraftingMenuMixin`（服务端，主源集）                                                                           | ✅ 已完成（已核对，未注册进 mme.mixins.json） |
| 3b | 客户端 `CraftingScreenMixin` + mme.client.mixins.json 注册                                                  | ⬜ 待做                            |
| 4  | `AnvilMenuMixin`                                                                                       | ⬜ 待做                            |
| 5  | `InventoryMenuMixin` 瘦身 + `InventoryScreenMixin` 更新 + mme.mixins.json 注册两个新 mixin                      | ⬜ 待做                            |
| 6  | 方块层替换 + MMEMenuTypes/MMEClient 清理 + 删 8 个旧类                                                            | ⬜ 待做                            |
| 7  | `runDatagen` + `./gradlew build`（含单测）                                                                  | ⬜ 待做                            |
| 8  | 更新 `docs/MME-原版改动点总览.md`                                                                               | ⬜ 待做                            |

## 已验证的关键事实

**项目现状**

* 工作台：9 个 `GradeCraftingTableBlock`（[MMEBlocks.java L158-202](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/MMEBlocks.java#L158-L202)）+ `GradeCraftingTableMenu` + `GradeCraftingTableScreen`。

* 砧：7 组 `AnvilCollection`（intact/chipped/damaged，[AnvilCollection.java L53-69](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/AnvilCollection.java#L53-L69)）+ `VanillaBlockModify` 把原版 anvil 三方块换成 IRON tag 的 `MMEAnvilBlock` + `AnvilBlockEntity`（ANVIL\_MAP 损坏链 + NBT）+ `GradeAnvilScreen`（原版 AnvilScreen 克隆）。

* 背包 2×2：`InventoryMenuMixin`（含手写 updateResult + 手动发包，待瘦身）+ `PlayerCraftingResultSlot`。

* `MMEClient` L20-21 绑定两个自定义 Screen；`MMEMenuTypes` L17-18 注册两个待删 MenuType（保留 `MME_ENCHANTMENT`）。

* MixinExtras（`@ModifyExpressionValue` 等）项目内已多处使用，可用。

**原版 26.3（javap 字节码验证，Mojang 映射）**

* `AnvilMenu`：字段 `private final DataSlot cost`、`private int repairItemCountCost`、`private boolean onlyRenaming`、`private String itemName`；方法 `createResult()`、`onTake(Player,ItemStack)`、`setItemName(String)`、`getCost()`。`createResult` 中 `isValidRepairItem` 调用点**仅 1 处**，整型常量 4 **恰好 2 处**（均为修复除数）。

* **`AnvilMenu`** **不声明** **`stillValid`**（继承自 `ItemCombinerMenu`）→ 不能 @Inject，必须用 mixin **新增 override 方法**（方法合并）。

* `CraftingMenu extends AbstractCraftingMenu`：`AbstractCraftingMenu` 有具体 `addResultSlot(Player,int,int)`（mixin 可新增 override，`InventoryMenuMixin` 已验证此模式可行）、`protected final craftSlots/resultSlots`、ctor `(MenuType<?>,int,int,int)`。

* `ItemCombinerMenu`：`protected final access/player/inputSlots/resultSlots`。

## 实施中发现的修正（相对初版方案）

1. **AnvilMenuMixin.stillValid**：初版写 @Inject，但 `AnvilMenu` 不声明该方法（javap 验证）→ 改为 mixin **新增 override**：

   ```java
   @Override
   public boolean stillValid(Player player) {
       if (this.mme$metal == null) return super.stillValid(player); // 无金属走原版
       return this.access.evaluate((world, pos) ->
               player.isWithinBlockInteractionRange(pos, 4.0)
                       && world.getBlockState(pos.above()).isAir(), true);
   }
   ```

   若构建期 super 调用报错（mixin 限制），回退方案：复制 `ItemCombinerMenu.stillValid` 原版函数体替代 super 调用。
2. **TimedCraftingSession L197/L203**：`slot.clearRunning()` → `TimedCraftingResultSlot` 未声明该方法，编译会失败 → 改为 `this.clearRunning()`（session 自身方法）。
3. **duck 命名遵循项目现状**：`MME$CamelCase` + default throw（与 `CampfireBlockEntityExtension` 一致），而非初版拟定的 mme\$ 小驼峰；附带收益：`InventoryScreenMixin` 调用零改动。
4. **构建前需 javap 复核的 3 个签名**（失配则按原版实际签名调整）：

   * `AbstractContainerMenu.owner()` 是否存在（`TimedCraftingSession` 依赖；不存在则给 session 构造器显式传 `Player`）；

   * `RecipeHolder.id().identifier()` 返回类型（`TimedCraftingSession.recipeId` 依赖）；

   * `CraftingScreen` 构造器签名（mixin 构造器必须与目标完全一致；26.3 预期 `(CraftingMenu, Inventory, Component)`，父类 4 参 `(menu, recipeBookComponent, inventory, title)`，参照现有 `InventoryScreenMixin`）。

## 目标架构（已批准，保持不变）

### 1. 金属材料抽象（`com.acuteterror233.mite.material`）✅

`MetalMaterial`（crafting/anvil 可空，`craftingOnly/anvilOnly/of` 三工厂）+ `MMEMaterials` 11 个静态实例，参数与旧方块逐一对应（HAND 含 0.3 饥饿）。

### 2. 共享计时逻辑 ✅

`TimedCraftingSession`：`ContainerData` 4 槽 `{progress,total,allow,active}`；`updateResult` 走标准远程槽脏检查（无手动发包）；`tick/evaluateRunning/beginPlacing/finishPlacing/reset`；配方或总时长变化重置进度（修复 P0-2）。`TimedCraftingResultSlot extends ResultSlot`：有金属=启动计时合成返回 EMPTY，无金属=原版行为。

### 3. CraftingMenuMixin ✅（服务端部分）

`@Inject` init/addResultSlot override/slotsChanged/beginPlacingRecipe/finishPlacingRecipe/broadcastChanges/stillValid/quickMoveStack/canTakeItemForPickAll，duck 全实现。已核对磁盘文件与设计一致。

### 3b. 客户端 CraftingScreenMixin（待做）

* [ ] 新建 `src/client/java/com/acuteterror233/mite/mixin/client/gui/screen/ingame/CraftingScreenMixin.java`：

  * `@Mixin(CraftingScreen.class) extends AbstractRecipeBookScreen<CraftingMenu>`；构造器签名 javap 核对后照抄 vanilla（内部 `new CraftingRecipeBookComponent(handler)`）。

  * `@Inject(method="extractBackground", at=@At("RETURN"))`：`((TimedCraftingMenuExtension)menu).MME$HasMetal()` 时画进度条（迁移自 [GradeCraftingTableScreen L44-53](file:///z:/ModProjects/MME/src/client/java/com/acuteterror233/mite/gui/screen/inventory/GradeCraftingTableScreen.java#L44-L53)）：精灵 `container/furnace/burn_progress`(24×16) 画在 `leftPos+90, topPos+34`，宽 `= (int)(MME$GetCraftingTime()*24)`，高 16，`RenderPipelines.GUI_TEXTURED`。

  * `@Override getTooltipFromContainerItem`：`hoveredSlot instanceof TimedCraftingResultSlot && MME$HasMetal() && !MME$IsAllowCrafting()` 时追加 `mme.craftingTable.noAllowedCrafting`。

  * 原版背景/布局零改动（vanilla CraftingScreen 自绘 crafting\_table 背景）。

* [ ] `mme.client.mixins.json` 的 `"client"` 数组追加 `"gui.screen.ingame.CraftingScreenMixin"`。

### 4. AnvilMenuMixin（待做）

新建 `src/main/java/com/acuteterror233/mite/mixin/world/inventory/AnvilMenuMixin.java`：

* `@Mixin(AnvilMenu.class) extends ItemCombinerMenu implements MetalMenuExtension`；ctor `(MenuType<?>, int, Inventory, ContainerLevelAccess)` → super。

* `@Shadow` AnvilMenu 自有字段：`cost`(DataSlot)、`repairItemCountCost`(int)、`onlyRenaming`(boolean)；`access/player/inputSlots/resultSlots` 经继承直取（同 CraftingMenuMixin 模式）。

* `@Unique @Nullable MetalMaterial mme$metal` + duck 实现（仅金属注入，无 session）。

* `@ModifyExpressionValue(method="createResult", at=@At(value="INVOKE", target="Lnet/minecraft/world/item/ItemStack;isValidRepairItem(Lnet/minecraft/world/item/ItemStack;)Z"))`：`metal.anvil()!=null` 时追加 `&& !inputSlots.getItem(1).is(notAllowedRepairMaterials)`。

* `@ModifyConstant(method="createResult", constant=@Constant(intValue=4))`：第二格 `is(MMEItemTags.NUGGET)` 且 `metal.anvil()!=null` → 返回 6，否则原值（两处除数统一 /6，见"行为变化"）。

* `onTake` `@Overwrite`（平移 [GradeAnvilMenu L64-100](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/inventory/GradeAnvilMenu.java#L64-L100)）：扣等级（cost）→ `repairItemCountCost>0` 按量消耗第二格并计耐久差，否则 `!onlyRenaming` 清第二格 → `access.execute`：`AnvilBlockEntity.addDamage(i)`、达到 maxDamage 时 ServerPlayer closeContainer、`levelEvent(1030)` → `cost.set(0)` → 清第一格。（整体替换即天然抑制原版 AnvilBlock.damage 退化路径，由 BE 接管。）

* mixin 新增 `stillValid` override（见"修正 1"）。

* `mayPickup`/`setItemName` 原版已等价，不动。

* 注册进 `mme.mixins.json`。

### 5. InventoryMenuMixin 瘦身 + InventoryScreenMixin（待做）

* [InventoryMenuMixin.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/mixin/world/inventory/InventoryMenuMixin.java) 重写：

  * `implements TimedCraftingMenuExtension`（删 `InventoryMenuExtension`）；删除 property/CraftingTime/updateResult/isAllowedCrafting/additionalCraftingTime/DefaultCraftingTime/手动发包。

  * 持有 `TimedCraftingSession`（懒创建，同 CraftingMenuMixin）+ `mme$metal`；ctor RETURN 注入中 `setMetal(MMEMaterials.HAND)` + `addDataSlots(session.data())`。

  * `addResultSlot` override → `TimedCraftingResultSlot`（PlayerCraftingResultSlot 退场）。

  * `slotsChanged` 由 `@Overwrite` 改 `@Inject(HEAD, cancellable)`：hasMetal 且 `inventory == craftSlots` 且 !filling 且 ServerLevel → updateResult，cancel（恢复 vanilla 的容器守卫）。

  * `broadcastChanges` 整体 override 改 `@Inject(HEAD)` → `session.tick()`；`removed` 注入改调 `session.reset()`；`quickMoveStack` 注入换 `TimedCraftingResultSlot`；duck 5 方法全部委托 session。

* [InventoryScreenMixin.java](file:///z:/ModProjects/MME/src/client/java/com/acuteterror233/mite/mixin/client/gui/screen/ingame/InventoryScreenMixin.java)：仅把 `PlayerCraftingResultSlot` 换成 `TimedCraftingResultSlot`（duck 方法名不变，零其他改动）。

### 6. 方块层替换 + 清理（待做）

* [GradeCraftingTableBlock.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/GradeCraftingTableBlock.java)：字段 tag×2+float → `private final MetalMaterial metal` + `public MetalMaterial metal()`；`getMenuProvider` 开 **原版** **`CraftingMenu`** 并 `((TimedCraftingMenuExtension) menu).MME$SetMetalMaterial(this.metal)`（lambda 内先 new 后注入，早于 initMenu 广播）。

* [MMEAnvilBlock.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/MMEAnvilBlock.java)：字段 TagKey → `MetalMaterial`；`getMenuProvider` 开 **原版** **`AnvilMenu`** + duck 注入；方块实体/退化/getDrops/setPlacedBy 不动。

* [AnvilCollection.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/AnvilCollection.java) L53-69：`registerBlocks` 参数 `TagKey<Item>` → `MetalMaterial`。

* [MMEBlocks.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/MMEBlocks.java)：7 组砧（L84-104）→ `MMEMaterials.NETHERITE/ADAMANTIUM/MITHRIL/ANCIENT_METAL/GOLD/SILVER/COPPER`；9 工作台（L158-202）→ 对应 `MMEMaterials.*`。

* [VanillaBlockModify.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/VanillaBlockModify.java) L30-32：三个原版砧 → `MMEMaterials.IRON`（L33 crafting\_table 保持 `Block::new`）。

* [MMEMenuTypes.java](file:///z:/ModProjects/MME/src/main/java/com/acuteterror233/mite/block/MMEMenuTypes.java)：删 `GRADE_ANVIL`/`GRADE_CRAFTING_TABLE`（保留 `MME_ENCHANTMENT`）。

* [MMEClient.java](file:///z:/ModProjects/MME/src/client/java/com/acuteterror233/mite/MMEClient.java)：删 L20-21 两行 register 及对应 import。

* 删 8 个旧文件：`AbstractGradeCraftingMenu`、`GradeCraftingTableMenu`、`GradeAnvilMenu`、`slot/CraftingTableResultSlot`、`slot/PlayerCraftingResultSlot`、`interfaces/InventoryMenuExtension`、client `GradeAnvilScreen`、client `GradeCraftingTableScreen`。

* 注册进 `mme.mixins.json`：`world.inventory.CraftingMenuMixin`、`world.inventory.AnvilMenuMixin`。

### 7. 验证（待做）

* javap 复核 3 个签名（见"修正 4"）后 `./gradlew build`（含 JUnit）；每完成一个 mixin/步骤即构建（已核对到目前）。

* `./gradlew runDatagen`：确认生成物无引用漂移，diff 提交。

* `runServer` 冒烟：mixin 全部应用、无 defaultRequire 崩载。

* `runClient` 手动矩阵：原版工作台/铁砧行为不变；等级工作台（启动/计时/进度条/禁造提示/shift 结果槽/同总时长换料重置）；2×2 背包合成；铁砧（禁用材料修复被拒/改名/附魔合并/耐久消耗/损坏退化链/原版铁砧=IRON）；各金属开菜单一次。

### 8. 文档（待做）

更新 `docs/MME-原版改动点总览.md`：Mixin 清单增删（+CraftingMenuMixin/+AnvilMenuMixin/InventoryMenuMixin 描述更新，客户端 +CraftingScreenMixin）、MenuType 注册条目删除、注册时 modify 条目更新。

## 行为变化（唯一可见差异）

1. **铁砧粒修复**：现代码首格按 maxDamage/6、后续按 /4（不一致，疑似笔误）；统一为 /6（两处除数一致），对铁砧粒修复是轻微削弱。
2. 其余全部保真：计时、限制、速度公式、等级校验（方块+4.0 距离+上方空气）、砧损伤/退化、2×2 玩法均不变。

## 风险与对策

| 风险                                      | 对策                                                              |
| --------------------------------------- | --------------------------------------------------------------- |
| `defaultRequire=1`（@Shadow/注入失配即崩载）     | @Shadow 字段名与注入点已 javap 验证；每步立即 `./gradlew build`                |
| `createResult` pinpoint 注入随原版升级漂移       | 注入点单一（1 处 isValidRepairItem、2 处常量 4）；onTake @Overwrite 是唯一整方法替换 |
| mixin 新增 stillValid override 的 super 调用 | 若报错则复制原版函数体替代（见"修正 1"）                                          |
| `menu.owner()`/`RecipeHolder.id()` 签名漂移 | 构建前 javap 复核（见"修正 4"）                                           |
| 删 MenuType 影响存档/datagen                 | MenuType 不持久化（安全）；runDatagen 确认无生成物引用                           |
| CRLF 文件多行编辑失配                           | 全程短单行锚点编辑                                                       |

