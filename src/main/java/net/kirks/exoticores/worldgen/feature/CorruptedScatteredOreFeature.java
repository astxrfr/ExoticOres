package net.kirks.exoticores.worldgen.feature;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.ScatteredOreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.HashSet;
import java.util.Set;

public class CorruptedScatteredOreFeature extends ScatteredOreFeature {
    public CorruptedScatteredOreFeature() {
        super(OreConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        ExoticOres.LOGGER.debug("CorruptedScatteredOreFeature.place called");
        Set<BlockPos> placed = new HashSet<>();

        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        OreConfiguration config = context.config();
        BlockPos origin = context.origin();
        int numberOfTries = random.nextInt(config.size + 1);
        BlockPos.MutableBlockPos targetPos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < numberOfTries; i++) {
            this.offsetTargetPos(targetPos, random, origin, Math.min(i, 7));
            BlockState blockState = level.getBlockState(targetPos);

            for (OreConfiguration.TargetBlockState targetState : config.targetStates) {
                if (OreFeature.canPlaceOre(blockState, level::getBlockState, random, config, targetState, targetPos)) {
                    level.setBlock(targetPos, targetState.state, Block.UPDATE_CLIENTS);
                    placed.add(targetPos.immutable());
                    break;
                }
            }
        }

        Set<BlockPos> corruptedPositions = new HashSet<>();

        ExoticOres.LOGGER.debug("Placed ores: {}", placed.size());
        generateCorruption(random, level, placed, corruptedPositions);
        ExoticOres.LOGGER.debug("Corruption positions: {}", corruptedPositions.size());
        generateShrooms(random, level, corruptedPositions);

        return !placed.isEmpty();
    }

    private void generateCorruption(RandomSource rdm, WorldGenLevel level, Set<BlockPos> placedOres, Set<BlockPos> corruptedPositions) {
        var corruption = ModBlocks.KYANITE_BLOCK.get().defaultBlockState();

        for (BlockPos orePos : placedOres) {
            int veinCount = 100 + rdm.nextInt(11);

            for (int vein = 0; vein < veinCount; vein++) {
                double dy = rdm.nextDouble() * 2.0 - 1.0;
                double angle = rdm.nextDouble() * Math.PI * 2.0;
                double horizontal = Math.sqrt(1.0 - dy * dy);

                double dx = horizontal * Math.cos(angle);
                double dz = horizontal * Math.sin(angle);

                double length = 6.0 + rdm.nextDouble();

                double x = orePos.getX() + 0.5;
                double y = orePos.getY() + 0.5;
                double z = orePos.getZ() + 0.5;

                BlockPos previous = orePos;

                for (double distance = 0; distance < length; distance += 0.25) {
                    x += dx * 0.25;
                    y += dy * 0.25;
                    z += dz * 0.25;

                    BlockPos pos = BlockPos.containing(x, y, z);

                    if (pos.equals(previous)) continue;
                    previous = pos;

                    if (corruptedPositions.contains(pos) || placedOres.contains(pos)) continue;

                    var existing = level.getBlockState(pos);

                    if (existing.is(Blocks.END_STONE)) {
                        if(level.setBlock(pos, corruption, Block.UPDATE_CLIENTS))
                            corruptedPositions.add(pos);
                    } else if (existing.is(corruption.getBlock())) {
                        corruptedPositions.add(pos);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void generateShrooms(RandomSource random, WorldGenLevel level, Set<BlockPos> corruptedPositions) {
        var mushroom = ModBlocks.RADIOACTIVE_MUSHROOM.get().defaultBlockState();

        for (BlockPos ground : corruptedPositions) {
            BlockPos above = ground.above();

            if (!level.isEmptyBlock(above)) continue;
            if (random.nextFloat() >= 0.10f) continue;
            if (!mushroom.canSurvive(level, above)) continue;

            level.setBlock(above, mushroom, Block.UPDATE_CLIENTS);
        }
    }

    private void offsetTargetPos(BlockPos.MutableBlockPos targetPos, RandomSource random, BlockPos origin, int maxDistFromOriginForThisTry) {
        int xd = this.getRandomPlacementInOneAxisRelativeToOrigin(random, maxDistFromOriginForThisTry);
        int yd = this.getRandomPlacementInOneAxisRelativeToOrigin(random, maxDistFromOriginForThisTry);
        int zd = this.getRandomPlacementInOneAxisRelativeToOrigin(random, maxDistFromOriginForThisTry);
        targetPos.setWithOffset(origin, xd, yd, zd);
    }

    private int getRandomPlacementInOneAxisRelativeToOrigin(RandomSource random, int maxDistanceFromOrigin) {
        return Math.round((random.nextFloat() - random.nextFloat()) * maxDistanceFromOrigin);
    }
}
