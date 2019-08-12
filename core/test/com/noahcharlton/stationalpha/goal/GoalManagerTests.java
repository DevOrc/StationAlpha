package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GoalManagerTests {

    private final GoalManager goalManager = new GoalManager(new World());

    @ParameterizedTest
    @EnumSource(GoalTab.class)
    void goalManagerHasTabTest(GoalTab tab) {
        Assertions.assertTrue(goalManager.getTabs().contains(tab));
    }
}
