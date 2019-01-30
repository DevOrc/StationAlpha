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

    @Test
    void onClickNotVisibleTest() {
        TestGui gui = new TestGui();
        gui.setHeight(100);
        gui.setWidth(100);
        gui.setVisible(false);

        gui.handleClick(50, 50);

        Assertions.assertEquals(0, gui.getClickCount());
    }

    @Test
    void onGuiChildClickTest() {
        TestGui parent = new TestGui();
        TestGui child = new TestGui();
        child.setWidth(100);
        child.setHeight(100);

        parent.addGui(child);

        parent.handleClick(10, 10);

        Assertions.assertEquals(1, child.getClickCount());
    }
}