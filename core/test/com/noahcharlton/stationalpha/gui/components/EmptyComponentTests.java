package com.noahcharlton.stationalpha.gui.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyComponentTests {

    private final EmptyComponent component = new EmptyComponent();

    @Test
    void cannotAddSubGuiTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> component.addGui(new EmptyComponent()));
    }

    @Test
    void cannotAddAllSubGuiTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> component.addAllGui(new EmptyComponent()));
    }
}
