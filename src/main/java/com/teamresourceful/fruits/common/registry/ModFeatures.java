package com.teamresourceful.fruits.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blocks.PickableCactusPlantBlock;
import com.teamresourceful.fruits.common.blocks.PickablePlantBlock;
import com.teamresourceful.fruits.common.world.configurations.Trees;
import com.teamresourceful.fruits.common.world.generation.leavesplacers.PalmLeavePlacer;
import com.teamresourceful.fruits.common.world.generation.treedecorators.PalmTreeDecorator;
import com.teamresourceful.fruits.common.world.generation.treedecorators.RandomFoliageFruitDecorator;
import com.teamresourceful.fruits.common.world.generation.trunkplacers.PalmTrunkPlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.lang.reflect.Constructor;
import java.util.List;

public class ModFeatures {

    public static ConfiguredFeature<TreeConfiguration, ?> PALM_TREE = registerTree("palm",
            Trees.PALM,
            ImmutableList.of(new PalmTreeDecorator(1f, ModBlocks.COCONUT_BLOCK)),
            Biome.BiomeCategory.BEACH
    );

    public static ConfiguredFeature<TreeConfiguration, ?> APPLE_TREE = registerTree("apple",
            Trees.APPLE,
            ImmutableList.of(new RandomFoliageFruitDecorator(0.4f, ModBlocks.APPLE_BLOCK)),
            Biome.BiomeCategory.FOREST
    );

    public static ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE = registerTree("cherry",
            Trees.CHERRY,
            ImmutableList.of(new RandomFoliageFruitDecorator(0.4f, ModBlocks.CHERRY_BLOCK)),
            Biome.BiomeCategory.FOREST
    );

    public static TrunkPlacerType<?> PALM_TRUNK_PLACER = createTrunkPlacerType(PalmTrunkPlacer.CODEC);
    public static FoliagePlacerType<?> PALM_LEAVE_PLACER = createFoliagePlacerType(PalmLeavePlacer.CODEC);
    public static TreeDecoratorType<?> PALM_TREE_DECORATOR = createTreeDecoratorType(PalmTreeDecorator.CODEC);
    public static TreeDecoratorType<?> RANDOM_TREE_DECORATOR = createTreeDecoratorType(RandomFoliageFruitDecorator.CODEC);


    public static void onInitialize() {
        registerBushPatch(ModBlocks.BLACKBERRY_BUSH, "blackberry");
        registerBushPatch(ModBlocks.BLUEBERRY_BUSH, "blueberry");
        registerCactusPatch(ModBlocks.PRICKLY_PEAR_PLANT, "prickly_pear");

        Registry.register(Registry.TRUNK_PLACER_TYPES, new ResourceLocation(Fruits.MOD_ID, "palm_trunk_placer"), PALM_TRUNK_PLACER);
        Registry.register(Registry.FOLIAGE_PLACER_TYPES, new ResourceLocation(Fruits.MOD_ID, "palm_leave_placer"), PALM_LEAVE_PLACER);
        Registry.register(Registry.TREE_DECORATOR_TYPES, new ResourceLocation(Fruits.MOD_ID, "palm_tree_decorator"), PALM_TREE_DECORATOR);
        Registry.register(Registry.TREE_DECORATOR_TYPES, new ResourceLocation(Fruits.MOD_ID, "random_tree_decorator"), RANDOM_TREE_DECORATOR);

        ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Fruits.MOD_ID, "fruits_forest"));
        Registry.register(BuiltinRegistries.BIOME, biomeKey.location(), createFruitsForest(0.1f, 0.1f));
        OverworldBiomes.addContinentalBiome(biomeKey, OverworldClimate.TEMPERATE, 2);
    }

    private static void registerBushPatch(PickablePlantBlock plant, String id) {
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.getMaxAgeState()), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(25)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id + "_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    private static void registerCactusPatch(PickableCactusPlantBlock plant, String id) {
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.getMaxAgeState()), SimpleBlockPlacer.INSTANCE)).tries(12).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.RED_SAND)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(12)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id + "_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.DESERT), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    private static ConfiguredFeature<TreeConfiguration, ?> registerTree(String name, TreeConfiguration.TreeConfigurationBuilder builder, List<TreeDecorator> decorators, Biome.BiomeCategory... biomes){
        ConfiguredFeature<TreeConfiguration, ?> tree = Feature.TREE.configured(builder.build().withDecorators(decorators));

        ConfiguredFeature<?, ?> configuredTree = tree.decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING)).squared());
        ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, name+"_tree"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.location(), configuredTree);
        BiomeModifications.addFeature(BiomeSelectors.categories(biomes), GenerationStep.Decoration.VEGETAL_DECORATION, key);
        return tree;
    }

    public static Biome createFruitsForest(float depth, float scale) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.monsters(spawnBuilder, 95, 5, 100);

        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
        BiomeDefaultFeatures.addDefaultOverworldLandStructures(builder);
        BiomeDefaultFeatures.addDefaultLakes(builder);
        BiomeDefaultFeatures.addDefaultCarvers(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);
        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addWaterTrees(builder);
        builder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.FOREST)
                .depth(depth)
                .scale(scale)
                .temperature(0.85f)
                .downfall(0.6f)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(0x3dd0f5)
                                .waterFogColor(0x34b8d9)
                                .grassColorOverride(0x1be35e)
                                .fogColor(12638463)
                                .skyColor(10192639)
                                .build()
                )
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(builder.build())
                .build();
    }

    public static TrunkPlacerType<?> createTrunkPlacerType(Codec codec) {
        try {
            Constructor constructor = TrunkPlacerType.class.getDeclaredConstructor(Codec.class);
            constructor.setAccessible(true);
            return (TrunkPlacerType<? extends TrunkPlacer>) constructor.newInstance(codec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static FoliagePlacerType<?> createFoliagePlacerType(Codec codec) {
        try {
            Constructor constructor = FoliagePlacerType.class.getDeclaredConstructor(Codec.class);
            constructor.setAccessible(true);
            return (FoliagePlacerType<? extends FoliagePlacer>) constructor.newInstance(codec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TreeDecoratorType<?> createTreeDecoratorType(Codec codec) {
        try {
            Constructor constructor = TreeDecoratorType.class.getDeclaredConstructor(Codec.class);
            constructor.setAccessible(true);
            return (TreeDecoratorType<? extends TreeDecorator>) constructor.newInstance(codec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
