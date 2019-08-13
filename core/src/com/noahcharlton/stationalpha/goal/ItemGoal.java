package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

public class ItemGoal extends Goal {

    private final Item item;
    private final int amount;

    public ItemGoal(Item item, int amount) {
        super(formatName(item, amount), "");

        this.item = item;
        this.amount = amount;
    }

    @Override
    public void update(World world) {
        int currentAmount = world.getInventory().getAmountForItem(item);

        if(currentAmount >= amount)
            setCompleted(true);

        setDesc(currentAmount + " / " + amount);
    }

    private static String formatName(Item item, int amount) {
        return String.format("Collect %d %s", amount, item.getDisplayName());
    }
}
