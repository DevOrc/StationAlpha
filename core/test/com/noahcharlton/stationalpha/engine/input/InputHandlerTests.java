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

    @Test
    void onSelectedRunsTest() {
        TestBuildAction action = new TestBuildAction();

        inputHandler.setBuildAction(action);

        Assertions.assertTrue(action.onSelectedRan);
    }

    @Test
    void onDeselectedRunsTest() {
        TestBuildAction action = new TestBuildAction();

        inputHandler.setBuildAction(action);
        inputHandler.setBuildAction(null);

        Assertions.assertTrue(action.onDeselectedRan);
    }
}
class TestBuildAction implements BuildAction{

    boolean onSelectedRan = false;
    boolean onDeselectedRan = false;

    @Override
    public void onSelected() {
        onSelectedRan = true;
    }

    @Override
    public void onDeselected() {
        onDeselectedRan = true;
    }

    @Override
    public void onClick(Tile tile, int button) {

    }

    @Override
    public String getName() {
        return "";
    }
}
