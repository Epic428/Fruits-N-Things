package com.teamresourceful.fruits.common.registry;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blocks.PickableCactusPlantBlock;
import com.teamresourceful.fruits.common.blocks.PickablePlantBlock;
import com.teamresourceful.fruits.common.blocks.SaplingBlockBase;
import com.teamresourceful.fruits.common.worldgen.StoneSaplingGenerator;
import com.teamresourceful.fruits.common.worldgen.StoneTrunkPlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.OptionalInt;

public class ModFeatures {

    public static ResourceKey<Biome> BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Fruits.MOD_ID, "fruits_forest"));
    public static Biome FRUITS_FOREST;
    public static Block DIAMOND_TREE_SAPLING = new SaplingBlockBase(new StoneSaplingGenerator());

    public static ConfiguredFeature<TreeConfiguration, ?> TREE = Feature.TREE.configured(
            new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(Blocks.STONE.defaultBlockState()),
                    new StoneTrunkPlacer(7, 0, 0),
                    new SimpleStateProvider(Blocks.DIAMOND_BLOCK.defaultBlockState()),
                    new SimpleStateProvider(DIAMOND_TREE_SAPLING.defaultBlockState()),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
            ).ignoreVines().build());

    public static TrunkPlacerType<?> STONE_TRUNK_PLACER = createTrunkPlacerType(StoneTrunkPlacer.CODEC);

    public static void onInitialize() {
        Registry.register(Registry.BLOCK, new ResourceLocation(Fruits.MOD_ID, "diamond_sapling"), DIAMOND_TREE_SAPLING);
        Registry.register(Registry.ITEM, new ResourceLocation(Fruits.MOD_ID, "diamond_sapling"), new BlockItem(DIAMOND_TREE_SAPLING, new FabricItemSettings().group(Fruits.ITEM_GROUP)));

        registerBushPatch(ModBlocks.BLACKBERRY_BUSH, "blackberry");
        registerBushPatch(ModBlocks.BLUEBERRY_BUSH, "blueberry");
        registerCactusPatch(ModBlocks.PRICKLY_PEAR_PLANT, "prickly_pear");

        Registry.register(Registry.TRUNK_PLACER_TYPES, new ResourceLocation(Fruits.MOD_ID, "stone_trunk_placer"), STONE_TRUNK_PLACER);

        TREE = (ConfiguredFeature<TreeConfiguration, ?>) TREE.decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING)).squared());
        ResourceKey<ConfiguredFeature<?, ?>> treeRich = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, "custom_tree_feature"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, treeRich.location(), TREE);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Decoration.VEGETAL_DECORATION, treeRich);

        FRUITS_FOREST = createFruitsForest(0.1f, 0.1f);
        Registry.register(BuiltinRegistries.BIOME, BIOME_KEY.location(), FRUITS_FOREST);
        OverworldBiomes.addContinentalBiome(BIOME_KEY, OverworldClimate.TEMPERATE, 2);
    }

    private static void registerBushPatch(PickablePlantBlock plant, String id) {
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.defaultBlockState().setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE)), SimpleBlockPlacer.INSTANCE)).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(25)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id + "_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    private static void registerCactusPatch(PickableCactusPlantBlock plant, String id) {
        ConfiguredFeature<?, ?> patch = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(plant.defaultBlockState().setValue(SweetBerryBushBlock.AGE, SweetBerryBushBlock.MAX_AGE)), SimpleBlockPlacer.INSTANCE)).tries(12).whitelist(ImmutableSet.of(Blocks.SAND, Blocks.RED_SAND)).build()).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(12)));
        ResourceKey<ConfiguredFeature<?, ?>> patchName = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fruits.MOD_ID, id + "_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, patchName.location(), patch);
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.BiomeCategory.DESERT), GenerationStep.Decoration.VEGETAL_DECORATION, patchName);
    }

    public static Biome createFruitsForest(float depth, float scale) {
        MobSpawnSettings.Builder spawnBuilder = createDefaultSpawnSettings();
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
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TREE);

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
                                .skyColor(getSkyColor(0.85f))
                                .build()
                )
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(builder.build())
                .build();
    }

    public static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    private static MobSpawnSettings.Builder createDefaultSpawnSettings() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.farmAnimals(builder);
        BiomeDefaultFeatures.monsters(builder, 95, 5, 100);
        return builder;
    }

    public static TrunkPlacerType<?> createTrunkPlacerType(Codec codec) {
        try {
            Constructor constructor = TrunkPlacerType.class.getDeclaredConstructor(Codec.class);
            constructor.setAccessible(true);
            return (TrunkPlacerType<? extends TrunkPlacer>) constructor.newInstance(codec);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
