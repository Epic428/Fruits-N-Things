package com.teamresourceful.fruits.common.registry;

import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blocks.*;
import com.teamresourceful.fruits.common.lib.Constants;
import com.teamresourceful.fruits.common.world.generation.treegrowers.SaplingTreeGrower;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModBlocks {
    private ModBlocks() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    private static final FabricBlockSettings WOOD_PROPERTY = FabricBlockSettings.of(Material.WOOD);
    private static final FabricBlockSettings METAL_PROPERTY = FabricBlockSettings.of(Material.METAL);
    private static final FabricBlockSettings CUTOUT_METAL_PROPERTY = FabricBlockSettings.copyOf(METAL_PROPERTY).nonOpaque();
    private static final FabricBlockSettings CUTOUT_WOOD_PROPERTY = FabricBlockSettings.copyOf(WOOD_PROPERTY).nonOpaque();

    public static final Block COMPOST_BIN = registerFlammableBlock("compost_bin", new CompostBinBlock(WOOD_PROPERTY), 5, 20);
    public static final Block FERTILIZED_DIRT = register("fertilized_dirt", new FertilizedDirtBlock(FabricBlockSettings.of(Material.DIRT)));
    public static final Block RAIN_BARREL = registerFlammableBlock("rain_barrel", new RainBarrelBlock(CUTOUT_WOOD_PROPERTY), 5, 20);
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(WOOD_PROPERTY));
    public static final Block TRELLIS = register("trellis", new TrellisBlock(WOOD_PROPERTY));
    public static final Block SPRINKLER = register("sprinkler", new SprinklerBlock(METAL_PROPERTY));
    public static final Block OIL_PRESS = register("oil_press", new OilPressBlock(CUTOUT_METAL_PROPERTY));
    public static final Block JUICER = register("juicer", new JuicerBlock(METAL_PROPERTY));
    public static final Block BLENDER = register("blender", new BlenderBlock(METAL_PROPERTY));

    public static final Block PALM_LOG = registerFlammableBlock("palm_log", new RotatedPillarBlock(WOOD_PROPERTY), 5,5);
    public static final Block PALM_LEAVES = registerFlammableBlock("palm_leaves", new AdvancedLeavesBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion()), 30,60);
    public static final Block PALM_SAPLING = register("palm_sapling", new SaplingBlockBase(new SaplingTreeGrower(() -> ModFeatures.PALM_TREE)));

    public static final Block CHERRY_LOG = registerFlammableBlock("cherry_log", new RotatedPillarBlock(WOOD_PROPERTY), 5,5);
    public static final Block CHERRY_LEAVES = registerFlammableBlock("cherry_leaves", new AdvancedLeavesBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion()), 30,60);
    public static final Block CHERRY_SAPLING = register("cherry_sapling", new SaplingBlockBase(new SaplingTreeGrower(() -> ModFeatures.CHERRY_TREE)));


    public static final Block APPLE_SAPLING = register("apple_sapling", new SaplingBlockBase(new SaplingTreeGrower(() -> ModFeatures.APPLE_TREE)));


    public static final PickablePlantBlock BLACKBERRY_BUSH = registerBlockOnly("blackberry_bush", new PickablePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.BLACKBERRY));
    public static final PickablePlantBlock BLUEBERRY_BUSH = registerBlockOnly("blueberry_bush", new PickablePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.BLUEBERRY));
    public static final PickableCactusPlantBlock PRICKLY_PEAR_PLANT = registerBlockOnly("prickly_pear", new PickableCactusPlantBlock(BlockBehaviour.Properties.of(Material.PLANT), () -> ModItems.PRICKLY_PEAR));
    public static final PickableTreePlantBlock COCONUT_BLOCK = registerBlockOnly("coconut", new PickableTreePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.COCONUT, () -> ModBlocks.PALM_LEAVES));
    public static final PickableTreePlantBlock APPLE_BLOCK = registerBlockOnly("apple", new PickableTreePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), 1, () -> Items.APPLE, () -> Blocks.OAK_LEAVES));
    public static final PickableTreePlantBlock CHERRY_BLOCK = registerBlockOnly("cherry", new PickableTreePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.CHERRY, () -> ModBlocks.CHERRY_LEAVES));




    public static void onInitialize() {
        //class load
    }

    private static <T extends Block> T register(String name, T block) {
        var resourceLocation = new ResourceLocation(Fruits.MOD_ID, name);
        Registry.register(Registry.ITEM, resourceLocation, createBlockItem(block));
        return Registry.register(Registry.BLOCK, resourceLocation, block);
    }

    private static <T extends Block> T registerFlammableBlock(String name, T block, int burn, int spread) {
        var flammableBlock = register(name, block);
        FlammableBlockRegistry.getDefaultInstance().add(flammableBlock, burn, spread);
        return flammableBlock;
    }

    private static <T extends Block> T registerBlockOnly(String name, T block) {
        return Registry.register(Registry.BLOCK, new ResourceLocation(Fruits.MOD_ID, name), block);
    }

    private static Item createBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties().tab(Fruits.ITEM_GROUP));
    }
}
