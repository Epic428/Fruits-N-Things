package com.teamresourceful.fruits.common.world.generation.leavesplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.fruits.common.registry.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class PalmLeavePlacer extends FoliagePlacer {

    public static final Codec<PalmLeavePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            foliagePlacerParts(instance).apply(instance, (a,b) -> new PalmLeavePlacer()));

    public PalmLeavePlacer() {
        super(ConstantInt.of(0), ConstantInt.of(0));
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFeatures.PALM_LEAVE_PLACER;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> biConsumer, Random random, TreeConfiguration config, int i, FoliageAttachment foliageAttachment, int j, int k, int l) {
        BlockPos.MutableBlockPos pos = foliageAttachment.pos().mutable();
        FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);

        for (Direction value : Direction.Plane.HORIZONTAL) {
            pos.move(value);
            FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
            pos.move(Direction.DOWN);
            pos.move(value);
            FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
            pos.move(value);
            FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
            pos.move(Direction.DOWN);
            pos.move(value);
            FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
            pos.set(foliageAttachment.pos());
        }

        this.placeLeavesRow(level,biConsumer, random, config, pos.move(Direction.DOWN), 1, 0, false);
        pos.set(foliageAttachment.pos());

        for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
                if (n == 0 || m == 0) continue;
                pos.move(m*2, -1, n*2);
                FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
                pos.move(m, -1, n);
                FoliagePlacer.tryPlaceLeaf(level,biConsumer, random, config, pos);
                pos.set(foliageAttachment.pos());
            }
        }

    }

    @Override
    public int foliageHeight(Random random, int i, TreeConfiguration treeConfiguration) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int i, int j, int k, int l, boolean bl) {
        return false;
    }
}
