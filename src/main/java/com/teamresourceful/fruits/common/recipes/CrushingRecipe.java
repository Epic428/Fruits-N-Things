package com.teamresourceful.fruits.common.recipes;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.lib.CodecUtils;
import com.teamresourceful.fruits.common.registry.ModRecipes;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CrushingRecipe implements Recipe<Container> {

    public static final Codec<CrushingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("presses").forGetter(CrushingRecipe::getPresses),
            CodecUtils.INGREDIENT_CODEC.fieldOf("ingredient").forGetter(CrushingRecipe::getIngredient),
            Registry.FLUID.fieldOf("output").orElse(Fluids.EMPTY).forGetter(CrushingRecipe::getOutput),
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance").orElse(0f).forGetter(CrushingRecipe::getChance),
            CodecUtils.ITEM_STACK_CODEC.fieldOf("weightedResult").orElse(ItemStack.EMPTY).forGetter(CrushingRecipe::getWeightedResult)
    ).apply(instance, CrushingRecipe::new));

    private final ResourceLocation id;
    private final int presses;
    private final Ingredient ingredient;
    private final Fluid output;
    private final float chance;
    private final ItemStack weightedResult;

    public CrushingRecipe(int presses, Ingredient ingredient, Fluid output, float chance, ItemStack weightedResult) {
        this(null, presses, ingredient, output, chance, weightedResult);
    }

    public CrushingRecipe(ResourceLocation id, int presses, Ingredient ingredient, Fluid output, float chance, ItemStack weightedResult) {
        this.id = id;
        this.presses = presses;
        this.ingredient = ingredient;
        this.output = output;
        this.chance = chance;
        this.weightedResult = weightedResult;
    }

    public int getPresses() {
        return presses;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public float getChance() { return chance; }

    public Fluid getOutput() { return output; }

    public ItemStack getWeightedResult() { return weightedResult; }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRUSHING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRUSHING_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<CrushingRecipe> {

        final IRecipeFactory factory;

        public Serializer(IRecipeFactory factory) {
            this.factory = factory;
        }

        @Override
        public CrushingRecipe fromJson(ResourceLocation id, JsonObject json) {
            CrushingRecipe recipe = CrushingRecipe.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, s -> Fruits.LOGGER.error("Could not parse Crushing Recipe!"));
            return this.factory.create(id, recipe.presses, recipe.ingredient, recipe.output, recipe.chance, recipe.weightedResult);
        }

        @Override
        public CrushingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return friendlyByteBuf.readWithCodec(CrushingRecipe.CODEC);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, CrushingRecipe recipe) {
            friendlyByteBuf.writeWithCodec(CrushingRecipe.CODEC, recipe);
        }

        public interface IRecipeFactory {
            CrushingRecipe create(ResourceLocation id, int presses, Ingredient ingredient, Fluid output, float chance, ItemStack weightedResult);
        }
    }

}
