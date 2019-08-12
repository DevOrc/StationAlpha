package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GoalManagerTests implements GoalSupplier{

    private final World world = new World();
    private final GoalManager goalManager = new GoalManager(world, this);
    private TestGoal goal;

    @Override
    public Goal getNext() {
        int newID = goal == null ? 0 : goal.getId() + 1;
        goal = new TestGoal(newID);

        return goal;
    }

    @Test
    void goalNotCompletedYetBasicTest() {
        goalManager.update();

        Assertions.assertSame(goal, goalManager.getCurrentGoal());
    }

    @Test
    void onGoalCompletedGotoNextGoal() {
        TestGoal nextGoal = new TestGoal(1);

        goal.setGoalAchieved(true);
        goalManager.update();

        Assertions.assertEquals(nextGoal, goalManager.getCurrentGoal());
    }

    @Test
    void onGoalCompletedRunOnCompleted() {
        TestGoal localGoal = goal;

        goal.setGoalAchieved(true);
        goalManager.update();

        Assertions.assertTrue(localGoal.isOnCompletedRan());
    }

    @Test
    void addsMessageOnGoalCompleted() {
        MessageQueue.getInstance().getMessages().clear();
        goal.setGoalAchieved(true);

        goalManager.update();

        Assertions.assertEquals(1, MessageQueue.getInstance().getMessages().size());
    }

    @Nested
    class SecondGoalNullSupplier implements GoalSupplier{

        boolean firstSupplied = false;

        @Override
        public Goal getNext() {
            if(firstSupplied) return null;

            firstSupplied = true;

            TestGoal firstGoal = new TestGoal(0);
            firstGoal.setGoalAchieved(true);

            return firstGoal;
        }

        @Test
        void nullGoalThrowsExceptionTest() {
            GoalManager manager = new GoalManager(new World(), new SecondGoalNullSupplier());

            Assertions.assertThrows(NullPointerException.class, manager::update);
        }
    }
}
