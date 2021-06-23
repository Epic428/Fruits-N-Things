package com.teamresourceful.fruits.common.items;

import com.teamresourceful.fruits.Fruits;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FoodReturnableItem extends Item {

    private final Item returnable;

    public FoodReturnableItem(Properties properties, Item returnable) {
        super(properties.tab(CreativeModeTab.TAB_FOOD).tab(Fruits.ITEM_GROUP));
        this.returnable = returnable;
    }

    public static FoodReturnableItem bowlFood(Properties properties){
        return new FoodReturnableItem(properties, Items.BOWL);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.finishUsingItem(itemStack, level, livingEntity);
        return livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild ? itemStack2 : new ItemStack(returnable);
    }
}
