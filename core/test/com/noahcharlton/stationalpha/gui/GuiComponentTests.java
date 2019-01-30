package com.noahcharlton.stationalpha.gui;

import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuiComponentTests extends LibGdxTest {

    @Test
    public void addAllTest(){
        TestGui mainGui = new TestGui();
        TestGui guiOne = new TestGui();
        TestGui guiTwo = new TestGui();
        TestGui guiThree = new TestGui();

        mainGui.addAllGui(guiOne, guiTwo, guiThree);

        Assertions.assertArrayEquals(new TestGui[]{guiOne, guiTwo, guiThree}, mainGui.getSubGuis().toArray());
    }
}
