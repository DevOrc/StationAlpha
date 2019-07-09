package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemGoalTests {

    static class TestItemGoal extends ItemGoal {
        public TestItemGoal() {
            super(Item.TEST_ITEM, 5);
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        protected String getTextDesc() {
            return "";
        }
    }

    private final World world = new World();
    private final Inventory inventory = world.getInventory();
    private final TestItemGoal testItemGoal = new TestItemGoal();

    @Test
    void checkCompletedLowTest() {
        inventory.setAmountForItem(Item.TEST_ITEM, 3);

        Assertions.assertFalse(testItemGoal.checkCompleted(world));
    }

    @Test
    void checkCompletedExactlyEqualTest() {
        inventory.setAmountForItem(Item.TEST_ITEM, 5);

        Assertions.assertTrue(testItemGoal.checkCompleted(world));
    }

    @Test
    void checkCompletedExtraTest() {
        inventory.setAmountForItem(Item.TEST_ITEM, 7);

        Assertions.assertTrue(testItemGoal.checkCompleted(world));
    }
}
