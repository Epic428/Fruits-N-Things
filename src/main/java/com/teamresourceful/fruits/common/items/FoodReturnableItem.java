package com.teamresourceful.fruits.common.items;

import com.teamresourceful.fruits.Fruits;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FoodReturnableItem extends BaseFoodItem {

    private final Item returnable;

    public FoodReturnableItem(Properties properties, Item returnable, int timeToEat) {
        super(properties.tab(CreativeModeTab.TAB_FOOD).tab(Fruits.ITEM_GROUP),timeToEat);
        this.returnable = returnable;
    }

    public static FoodReturnableItem bowlFood(Properties properties){
        return new FoodReturnableItem(properties, Items.BOWL, 32);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !player.getAbilities().instabuild){
            if (itemStack.getCount() > 1) {
                player.addItem(returnable.getDefaultInstance());
            }else {
                return returnable.getDefaultInstance();
            }
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
