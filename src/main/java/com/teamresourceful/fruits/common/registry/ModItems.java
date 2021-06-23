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
import net.minecraft.world.item.ItemNameBlockItem;

@SuppressWarnings("unused")
public class ModItems {
    private ModItems() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static final Item ACAI_BERRY = register("acai_berry", createFoodItem(ModFoods.ACAI_BERRY));
    public static final Item AGAVE = register("agave", createFoodItem(ModFoods.AGAVE));
    public static final Item APRICOT = register("apricot", createFoodItem(ModFoods.APRICOT));
    public static final Item ARROWROOT = register("arrowroot", createFoodItem(ModFoods.ARROWROOT));
    public static final Item ARTICHOKE = register("artichoke", createFoodItem(ModFoods.ARTICHOKE));
    public static final Item ARUGULA = register("arugula", createFoodItem(ModFoods.ARUGULA));
    public static final Item ASPARAGUS = register("asparagus", createFoodItem(ModFoods.ASPARAGUS));
    public static final Item AVOCADO = register("avocado", createFoodItem(ModFoods.AVOCADO));
    public static final Item BANANA_PEEL = register("banana_peel", createReturnableItem());
    public static final Item BANANA = register("banana", createFoodItem(ModFoods.BANANA, BANANA_PEEL));
    public static final Item BEANS = register("beans", createFoodItem(ModFoods.BEANS));
    public static final Item BELL_PEPPER = register("bell_pepper", createFoodItem(ModFoods.BELL_PEPPER));
    public static final Item BLACKBERRY = register("blackberry", new ItemNameBlockItem(ModBlocks.BLACKBERRY_BUSH, new Item.Properties().food(ModFoods.BLACKBERRY)));
    public static final Item BLUEBERRY = register("blueberry", new ItemNameBlockItem(ModBlocks.BLUEBERRY_BUSH, new Item.Properties().food(ModFoods.BLUEBERRY)));
    public static final Item BOK_CHOY = register("bok_choy", createFoodItem(ModFoods.BOK_CHOY));
    public static final Item BROCCOLI = register("broccoli", createFoodItem(ModFoods.BROCCOLI));
    public static final Item BRUSSEL_SPROUT = register("brussel_sprout", createFoodItem(ModFoods.BRUSSEL_SPROUT));
    public static final Item CABBAGE = register("cabbage", createFoodItem(ModFoods.CABBAGE));
    public static final Item CANTALOUPE = register("cantaloupe", createFoodItem(ModFoods.CANTALOUPE));
    public static final Item CAULIFLOWER = register("cauliflower", createFoodItem(ModFoods.CAULIFLOWER));
    public static final Item CELERY = register("celery", createFoodItem(ModFoods.CELERY));
    public static final Item CHERRY = register("cherry", createFoodItem(ModFoods.CHERRY));
    public static final Item CHILI_PEPPER = register("chili_pepper", createFoodItem(ModFoods.CHILLI_PEPPER));
    public static final Item COCONUT = register("coconut", createFoodItem(ModFoods.COCONUT));
    public static final Item CRANBERRY = register("cranberry", createFoodItem(ModFoods.CRANBERRY));
    public static final Item CUCUMBER = register("cucumber", createFoodItem(ModFoods.CUCUMBER));
    public static final Item DATE = register("date", createFoodItem(ModFoods.DATE));
    public static final Item DRAGON_FRUIT = register("dragon_fruit", createFoodItem(ModFoods.DRAGON_FRUIT));
    public static final Item EGGPLANT = register("eggplant", createFoodItem(ModFoods.EGGPLANT));
    public static final Item FENNEL = register("fennel", createFoodItem(ModFoods.FENNEL));
    public static final Item FIG = register("fig", createFoodItem(ModFoods.FIG));
    public static final Item GARLIC = register("garlic", createFoodItem(ModFoods.GARLIC));
    public static final Item GINGER = register("ginger", createFoodItem(ModFoods.GINGER));
    public static final Item GRAPE = register("grape", createFoodItem(ModFoods.GRAPE));
    public static final Item GRAPEFRUIT = register("grapefruit", createFoodItem(ModFoods.GRAPEFRUIT));
    public static final Item GUAVA = register("guava", createFoodItem(ModFoods.GUAVA));
    public static final Item HONEYDEW = register("honeydew", createFoodItem(ModFoods.HONEYDEW));
    public static final Item HUCKLEBERRY = register("huckleberry", createFoodItem(ModFoods.HUCKLEBERRY));
    public static final Item JACKFRUIT = register("jackfruit", createFoodItem(ModFoods.JACKFRUIT));
    public static final Item JOJOBA = register("jojoba", createFoodItem(ModFoods.JOJOBA));
    public static final Item KALE = register("kale", createFoodItem(ModFoods.KALE));
    public static final Item KIWI = register("kiwi", createFoodItem(ModFoods.KIWI));
    public static final Item LEEK = register("leek", createFoodItem(ModFoods.LEEK));
    public static final Item LEMON = register("lemon", createFoodItem(ModFoods.LEMON));
    public static final Item LETTUCE = register("lettuce", createFoodItem(ModFoods.LETTUCE));
    public static final Item LIME = register("lime", createFoodItem(ModFoods.LIME));
    public static final Item MANDARIN = register("mandarin", createFoodItem(ModFoods.MANDARIN));
    public static final Item MANGO = register("mango", createFoodItem(ModFoods.MANGO));
    public static final Item MESQUITE = register("mesquite", createFoodItem(ModFoods.MESQUITE));
    public static final Item MULBERRY = register("mulberry", createFoodItem(ModFoods.MULBERRY));
    public static final Item NONI = register("noni", createFoodItem(ModFoods.NONI));
    public static final Item OKRA = register("okra", createFoodItem(ModFoods.OKRA));
    public static final Item OLIVE = register("olive", createFoodItem(ModFoods.OLIVE));
    public static final Item ONION = register("onion", createFoodItem(ModFoods.ONION));
    public static final Item ORANGE = register("orange", createFoodItem(ModFoods.ORANGE));
    public static final Item PAPAYA = register("papaya", createFoodItem(ModFoods.PAPAYA));
    public static final Item PARSNIP = register("parsnip", createFoodItem(ModFoods.PARSNIP));
    public static final Item PASSION_FRUIT = register("passion_fruit", createFoodItem(ModFoods.PASSION_FRUIT));
    public static final Item PEA = register("pea", createFoodItem(ModFoods.PEA));
    public static final Item PEACH = register("peach", createFoodItem(ModFoods.PEACH));
    public static final Item PEAR = register("pear", createFoodItem(ModFoods.PEAR));
    public static final Item PINEAPPLE = register("pineapple", createFoodItem(ModFoods.PINEAPPLE));
    public static final Item PINE_BERRY = register("pine_berry", createFoodItem(ModFoods.PINE_BERRY));
    public static final Item PINE_NUT = register("pine_nut", createFoodItem(ModFoods.PINE_NUT)); //desert tree
    public static final Item PLUM = register("plum", createFoodItem(ModFoods.PLUM));
    public static final Item POMEGRANATE = register("pomegranate", createFoodItem(ModFoods.POMEGRANATE));
    public static final Item PRICKLY_PEAR = register("prickly_pear", createFoodItem(ModFoods.PRICKLY_PEAR));
    public static final Item RADISH = register("radish", createFoodItem(ModFoods.RADISH));
    public static final Item RAISINS = register("raisins", createFoodItem(ModFoods.RAISINS));
    public static final Item RHUBARB = register("rhubarb", createFoodItem(ModFoods.RHUBARB));
    public static final Item SAGUARO = register("saguaro", createFoodItem(ModFoods.SAGUARO));
    public static final Item SHALLOT = register("shallot", createFoodItem(ModFoods.SHALLOT));
    public static final Item SNOW_PEA = register("snow_pea", createFoodItem(ModFoods.SNOW_PEA));
    public static final Item SPINACH = register("spinach", createFoodItem(ModFoods.SPINACH));
    public static final Item SPRING_ONION = register("spring_onion", createFoodItem(ModFoods.SPRING_ONION));
    public static final Item STARFRUIT = register("starfruit", createFoodItem(ModFoods.STARFRUIT));
    public static final Item STAR_APPLE = register("star_apple", createFoodItem(ModFoods.STAR_APPLE));
    public static final Item STRAWBERRY = register("strawberry", createFoodItem(ModFoods.STRAWBERRY));
    public static final Item SWEETCORN = register("sweetcorn", createFoodItem(ModFoods.SWEETCORN));
    public static final Item TOMATO = register("tomato", createFoodItem(ModFoods.TOMATO));
    public static final Item TURMERIC = register("turmeric", createFoodItem(ModFoods.TURMERIC));
    public static final Item TURNIP = register("turnip", createFoodItem(ModFoods.TURNIP));
    public static final Item WOLFBERRY = register("wolfberry", createFoodItem(ModFoods.WOLFBERRY));
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

    private static Item createFoodItem(FoodProperties foodProperties, Item returnable) {
        return new FoodReturnableItem(new Item.Properties().food(foodProperties).tab(CreativeModeTab.TAB_FOOD), returnable, foodProperties.isFastFood() ? 16 : 32);
    }

    private static Item createFoodItem(FoodProperties foodProperties) {
        return new BaseFoodItem(new Item.Properties().food(foodProperties).tab(CreativeModeTab.TAB_FOOD), foodProperties.isFastFood() ? 16 : 32);
    }
}
