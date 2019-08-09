package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.engine.input.mine.MineActions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerActionsTest extends LibGdxTest {

    @Test
    void cannotAddActionTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> PlayerActions.getActions().add(new TestBuildAction()));
   }

    @Test
    void hasAllMineActionsTest() {
        for(BuildAction action: MineActions.getActions()){
            Assertions.assertTrue(PlayerActions.getActions().contains(action));
        }
    }
}
