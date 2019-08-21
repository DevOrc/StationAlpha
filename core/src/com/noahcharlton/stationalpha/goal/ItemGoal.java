package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

public class ItemGoal extends Goal {

    private final Item item;
    private final int amount;

    private final String baseDesc;

    public ItemGoal(Item item, int amount, String name, String desc) {
        super(name, "");

        this.item = item;
        this.amount = amount;
        this.baseDesc = HelpInfo.get(desc);

        updateDescription(0);
    }

    @Override
    public void update(World world) {
        int currentAmount = world.getInventory().getAmountForItem(item);

        if(currentAmount >= amount)
            setCompleted(true);

        updateDescription(currentAmount);
    }

    private void updateDescription(int currentAmount) {
        setDesc(baseDesc + "\n" + currentAmount + " / " + amount + "  " + item.getDisplayName());
    }
}
