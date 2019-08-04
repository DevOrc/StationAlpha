package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemStackTests {

    @Test
    void negativeAmountFailsTest() {
        Assertions.assertThrows(GdxRuntimeException.class, () -> ItemStack.of(Item.SPACE_DUST, -5));
    }

    @Test
    void nullItemFailsTest() {
        Assertions.assertThrows(NullPointerException.class, () -> ItemStack.of(null, 5));
    }

    @Test
    void ofBasicItemTest() {
        ItemStack stack = ItemStack.of(Item.STEEL);

        Assertions.assertEquals(Item.STEEL, stack.getItem());
    }

    @Test
    void ofAmountBasicTest() {
        ItemStack stack = ItemStack.of(Item.SPACE_ROCK, 4);

        Assertions.assertEquals(4, stack.getAmount());
    }

    @Test
    void itemStackEqualsTest() {
        ItemStack stackOne = ItemStack.of(Item.POTATO, 33);
        ItemStack stackTwo = ItemStack.of(Item.POTATO, 33);

        Assertions.assertEquals(stackOne, stackTwo);
    }

    @Test
    void itemStackNotSameItemNotEqualsTest() {
        ItemStack stackOne = ItemStack.of(Item.STEEL, 33);
        ItemStack stackTwo = ItemStack.of(Item.POTATO, 33);

        Assertions.assertNotEquals(stackOne, stackTwo);
    }

    @Test
    void itemStackNotSameAmountNotEqualsTest() {
        ItemStack stackOne = ItemStack.of(Item.POTATO, 33);
        ItemStack stackTwo = ItemStack.of(Item.POTATO, 54);

        Assertions.assertNotEquals(stackOne, stackTwo);
    }

    @Test
    void resourcesAvailableBasicTest() {
        Inventory inventory = new Inventory();
        inventory.setAmountForItem(Item.POTATO, 34);

        ItemStack stack = ItemStack.of(Item.POTATO, 34);

        Assertions.assertTrue(stack.resourcesAvailable(inventory));
    }

    @Test
    void resourcesNotAvailableBasicTest() {
        Inventory inventory = new Inventory();
        inventory.setAmountForItem(Item.POTATO, 23);

        ItemStack stack = ItemStack.of(Item.POTATO, 34);

        Assertions.assertFalse(stack.resourcesAvailable(inventory));
    }
}
