package com.noahcharlton.stationalpha.gui.components.layout;

import com.noahcharlton.stationalpha.gui.GameGui;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.TestGui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class HStretchLayoutTest {

    private final HStretchLayout layout = new HStretchLayout();

    @Test
    void paddingLeftLayoutTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        components.add(new TestGui());
        TestGui parent = new TestGui();
        parent.setWidth(1000);

        layout.setPadding(123);
        layout.layout(parent, components);

        Assertions.assertEquals(123, components.get(0).getX());
    }

    @Test
    void paddingTopLayoutTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        TestGui child = new TestGui();
        components.add(child);
        TestGui parent = new TestGui();
        parent.setHeight(200);

        layout.setPadding(50);
        layout.layout(parent, components);

        Assertions.assertEquals(150, child.getY() + child.getHeight());
    }

    @Test
    void paddingBottomLayoutTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        TestGui child = new TestGui();
        components.add(child);
        TestGui parent = new TestGui();
        parent.setHeight(100);

        layout.setPadding(25);
        layout.layout(parent, components);

        Assertions.assertEquals(25, child.getY());
    }

    @Test
    void paddingRightLayoutTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        TestGui child = new TestGui();
        components.add(child);
        TestGui parent = new TestGui();
        parent.setWidth(100);

        layout.setPadding(25);
        layout.layout(parent, components);

        Assertions.assertEquals(75, child.getX() + child.getWidth());
    }

    @Test
    void basicHGapTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        TestGui childOne = new TestGui();
        TestGui childTwo = new TestGui();
        components.addAll(Arrays.asList(childOne, childTwo));
        TestGui parent = new TestGui();
        parent.setWidth(100);

        layout.setHGap(17);
        layout.layout(parent, components);

        Assertions.assertEquals(17, childTwo.getX() - (childOne.getX() + childOne.getWidth()));
    }

    @Test
    void heightFollowsParentTest() {
        ArrayList<GuiComponent> components = new ArrayList<>();
        TestGui child = new TestGui();
        components.add(child);
        TestGui parent = new TestGui();
        parent.setHeight(123456);

        layout.setPadding(0);
        layout.layout(parent, components);

        Assertions.assertEquals(123456, child.getHeight());
    }

    @Test
    void zeroChildrenLayoutTest() {
        layout.layout(new GameGui(), new ArrayList<>());
    }
}
