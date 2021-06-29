package com.teamresourceful.fruits.common.world.configurations;

import com.teamresourceful.fruits.common.registry.ModBlocks;
import com.teamresourceful.fruits.common.world.generation.leavesplacers.PalmLeavePlacer;
import com.teamresourceful.fruits.common.world.generation.trunkplacers.PalmTrunkPlacer;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class Trees {

    public static final TreeConfiguration.TreeConfigurationBuilder CHERRY = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
            new StraightTrunkPlacer(5,0,0),
            new SimpleStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
            new SimpleStateProvider(ModBlocks.CHERRY_SAPLING.defaultBlockState()),
            new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 65),
            new TwoLayersFeatureSize(0, 0, 0)
    );

    public static final TreeConfiguration.TreeConfigurationBuilder PALM = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(ModBlocks.PALM_LOG.defaultBlockState()),
            new PalmTrunkPlacer(),
            new SimpleStateProvider(ModBlocks.PALM_LEAVES.defaultBlockState()),
            new SimpleStateProvider(ModBlocks.PALM_SAPLING.defaultBlockState()),
            new PalmLeavePlacer(),
            new TwoLayersFeatureSize(0, 0, 0)
    );

    public static final TreeConfiguration.TreeConfigurationBuilder APPLE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(Blocks.OAK_LOG.defaultBlockState()),
            new StraightTrunkPlacer(5,0,0),
            new SimpleStateProvider(Blocks.OAK_LEAVES.defaultBlockState()),
            new SimpleStateProvider(ModBlocks.APPLE_SAPLING.defaultBlockState()),
            new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 65),
            new TwoLayersFeatureSize(0, 0, 0)
    );



}
