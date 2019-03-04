package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InventoryTests {

    private final Inventory inventory = new Inventory();

    @Test
    void inventoryItemCountMatchesItemCountTest() {
        Assertions.assertEquals(Item.values().length, inventory.getItems().size());
    }

    @Test
    void setItemBasicTest() {
        inventory.setAmountForItem(Item.SPACE_ROCK, 5);

        Assertions.assertEquals(inventory.getAmountForItem(Item.SPACE_ROCK), 5);
    }

    @Test
    void setBelowZeroTest() {
        inventory.setAmountForItem(Item.SPACE_ROCK, -5);

        Assertions.assertEquals(inventory.getAmountForItem(Item.SPACE_ROCK), 0);
    }

    @Test
    void changeAmountForItemBasicTest() {
        inventory.changeAmountForItem(Item.SPACE_ROCK, 16);

        Assertions.assertEquals(inventory.getAmountForItem(Item.SPACE_ROCK), 16);
    }

    @Test
    void changeAmountForItemNegativeTest() {
        inventory.setAmountForItem(Item.SPACE_ROCK, 12);
        inventory.changeAmountForItem(Item.SPACE_ROCK, -5);


        Assertions.assertEquals(inventory.getAmountForItem(Item.SPACE_ROCK), 7);
    }

    @Test
    void changeItemBelowZeroTest() {
        inventory.setAmountForItem(Item.SPACE_ROCK, 12);
        inventory.changeAmountForItem(Item.SPACE_ROCK, -14);


        Assertions.assertEquals(inventory.getAmountForItem(Item.SPACE_ROCK), 0);
    }
}
