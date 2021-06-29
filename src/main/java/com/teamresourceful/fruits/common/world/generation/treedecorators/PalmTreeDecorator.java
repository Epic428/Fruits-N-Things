package com.teamresourceful.fruits.common.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.fruits.common.blocks.PickableTreePlantBlock;
import com.teamresourceful.fruits.common.registry.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class PalmTreeDecorator extends TreeDecorator {

    public static final Codec<PalmTreeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
            Registry.BLOCK.fieldOf("plant").forGetter(decorator -> decorator.plant)
        ).apply(instance, (probability, plant)-> {
            if (plant instanceof PickableTreePlantBlock plantBlock) {
                return new PalmTreeDecorator(probability, plantBlock);
            }
            throw new IllegalArgumentException("plant must be an instanceof PickableTreePlantBlock to be used in PalmTreeDecorator");
        })
    );

    private final float probability;
    private final PickableTreePlantBlock plant;

    public PalmTreeDecorator(float probability, PickableTreePlantBlock plant) {
        this.probability = probability;
        this.plant = plant;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModFeatures.PALM_TREE_DECORATOR;
    }

    @Override
    public void place(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> list, List<BlockPos> list2) {
        BlockPos pos = list.get(list.size() - 1).below(1);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextFloat() > this.probability) continue;
            biConsumer.accept(pos.mutable().move(direction), plant.getMaxAgeState());
        }
    }
}
