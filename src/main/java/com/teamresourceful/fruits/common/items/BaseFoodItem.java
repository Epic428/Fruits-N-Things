package com.teamresourceful.fruits.common.items;

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
}
