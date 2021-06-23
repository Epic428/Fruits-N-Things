package com.teamresourceful.fruits.common.registry;

import com.teamresourceful.fruits.Fruits;
import com.teamresourceful.fruits.common.items.BaseFoodItem;
import com.teamresourceful.fruits.common.items.FoodReturnableItem;
import com.teamresourceful.fruits.common.lib.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    private ModItems() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }
    // compost bin - meal worms to speed up composting process


    //public static final Item APPLE = createFoodItem();
    public static final Item ACAI_BERRY = register("acai_berry", createFoodItem(ModFoods.ACAI_BERRY));
    public static final Item APRICOT = register("apricot", createFoodItem(ModFoods.APRICOT));
    public static final Item AVOCADO = register("avocado", createFoodItem(ModFoods.AVOCADO));
    public static final Item BANANA_PEEL = register("banana_peel", createReturnableItem());
    public static final Item BANANA = register("banana", createFoodItem(ModFoods.BANANA, BANANA_PEEL));
    public static final Item BLACKBERRY = register("blackberry", createFoodItem(ModFoods.BLACKBERRY));
    public static final Item CANTALOUPE = register("cantaloupe", createFoodItem(ModFoods.CANTALOUPE));
    public static final Item CHERRY = register("cherry", createFoodItem(ModFoods.CHERRY));
    public static final Item COCONUT = register("coconut", createFoodItem(ModFoods.COCONUT));
    public static final Item CRANBERRY = register("cranberry", createFoodItem(ModFoods.CRANBERRY));
    public static final Item DRAGON_FRUIT = register("dragon_fruit", createFoodItem(ModFoods.DRAGON_FRUIT));
    public static final Item FIG = register("fig", createFoodItem(ModFoods.FIG));
    public static final Item GRAPE = register("grape", createFoodItem(ModFoods.GRAPE));
    public static final Item GRAPEFRUIT = register("grapefruit", createFoodItem(ModFoods.GRAPEFRUIT));
    public static final Item GUAVA = register("guava", createFoodItem(ModFoods.GUAVA));
    public static final Item HONEYDEW = register("honeydew", createFoodItem(ModFoods.HONEYDEW));
    public static final Item HUCKLEBERRY = register("huckleberry", createFoodItem(ModFoods.HUCKLEBERRY));
    public static final Item JACK_FRUIT = register("jackfruit", createFoodItem(ModFoods.JACK_FRUIT));
    public static final Item KIWI = register("kiwi", createFoodItem(ModFoods.KIWI));
    public static final Item LEMON = register("lemon", createFoodItem(ModFoods.LEMON));
    public static final Item LIME = register("lime", createFoodItem(ModFoods.LIME));
    public static final Item MANDARIN = register("mandarin", createFoodItem(ModFoods.MANDARIN));
    public static final Item MANGO = register("mango", createFoodItem(ModFoods.MANGO));
    public static final Item MULBERRY = register("mulberry", createFoodItem(ModFoods.MULBERRY));
    public static final Item NONI = register("noni", createFoodItem(ModFoods.NONI));
    public static final Item OLIVE = register("olive", createFoodItem(ModFoods.OLIVE));
    public static final Item ORANGE = register("orange", createFoodItem(ModFoods.ORANGE));
    public static final Item PAPAYA = register("papaya", createFoodItem(ModFoods.PAPAYA));
    public static final Item PASSION_FRUIT = register("passion_fruit", createFoodItem(ModFoods.PASSION_FRUIT));
    public static final Item PEACH = register("peach", createFoodItem(ModFoods.PEACH));
    public static final Item PEAR = register("pear", createFoodItem(ModFoods.PEAR));
    public static final Item PINEAPPLE = register("pineapple", createFoodItem(ModFoods.PINEAPPLE));
    public static final Item PINE_BERRY = register("pine_berry", createFoodItem(ModFoods.PINE_BERRY));
    public static final Item PLUM = register("plum", createFoodItem(ModFoods.PLUM));
    public static final Item POMEGRANATE = register("pomegranate", createFoodItem(ModFoods.POMEGRANATE));
    public static final Item RAISINS = register("raisins", createFoodItem(ModFoods.RAISINS));
    public static final Item STARFRUIT = register("starfruit", createFoodItem(ModFoods.STARFRUIT));
    public static final Item STAR_APPLE = register("star_apple", createFoodItem(ModFoods.STAR_APPLE));
    public static final Item STRAWBERRY = register("strawberry", createFoodItem(ModFoods.STRAWBERRY));
    public static final Item ZUCCHINI = register("zucchini", createFoodItem(ModFoods.ZUCCHINI));

    public static void onInitialize() {
        //class load
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(Fruits.MOD_ID, name), item);
    }

    private static Item createReturnableItem() {
        return new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).tab(Fruits.ITEM_GROUP));
    }

    private static Item createFoodItem(FoodProperties foodComponent, Item returnable) {
        return new FoodReturnableItem(new Item.Properties().food(foodComponent), returnable);
    }

    private static Item createFoodItem(FoodProperties foodProperties) {
        return new BaseFoodItem(new Item.Properties().food(foodProperties), foodProperties.isFastFood() ? 16 : 32);
    }
}
