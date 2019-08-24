package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemRewardTests {

    private final World world = new World();
    private final Inventory inventory = world.getInventory();
    private final ItemReward reward = new ItemReward(Item.STEEL.stack(3));

    @Test
    void giveRewardBasicTest() {
        reward.giveReward(world);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.STEEL));
    }
}
