package com.noahcharlton.stationalpha.goal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultGoalsTests {

    private final DefaultGoals goals = new DefaultGoals();

    @Test
    void idStartsAtNegativeOneTest() {
        Assertions.assertEquals(-1, goals.getId());
    }

    @Test
    void firstGoalHasIDOfZero() {
        goals.getNext();

        Assertions.assertEquals(0, goals.getId());
    }

    @Test
    void returnsEndGameGoalAfter100GoalsTest() {
        for(int i = 0; i < 100; i++){
            goals.getNext();
        }

        Assertions.assertTrue(goals.getNext() instanceof EndGameGoal);
    }
}
