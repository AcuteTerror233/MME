package com.acuteterror233.mite.mixin.world.level.block;

import com.acuteterror233.mite.block.state.properties.MMEBlockStateProperties;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for {@code CropBlock} — adds the MME crop disease system, fertile-farmland boost and
 * special-moon-phase growth multipliers, applying to every vanilla crop.
 *
 * <p>State space: each crop gains the custom {@code DISEASE_LEVEL} property (0 healthy,
 * 1 infected, 2 withered) next to the vanilla {@code AGE} (registered in
 * {@code createBlockStateDefinition}, defaulted in the constructor inject). Random ticking is
 * retained while a crop is still growing <em>or</em> not fully withered. In {@code randomTick}:
 * healthy crops in bright light grow with a moon-phase multiplier (harvest ×2, blue ×2.5,
 * frost ×0.75, turbid ×0.5 — see {@link SpecialMoonPhase}) and may catch disease (1/5000 per tick,
 * 1/500 during a blood moon; blue moon grants immunity). Dark or infected crops do not grow;
 * infection spreads to the 7 surrounding crop positions (1/5 chance each) and advances to the
 * final withered stage (1/10 chance). {@code getGrowthSpeed} gains +5 when the block below carries
 * MME's {@code FERTILE} property. All growth/disease logic runs server-side via random ticks.</p>
 */
@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends VegetationBlock implements BonemealableBlock {
    /** MME disease stage property (0 healthy, 1 infected, 2 withered). */
    @Unique
    private static final IntegerProperty DISEASE_LEVEL = MMEBlockStateProperties.DISEASE_LEVEL;
    /** Shadowed vanilla age property of the crop. */
    @Shadow
    @Final
    public static IntegerProperty AGE;
    /** Shadowed delegate: the stored age of a crop state. */
    @Shadow
    public abstract int getAge(BlockState blockState);
    /** Shadowed delegate: the maximum age of this crop. */
    @Shadow
    public abstract int getMaxAge();
    /** Shadowed delegate: the block state representing a given age. */
    @Shadow
    public abstract BlockState getStateForAge(int i);
    /** Shadowed delegate: vanilla growth-speed formula (mixin extends its result). */
    @Shadow
    protected static float getGrowthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }
    /** Shadowed delegate: the age property used by this crop subclass. */
    @Shadow
    protected abstract IntegerProperty getAgeProperty();
    /** Shadowed delegate: whether the state is at maximum age. */
    @Shadow
    @Final
    public abstract boolean isMaxAge(BlockState blockState);

    protected CropBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * Injected at the tail of the constructor: adds the healthy disease level (0) to the crop's
     * default state next to the vanilla zero age.
     *
     * @param properties vanilla constructor properties
     * @param ci         injection callback (unused; never cancelled)
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(Properties properties, CallbackInfo ci) {
        this.registerDefaultState(this.stateDefinition.any().setValue(getAgeProperty(), 0).setValue(DISEASE_LEVEL, 0));
    }

    /**
     * Injected at the return of {@code CropBlock#getGrowthSpeed}: adds a flat +5 growth bonus when
     * the block below the crop carries MME's {@code FERTILE} property (fertilized farmland).
     *
     * @param block      the crop block whose growth speed is queried
     * @param blockGetter the level view containing the crop
     * @param blockPos   the crop position
     * @param cir        return callback; set when a fertile bonus applies
     */
    @Inject(method = "getGrowthSpeed", at = @At("RETURN"), cancellable = true)
    private static void growthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Float> cir) {
        BlockState state = blockGetter.getBlockState(blockPos.below());
        if (state.hasProperty(MMEBlockStateProperties.FERTILE)) {
            cir.setReturnValue(cir.getReturnValueF() + 5);
        }
    }

    /**
     * Overwrites vanilla {@code isRandomlyTicking}: a crop keeps ticking while it can still grow
     * or while its disease has not reached the final withered stage (2).
     *
     * @param blockState the crop state being ticked
     * @return whether the crop should receive random ticks
     */
    @Overwrite
    public boolean isRandomlyTicking(BlockState blockState) {
        return !this.isMaxAge(blockState) || blockState.getValue(DISEASE_LEVEL) != 2;
    }

    /**
     * Overwrites vanilla {@code randomTick}: drives growth (with moon-phase multipliers), disease
     * onset, disease spread to neighboring crops and disease progression. See the class comment
     * for the full probability table.
     *
     * @param blockState  the crop state being ticked
     * @param serverLevel the server level (consulted for light, moon phase and neighbor states)
     * @param blockPos    the crop position
     * @param randomSource the level random source
     */
    @Overwrite
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int diseaseLevel = blockState.getValue(DISEASE_LEVEL);
        SpecialMoonPhase dimensionValue = serverLevel.environmentAttributes().getDimensionValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE);
        if (serverLevel.getRawBrightness(blockPos, 0) >= 9 && diseaseLevel == 0) {
            // Healthy crop in bright light: advance age with the moon-phase growth multiplier.
            int i = this.getAge(blockState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed((CropBlock) (Object)this, serverLevel, blockPos);
                switch (dimensionValue) {
                    case HARVEST_MOON ->  f*=2F;
                    case BLUE_MOON ->   f*=2.5F;
                    case FROST_MOON ->   f*=0.75F;
                    case TURBID_MOON ->   f*=0.5F;
                }
                if (randomSource.nextInt((int)(50.0F / f) + 1) == 0) {
                    serverLevel.setBlock(blockPos, this.getStateForAge(i + 1), 2);
                }
            }
            // Disease onset: 1/5000 normally, 1/500 during a blood moon; blue moon grants immunity.
            int dlProbability = 5000;
            if (dimensionValue == SpecialMoonPhase.BLOOD_MOON) {
                dlProbability -= 4500;
            }
            if (randomSource.nextInt(dlProbability) == 0 && !dimensionValue.equals(SpecialMoonPhase.BLUE_MOON)) {
                serverLevel.setBlock(blockPos, blockState.setValue(DISEASE_LEVEL, 1), 2);
            }
        }else {
            // Dark or infected crop: no growth — spread infection to the 7 surrounding crops
            // (1/5 each) and advance own disease toward the withered stage (1/10).
            BlockPos[] around = new BlockPos[]{blockPos.east(), blockPos.east().north(), blockPos.north(), blockPos.north().west(), blockPos.west(), blockPos.west().south(), blockPos.south().east()};
            for (BlockPos blockPos1 : around) {
                BlockState state = serverLevel.getBlockState(blockPos1);
                if (state.hasProperty(DISEASE_LEVEL) && state.getValue(DISEASE_LEVEL) == 0) {
                    if (randomSource.nextInt(5) == 0) {
                        serverLevel.setBlock(blockPos1, state.setValue(DISEASE_LEVEL, 1), 2);
                    }
                }
            }
            if (randomSource.nextInt(10) == 0 ) {
                serverLevel.setBlock(blockPos, blockState.setValue(DISEASE_LEVEL, 2), 2);
            }
        }
    }

    /**
     * Overwrites vanilla {@code createBlockStateDefinition}: registers the custom disease level
     * next to the vanilla age property.
     *
     * @param builder the state definition builder to add properties to
     */
    @Overwrite
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISEASE_LEVEL).add(AGE);
    }
}
