package com.teamresourceful.fruits.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.fruits.common.registry.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class StoneTrunkPlacer extends TrunkPlacer {

    public static final Codec<StoneTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> trunkPlacerParts(instance).apply(instance, StoneTrunkPlacer::new));

    public StoneTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModFeatures.STONE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, int trunkHeight, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> foliageNodes = new ArrayList<>();
        for (int i = 0; i < trunkHeight; i++) {
            placeLog(levelSimulatedReader, biConsumer, random, blockPos.mutable().above(i), treeConfiguration);
            for (Direction direction : Direction.values()) {
                if (direction == Direction.DOWN || direction == Direction.UP) continue;
                placeLog(levelSimulatedReader, biConsumer, random, blockPos.mutable().relative(direction).above(i), treeConfiguration);
                if (i >= 4) {
                    foliageNodes.add(new FoliagePlacer.FoliageAttachment(blockPos.mutable().relative(direction).above(i), 0, false));
                }
            }
        }

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || direction == Direction.UP) continue;
            BlockPos edgePos = blockPos.relative(direction, 2);
            placeLog(levelSimulatedReader, biConsumer, random, edgePos, treeConfiguration);

            BlockPos cornerPos = blockPos.relative(direction).relative(direction.getClockWise());
            placeLog(levelSimulatedReader, biConsumer, random, cornerPos, treeConfiguration);
        }

        return foliageNodes;
    }
}
