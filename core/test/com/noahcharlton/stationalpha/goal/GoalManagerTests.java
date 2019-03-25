package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalManagerTests {

    private final TestGoal goal = new TestGoal();
    private final GoalManager goalManager = new GoalManager(new World(), goal);

    @Test
    void goalNotCompletedYetBasicTest() {
        goalManager.update();

        Assertions.assertSame(goal, goalManager.getCurrentGoal().get());
    }

    @Test
    void onGoalCompletedGetNextGoalEmptyTest() {
        goal.setGoalAchieved(true);
        goalManager.update();

        Assertions.assertFalse(goalManager.getCurrentGoal().isPresent());
    }

    @Test
    void onGoalCompletedGetNextGoalTest() {
        TestGoal nextGoal = new TestGoal();
        goal.setNextGoal(nextGoal);
        goal.setGoalAchieved(true);

        goalManager.update();

        Assertions.assertSame(nextGoal, goalManager.getCurrentGoal().get());
    }
}
