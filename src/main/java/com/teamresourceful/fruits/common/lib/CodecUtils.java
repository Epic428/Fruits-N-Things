package com.teamresourceful.fruits.common.lib;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public class CodecUtils {

    public static final Codec<ItemStack> ITEM_STACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.fieldOf("id").forGetter(ItemStack::getItem),
            Codec.INT.fieldOf("count").orElse(1).forGetter(ItemStack::getCount),
            CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(o -> Optional.ofNullable(o.getTag()))
    ).apply(instance, CodecUtils::createItemStack));

    //Codec for converting an ItemStack to an Ingredient
    public static final Codec<Ingredient> INGREDIENT_CODEC = ITEM_STACK_CODEC.comapFlatMap(CodecUtils::convertToIngredient, CodecUtils::ingredientToString);


    //helper method to create an item stack with an optional tag
    private static ItemStack createItemStack(ItemLike item, int count, Optional<CompoundTag> tagOptional) {
        ItemStack stack = new ItemStack(item, count);
        tagOptional.ifPresent(stack::setTag);
        return stack;
    }

    private static DataResult<Ingredient> convertToIngredient(ItemStack stack) {
        return DataResult.success(Ingredient.of(stack));
    }

    private static ItemStack ingredientToString(Ingredient ingredient) {
        return ingredient.getItems()[0];
    }
}
