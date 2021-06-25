package com.teamresourceful.fruits.common.items;

import com.teamresourceful.fruits.Fruits;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaseFoodItem extends Item {

    private final int timeToEat;

    public BaseFoodItem(Properties properties, int timeToEat) {
        super(properties);
        this.timeToEat = timeToEat;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return timeToEat;
    }

    @Override
    protected boolean allowdedIn(CreativeModeTab creativeModeTab) {
        return creativeModeTab.equals(Fruits.ITEM_GROUP) || creativeModeTab.equals(CreativeModeTab.TAB_FOOD) || super.allowdedIn(creativeModeTab);
    }
}
