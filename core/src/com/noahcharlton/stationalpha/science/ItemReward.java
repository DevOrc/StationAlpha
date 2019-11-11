package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;

import java.util.function.Consumer;

public class ItemReward implements Consumer<World> {

    private final ItemStack reward;

    public ItemReward(ItemStack reward) {
        this.reward = reward;
    }

    @Override
    public void accept(World world) {
        Inventory inventory = world.getInventory();

        inventory.changeAmountForItem(reward.getItem(), reward.getAmount());
    }
}
