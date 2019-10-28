package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemGoalTests {

    @Test
    void itemGoalDescriptionEndTest() {
        World world = new World();
        world.getInventory().setAmountForItem(Item.STEEL, 12);
        Goal goal = new ItemGoal(Item.STEEL, 32, "", "");

        goal.update(world);

        Assertions.assertTrue(goal.getDesc().contains("12 / 32"));
    }

    @Test
    void itemGoalUpdateIsCompletedTest() {
        World world = new World();
        world.getInventory().setAmountForItem(Item.STEEL, 40);
        Goal goal = new ItemGoal(Item.STEEL, 32, "", "");

        goal.update(world);

        Assertions.assertTrue(goal.isCompleted());
    }

    @Test
    void onCompleteShowMessageTest() {
        MessageQueue.getInstance().getMessages().clear();
        World world = new World();
        world.getInventory().setAmountForItem(Item.STEEL, 40);
        Goal goal = new ItemGoal(Item.STEEL, 32, "", "");

        goal.update(world);

        Assertions.assertEquals(1, MessageQueue.getInstance().getMessages().size());
    }

    @Test
    void onGoalCompletedRewardGivenTest() {
        Goal goal = new ItemGoal(Item.STEEL, 32, "", "");

        World world = new World();
        world.getInventory().setAmountForItem(Item.STEEL, 32);
        goal.goalReward = w -> w.getInventory().setAmountForItem(Item.SPACE_DUST, 5);
        goal.update(world);

        Assertions.assertEquals(5, world.getInventory().getAmountForItem(Item.SPACE_DUST));
    }
}
