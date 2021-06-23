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
import net.minecraft.world.level.material.Material;

public class ModBlocks {
    private ModBlocks() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    private static final FabricBlockSettings WOOD_PROPERTY = FabricBlockSettings.of(Material.WOOD);
    private static final FabricBlockSettings METAL_PROPERTY = FabricBlockSettings.of(Material.METAL);

    public static final Block COMPOST_BIN = register("compost_bin", new CompostBinBlock(WOOD_PROPERTY));
    public static final Block RAIN_BARREL = register("rain_barrel", new RainBarrelBlock(WOOD_PROPERTY));
    public static final Block FERMENTATION_BARREL = register("fermentation_barrel", new FermentationBarrelBlock(WOOD_PROPERTY));
    public static final Block TRELLIS = register("trellis", new TrellisBlock(WOOD_PROPERTY));
    public static final Block SPRINKLER = register("sprinkler", new SprinklerBlock(METAL_PROPERTY));
    public static final Block OIL_PRESS = register("oil_press", new OilPressBlock(METAL_PROPERTY));
    public static final Block JUICER = register("juicer", new JuicerBlock(METAL_PROPERTY));
    public static final Block BLENDER = register("blender", new BlenderBlock(WOOD_PROPERTY));

    private static Block register(String name, Block block) {
        var resourceLocation = new ResourceLocation(Fruits.MOD_ID, name);
        Registry.register(Registry.ITEM, resourceLocation, createBlockItem(block));
        return Registry.register(Registry.BLOCK, resourceLocation, block);
    }

    private static Item createBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties().tab(Fruits.ITEM_GROUP));
    }
}
