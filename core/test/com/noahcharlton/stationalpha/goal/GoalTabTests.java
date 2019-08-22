package com.noahcharlton.stationalpha.goal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GoalTabTests {

    @ParameterizedTest
    @EnumSource(GoalTab.class)
    void nullNotInGoalDescriptionTest(GoalTab tab) {
        for(Goal goal: tab.getGoals()){
            Assertions.assertFalse(goal.getDesc().contains("null"), goal.getName() + " has " +
                    "a null description!");
        }
    }
}
