package com.noahcharlton.stationalpha.gui.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScrollPaneTests {

    private final EmptyComponent component = new EmptyComponent();
    private final ScrollPane scrollPane = new ScrollPane(component);

    @Test
    void cannotAddSubGuiTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> scrollPane.addGui(new EmptyComponent()));
    }

    @Test
    void cannotAddAllSubGuiTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> scrollPane.addAllGui(new EmptyComponent()));
    }

    @Test
    void clampToContentXTest() {
        component.setX(123);
        scrollPane.clampToContent();

        Assertions.assertEquals(123 - Pane.BORDER_WIDTH, scrollPane.getX());
    }

    @Test
    void clampToContentYTest() {
        component.setY(664);
        scrollPane.clampToContent();

        Assertions.assertEquals(664 - Pane.BORDER_WIDTH, scrollPane.getY());
    }

    @Test
    void clampToContentWidthTest() {
        component.setWidth(88);
        scrollPane.clampToContent();

        Assertions.assertEquals(88 + (Pane.BORDER_WIDTH * 2), scrollPane.getWidth());
    }

    @Test
    void clampToContentHeightTest() {
        component.setHeight(214);
        scrollPane.clampToContent();

        Assertions.assertEquals(214 + (Pane.BORDER_WIDTH * 2), scrollPane.getHeight());
    }
}
