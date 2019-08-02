package com.noahcharlton.stationalpha.gui;

import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameGuiTests extends LibGdxTest {

    private final GameGui gameGui = new GameGui();

    @Test
    void nothingSelectedBoxTest() {
        InputHandler.getInstance().setCurrentlySelected(null);

        gameGui.update();

        Assertions.assertFalse(gameGui.getSelectableBox().isVisible());
    }

    @Test
    void somethingSelectedBoxTest() {
        InputHandler.getInstance().setCurrentlySelected(new TestSelectable());

        gameGui.update();

        Assertions.assertTrue(gameGui.getSelectableBox().isVisible());
    }
}
class TestSelectable implements Selectable{

    @Override
    public String[] getDebugInfo() {
        return new String[0];
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
}