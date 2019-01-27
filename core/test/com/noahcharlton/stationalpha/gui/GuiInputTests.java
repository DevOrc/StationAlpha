package com.noahcharlton.stationalpha.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuiInputTests {

    @Test
    void isPointOnBasicTest() {
        TestGui gui = new TestGui();
        gui.setWidth(50);
        gui.setHeight(50);

        Assertions.assertTrue(gui.isPointOnGui(20, 20));
    }

    @Test
    void isPointOnMissRightTest() {
        TestGui gui = new TestGui();
        gui.setWidth(10);
        gui.setHeight(10);

        Assertions.assertFalse(gui.isPointOnGui(100, 100));
    }

    @Test
    void isPointOnMissLeftTest() {
        TestGui gui = new TestGui();
        gui.setX(10);
        gui.setY(10);
        gui.setWidth(10);
        gui.setHeight(10);

        Assertions.assertFalse(gui.isPointOnGui(0, 0));
    }

    @Test
    void handleClickBasicTest() {
        TestGui gui = new TestGui();

        gui.setWidth(10);
        gui.setHeight(10);
        gui.handleClick(5, 5);

        Assertions.assertEquals(1, gui.getClickCount());
    }
}