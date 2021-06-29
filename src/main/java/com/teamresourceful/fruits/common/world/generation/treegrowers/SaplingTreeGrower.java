package com.teamresourceful.fruits.common.world.generation.treegrowers;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public class SaplingTreeGrower extends AbstractTreeGrower {

    private final Supplier<ConfiguredFeature<TreeConfiguration, ?>>  tree;

    public SaplingTreeGrower(Supplier<ConfiguredFeature<TreeConfiguration, ?>> tree) {
        this.tree = tree;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean bl) {
        return tree.get();
    }
}
