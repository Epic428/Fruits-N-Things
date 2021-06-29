package com.teamresourceful.fruits.common.world.generation.trunkplacers;

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

public class PalmTrunkPlacer extends TrunkPlacer {

    public static final Codec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).apply(instance, (a,b,c) -> new PalmTrunkPlacer()));

    public PalmTrunkPlacer() {
        super(14, 0, 0);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModFeatures.PALM_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> biConsumer, Random random, int trunkHeight, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        List<FoliagePlacer.FoliageAttachment> foliageNodes = new ArrayList<>();
        BlockPos.MutableBlockPos pos = blockPos.mutable();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        placeLog(level, biConsumer, random, pos, treeConfiguration);
        pos.move(direction);
        for (int i = 0; i < 3; i++) {
            placeLog(level, biConsumer, random, pos, treeConfiguration);
            pos.move(Direction.UP);
        }
        placeLog(level, biConsumer, random, pos, treeConfiguration);
        pos.move(direction);
        for (int i = 0; i < 4; i++) {
            placeLog(level, biConsumer, random, pos, treeConfiguration);
            pos.move(Direction.UP);
        }
        placeLog(level, biConsumer, random, pos, treeConfiguration);
        pos.move(direction.getOpposite());
        for (int i = 0; i < 3; i++) {
            placeLog(level, biConsumer, random, pos, treeConfiguration);
            pos.move(Direction.UP);
        }
        placeLog(level, biConsumer, random, pos, treeConfiguration);
        pos.move(direction.getOpposite());
        for (int i = 0; i < 4; i++) {
            placeLog(level, biConsumer, random, pos, treeConfiguration);
            pos.move(Direction.UP);
        }
        foliageNodes.add(new FoliagePlacer.FoliageAttachment(pos, 0, false));

        return foliageNodes;
    }
}
