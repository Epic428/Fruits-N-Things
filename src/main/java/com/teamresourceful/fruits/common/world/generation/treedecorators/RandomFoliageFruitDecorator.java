package com.teamresourceful.fruits.common.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.fruits.common.blocks.PickableTreePlantBlock;
import com.teamresourceful.fruits.common.registry.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RandomFoliageFruitDecorator extends TreeDecorator {

    public static final Codec<RandomFoliageFruitDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
                    Registry.BLOCK.fieldOf("plant").forGetter(decorator -> decorator.plant)
            ).apply(instance, (probability, plant) -> {
                if (plant instanceof PickableTreePlantBlock plantBlock) {
                    return new RandomFoliageFruitDecorator(probability, plantBlock);
                }
                throw new IllegalArgumentException("plant must be an instanceof PickableTreePlantBlock to be used in RandomFoliageFruitDecorator");
            })
    );

    private final float probability;
    private final PickableTreePlantBlock plant;

    public RandomFoliageFruitDecorator(float probability, PickableTreePlantBlock plant) {
        this.probability = probability;
        this.plant = plant;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModFeatures.RANDOM_TREE_DECORATOR;
    }

    @Override
    public void place(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> list, List<BlockPos> list2) {
        for (BlockPos bottomLeaf : list2.stream().filter(pos -> !list2.isEmpty() && list2.get(0).getY() == pos.getY()).collect(Collectors.toList())) {
            if (random.nextFloat() > this.probability && bottomLeaf != list2.get(0)) continue;
            if (levelSimulatedReader.isStateAtPosition(bottomLeaf, plant::canPlaceUnder)){
                biConsumer.accept(bottomLeaf.below(), plant.getMaxAgeState());
            }
        }
    }
}
