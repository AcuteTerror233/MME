# 糖尿病 Buff 系统化重构计划

## Context

当前糖尿病负面效果散落在 `FoodDataMixin.handleSugar` 中：按糖分阈值（48000/96000/144000）**永久直加** DARKNESS / BLINDNESS / WITHER + 一枚 INSULIN_RESISTANCE 标记（显示名已是"糖尿病"）。存在既有 bug：`clearAllSugarEffects` 漏清 BLINDNESS，降级时旧效果残留。

用户要求：统一为一个糖尿病 buff（复用 `insulin_resistance`，保 id 兼容存档与语言文件），由 buff 的等级（amplifier）驱动分级症状调度，取消独立散加的原版负面效果。

## 分级机制设计

糖分 → 等级映射（沿用现有阈值字段，`amplifier = tier - 1`，三级及以上封顶 amp 2）：

| 等级 | 条件 | 调度行为 |
|---|---|---|
| 1 级 (amp 0) | sugar > 48000 | 每 600 tick (30s) 施加 **失明 60 tick (3s)** |
| 2 级 (amp 1) | sugar > 96000 | 每 300 tick (15s) 同时施加 **反胃 + 失明，各 60 tick (3s)** |
| ≥3 级 (amp 2) | sugar > 144000 | **永久** 反胃 + 失明 + 凋零（-1 时长，存在性守卫下不重复 add） |

常量（@Unique，注释含取值含义）：`DIABETES_PULSE_INTERVAL_T1 = 600`、`DIABETES_PULSE_INTERVAL_T2 = 300`、`SYMPTOM_PULSE_TICKS = 60`。

## 平滑切换规则

- **升/降级**：先 `clearDiabetesSymptoms`（移除 BLINDNESS/NAUSEA/WITHER 旧症状——同时修复漏清 bug）→ 移除旧 buff 实例 → 按新等级加 `DIABETES(-1, tier-1, ambient=true, particles=false)` → **重置脉冲计时器为整周期**（避免切档瞬间双触发）
- **低于 1 级 / 创造模式**：清 buff + 全部症状
- **3 级永久症状**：每 tick 仅在缺失时补加（`hasEffect` 守卫），无 churn；死亡由原版清效果，NBT sugar 持久化不变
- **脉冲计时器**：`@Unique private int diabetesPulseTimer`，不需要 NBT 持久化（重载后重置为整周期即可）

## 修改文件

1. **FoodDataMixin.java**（唯一核心改动，src/main/java/com/acuteterror233/mite/mixin/world/entity/player/）：
   - 重写 `handleSugar`（L229-245）：阈值判断改为 tier 计算 + 调用新调度器
   - `applyStage`/`clearAllSugarEffects`（L248-263）替换为 `applyDiabetesStage(player, tier)` + `clearDiabetesSymptoms(player)` + `fireDiabetesPulse(player, tier)` 三个 @Unique 方法
   - 类 Javadoc（L39-41 糖分条目）更新为分级脉冲语义；`handleSugar` 等方法注释同步
   - 不动：营养摄取/NBT 持久化/hunger/heal 逻辑、`MME$AddFoodNutrition` 等接口
2. **MMEMobEffects.java**（src/main/java/com/acuteterror233/mite/world/effect/）：仅更新 INSULIN_RESISTANCE 的 Javadoc（说明糖尿病 buff 分级语义与调度器位置），注册不改

不新增文件；无 datagen 触点（复用现有 effect 与 lang 条目）；DARKNESS 从糖尿病管线移除。

## 验证

1. `.\gradlew build`（编译 + JUnit + mixin 加载 GameTest）
2. `runDatagen` 零漂移（本次不改 provider）
3. 人工测试矩阵：
   - 进食高糖食物堆糖分跨过 48000 → 出现"糖尿病"图标，30s 一次 3s 失明脉冲
   - 跨过 96000 → 15s 一次反胃+失明同时脉冲
   - 跨过 144000 → 反胃/失明/凋零永久且不重复刷新粒子
   - 停止进食等糖分降档 → 旧症状即时清除、无残留（重点回归漏清 bug）、计时器重置不双触发
   - 创造模式切生存、退出重进：状态正确恢复/清除
