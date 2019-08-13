package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GoalManagerTests {

    private final GoalManager goalManager = new GoalManager(new World());

    @ParameterizedTest
    @EnumSource(GoalTab.class)
    void goalManagerHasTabTest(GoalTab tab) {
        Assertions.assertTrue(goalManager.getTabs().contains(tab));
    }

    @Test
    void updatedBasicTest() {
        TestGoal testGoal = new TestGoal();

        goalManager.updateGoal(testGoal);

        Assertions.assertTrue(testGoal.isUpdated());
    }

    @Test
    void completedDoesNotUpdateTest() {
        TestGoal goal = new TestGoal();
        goal.setCompleted(true);

        goalManager.updateGoal(goal);

        Assertions.assertFalse(goal.isUpdated());
    }

    @Test
    void requirementsNotCompleteDoesNotUpdateTest() {
        TestGoal goal = new TestGoal();
        goal.addRequirement(new TestGoal());

        goalManager.updateGoal(goal);

        Assertions.assertFalse(goal.isUpdated());
    }
}
class TestGoal extends Goal{

    private boolean updated = false;

    @Override
    public void update(World world) {
        updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }
}