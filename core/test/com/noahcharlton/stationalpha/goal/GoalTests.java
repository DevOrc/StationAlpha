package com.noahcharlton.stationalpha.goal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalTests {

    private final Goal goal = new Goal();

    @Test
    void requirementsCompletedBasicTest() {
        Goal requirement = new Goal();
        requirement.setCompleted(true);

        goal.addRequirement(requirement);

        Assertions.assertTrue(goal.allRequirementsCompleted());
    }

    @Test
    void requirementsCompletedNotCompleteTest() {
        Goal requirement = new Goal();
        requirement.setCompleted(true);
        Goal secondRequirement = new Goal();
        secondRequirement.setCompleted(false);

        goal.addRequirement(requirement);
        goal.addRequirement(secondRequirement);

        Assertions.assertFalse(goal.allRequirementsCompleted());
    }
}
