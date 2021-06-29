package com.teamresourceful.fruits.common.registry;

import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.recipes.CrushingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {

    public static final RecipeType<CrushingRecipe> CRUSHING_RECIPE_TYPE = registerRecipe("crushing");
    public static final RecipeSerializer<?> CRUSHING_SERIALIZER = registerSerializer("crushing", new CrushingRecipe.Serializer(CrushingRecipe::new));

    public static void onInitialize(){

    }


    private static <T extends RecipeSerializer<?>> T registerSerializer(String id, T serializer){
        return Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(Fruits.MOD_ID, id), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerRecipe(String id) {
        return RecipeType.register(Fruits.MOD_ID+":"+id);
    }
}
