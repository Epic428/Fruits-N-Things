package com.teamresourceful.fruits.common.registry;

import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.blocks.*;
import com.teamresourceful.fruits.common.lib.Constants;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    public static final Block COMPOST_BIN = register("compost_bin", new CompostBinBlock(WOOD_PROPERTY));
    public static final Block FERTILIZED_DIRT = register("fertilized_dirt", new FertilizedDirtBlock(FabricBlockSettings.of(Material.DIRT)));
    public static final Block RAIN_BARREL = register("rain_barrel", new RainBarrelBlock(CUTOUT_WOOD_PROPERTY));
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(WOOD_PROPERTY));
    public static final Block TRELLIS = register("trellis", new TrellisBlock(WOOD_PROPERTY));
    public static final Block SPRINKLER = register("sprinkler", new SprinklerBlock(METAL_PROPERTY));
    public static final Block OIL_PRESS = register("oil_press", new OilPressBlock(CUTOUT_METAL_PROPERTY));
    public static final Block JUICER = register("juicer", new JuicerBlock(METAL_PROPERTY));
    public static final Block BLENDER = register("blender", new BlenderBlock(METAL_PROPERTY));

    public static final BerryBushBlock BLACKBERRY_BUSH = registerBlockOnly("blackberry_bush", new BerryBushBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.BLACKBERRY));
    public static final BerryBushBlock BLUEBERRY_BUSH = registerBlockOnly("blueberry_bush", new BerryBushBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission(), () -> ModItems.BLUEBERRY));


    public static void onInitialize() {
        //class load
    }

    private static <T extends Block> T register(String name, T block) {
        var resourceLocation = new ResourceLocation(Fruits.MOD_ID, name);
        Registry.register(Registry.ITEM, resourceLocation, createBlockItem(block));
        return Registry.register(Registry.BLOCK, resourceLocation, block);
    }

    private static <T extends Block> T registerBlockOnly(String name, T block) {
        return Registry.register(Registry.BLOCK, new ResourceLocation(Fruits.MOD_ID, name), block);
    }

    private static Item createBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties().tab(Fruits.ITEM_GROUP));
    }
}
