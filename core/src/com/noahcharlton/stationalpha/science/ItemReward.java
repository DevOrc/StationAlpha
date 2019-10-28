package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;

public class ItemReward implements GoalReward{

    private final ItemStack reward;

    public ItemReward(ItemStack reward) {
        this.reward = reward;
    }

    @Override
    public void giveReward(World world) {
        Inventory inventory = world.getInventory();

        inventory.changeAmountForItem(reward.getItem(), reward.getAmount());
    }
}
