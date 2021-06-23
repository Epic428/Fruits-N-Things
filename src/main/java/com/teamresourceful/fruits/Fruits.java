package com.teamresourceful.fruits;

import com.teamresourceful.fruits.common.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Fruits implements ModInitializer {

	public static final String MOD_ID = "fruits";
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "foods"),
			() -> new ItemStack(Items.APPLE));
	public static final CreativeModeTab ITEM_GROUP_WOODS = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "woods"),
			() -> new ItemStack(Items.OAK_LOG));


	@Override
	public void onInitialize() {
		//orchard villages
		ModItems.onInitialize();
		ModItems.onInitialize();
		//ModFeatures.onInitialize();
		System.out.println("Hello Fabric world!");
	}
}
