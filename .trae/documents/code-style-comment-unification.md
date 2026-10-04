# 全项目代码风格统一与注释补全计划

## Context

用户要求对 MME 全项目（213 个 Java 源文件：main 170+、client 38+、test 若干）做系统性风格统一与注释补全。已确认三项决策：

1. **格式化方式**：不引入 Spotless 等插件，仅手动/脚本修正（build.gradle 保持不动）
2. **行尾策略**：全部统一为 **LF**（当前 187 CRLF / 26 LF），根治 AGENTS.md 记录的 CRLF 多行编辑失配痛点
3. **注释语言**：**英文**（延续上一轮中文→英文转换成果；全项目中文注释残留仅 GradeCraftingTableBlock.java 一处，MMELanguageProvider 中的中文是 zh_cn 数据字符串，不是注释，不动）

现状基线：4 空格缩进为主流；仅 4 个文件含行首 tab；Javadoc 基准范式 = `MetalMaterial.java`、`TimedCraftingSession.java`（英文、类级 + 方法级）。

## 实施步骤

### Phase A：行尾统一为 LF

1. 编辑 `.gitattributes`，追加：
   ```
   *.java text eol=lf
   ```
   （保留现有 gradlew=LF、*.bat=CRLF 规则）
2. 脚本批量转换 `src/main/java`、`src/client/java`、`src/test/java` 下全部 `.java`：CRLF→LF，UTF-8 无 BOM 写回（`[System.IO.File]::WriteAllText` + `UTF8Encoding($false)`），保留中文数据字符串编码。不触碰 `src/main/generated`（纯 JSON）。
3. 转换后 `git status` 确认只有行尾差异。

### Phase B：格式手动修正

1. 4 个 tab 缩进文件转 4 空格：`world/food/FoodNutrition.java`、`MMEClient.java`、`MMEDataGenerator.java`、`datagen/MMEVillagerTradeProvider.java`（仅行首 tab，防误伤字符串字面量）。
2. 全部 .java 清除行尾空白（脚本，逐行 rtrim，不动内容结构）。
3. 大括号/空格不做全局重排（抽样显示已基本一致）；在 Phase C 逐文件处理时顺手修正明显 outlier 与明显未使用 import。

### Phase C：英文注释补全（核心工作量）

对 213 个文件分 6 批，用并行子代理（general-purpose）执行，统一规则写入每个代理的 prompt：

**注释规则（英文，Javadoc 风格对齐 MetalMaterial/TimedCraftingSession）**：
- 每个类/接口/枚举：类级 Javadoc（职责一句话 + 必要的机制说明，如 mixin 的注入目标与副作用）
- public/protected 方法：Javadoc 含功能描述、`@param`（含义）、`@return`（含义）；private 复杂方法用单行 Javadoc 或 // 说明
- 复杂逻辑块：行内 `//` 解释设计思路（如 mixin 中的同步策略、字节码对齐原因、限制检查的豁免语义）
- 常量/静态配置：说明用途与取值范围（如 `MME.FILTER_RECIPE_SET`、`CORRESPONDING_COMBUSTION_GRADE`、mixin 内 `mme$MOON_INFLUENCE_INTENSITY_PER_PHASE` 等）
- **硬约束**：不得改变任何代码语义（defaultRequire=1 下 mixin 尤其敏感）；只增改注释与纯空白；已有合格注释只查缺补漏；不碰 `src/main/generated`；不碰 zh_cn 语言数据字符串；文件现为 LF，多行编辑锚点可用

**批次划分**：
1. 根包 + `registry/` + `interfaces/`（MME.java、duck 接口等）
2. `mixin/world/item/crafting/` + `mixin/world/inventory/` + `inventory/`（合成系统核心）
3. `material/` + `block/`（含 GradeCraftingTableBlock 的中文 Javadoc 转英文）
4. `mixin/world/level/` + `mixin/world/entity/` + `world/`（月相/时间线/食物）
5. `entity/` + `item/` + 其余 main 包（datacomponents、renderer 等）
6. `src/client/java` 全部（mixin/client、gui、datagen providers、renderer）

每批完成后立即 `git diff --stat` 检查改动面。

### Phase D：验证

1. `.\gradlew build` — 全源集编译 + JUnit 通过（注释/空白不得引入编译错误）
2. `.\gradlew runDatagen` — 确认 `src/main/generated` 零漂移（git status 无新差异）
3. 抽查 5-8 个 mixin 文件：注入注解、@Shadow 签名原样未动
4. `git diff --stat` 汇总报告改动规模

## 风险与对策

- **BOM/编码**：统一以 UTF-8 无 BOM 写回；javac 对 BOM 敏感，去除无害
- **defaultRequire=1**：Phase C 明令禁止改代码；Phase D 抽查兜底
- **datagen 漂移**：只改 .java 注释不影响生成 JSON，Phase D 复核
- **批量脚本误伤**：LF 转换限定 `*.java` 三个源集目录，行尾空白清理跳过字符串含行尾空白的极端情况（Java 源中罕见，多行字符串 text block 若存在则该文件单独处理）

## 交付物

- 全项目 LF 统一 + `.gitattributes` 规则
- 4 个 tab 文件转空格、行尾空白清理
- 213 个文件英文注释全覆盖（缺啥补啥，已有合格注释保留）
- build + datagen 验证通过
