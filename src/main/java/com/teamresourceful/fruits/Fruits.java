package com.teamresourceful.fruits;

import com.teamresourceful.fruits.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Fruits implements ModInitializer {

	public static final String MOD_ID = "fruits";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "foods"), () -> new ItemStack(Items.APPLE));

	@Override
	public void onInitialize() {
		//orchard villages




		ModItems.onInitialize();
		System.out.println("Hello Fabric world!");
	}
}
