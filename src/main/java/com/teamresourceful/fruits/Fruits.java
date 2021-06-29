package com.teamresourceful.fruits;

import com.teamresourceful.fruits.common.registry.ModBlocks;
import com.teamresourceful.fruits.common.registry.ModFeatures;
import com.teamresourceful.fruits.common.registry.ModItems;
import com.teamresourceful.fruits.common.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Fruits implements ModInitializer {

	public static final String MOD_ID = "fruits";
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "foods"), () -> new ItemStack(Items.APPLE));
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		//orchard villages
		ModItems.onInitialize();
		ModBlocks.onInitialize();
		ModFeatures.onInitialize();
		ModRecipes.onInitialize();
	}
}
