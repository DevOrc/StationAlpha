package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.world.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputHandlerTests {

    private final InputHandler inputHandler = new InputHandler();

    @Test
    void setBuildActionTest() {
        TestBuildAction buildAction = new TestBuildAction();

        inputHandler.setBuildAction(buildAction);

        Assertions.assertSame(inputHandler.getBuildManager().getAction().get(), buildAction);
    }
}
class TestBuildAction implements BuildAction{

    @Override
    public void onClick(Tile tile, int button) {

    }

    @Override
    public String getName() {
        return "";
    }
}
