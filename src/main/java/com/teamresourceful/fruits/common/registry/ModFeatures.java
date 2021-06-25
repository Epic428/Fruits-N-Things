package com.teamresourceful.fruits.common.registry;

import com.google.common.collect.ImmutableSet;
import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blocks.PickableCactusPlantBlock;
import com.teamresourceful.fruits.common.blocks.PickablePlantBlock;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.OptionalInt;

public class ModFeatures {


    public static void onInitialize() {
        registerBushPatch(ModBlocks.BLACKBERRY_BUSH, "blackberry");
        registerBushPatch(ModBlocks.BLUEBERRY_BUSH, "blueberry");
        registerCactusPatch(ModBlocks.PRICKLY_PEAR_PLANT, "prickly_pear");
        tree();
    }

    private static void registerBushPatch(PickablePlantBlock plant, String id){
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.defaultBlockState().setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE)),SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(25)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id+"_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    private static void registerCactusPatch(PickableCactusPlantBlock plant, String id){
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.defaultBlockState().setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE)),SimpleBlockPlacer.INSTANCE)).tries(12).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.RED_SAND)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(12)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id+"_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.DESERT), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    private static void tree(){

        ConfiguredFeature<?, ?> TREE_RICH = Feature.TREE
                .configured(
                        (
                            new TreeConfiguration.TreeConfigurationBuilder(
                                new SimpleStateProvider(Blocks.DIAMOND_BLOCK.defaultBlockState()),
                                new FancyTrunkPlacer(3, 11, 0),
                                new SimpleStateProvider(Blocks.STONE.defaultBlockState()),
                                new SimpleStateProvider(Blocks.OAK_SAPLING.defaultBlockState()),
                                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                            )
                        ).ignoreVines().build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(64)));
        ResourceKey<ConfiguredFeature<?, ?>> treeRich = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation("tutorial", "tree_rich"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, treeRich.location(), TREE_RICH);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Decoration.VEGETAL_DECORATION, treeRich);
    }

}
