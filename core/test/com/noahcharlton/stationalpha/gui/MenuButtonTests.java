package com.noahcharlton.stationalpha.gui;

import com.noahcharlton.stationalpha.gui.components.MenuButton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MenuButtonTests{

    private int timesRan;

    private final MenuButton button = new MenuButton("", () -> {
        timesRan++;
    });

    @Test
    void basicTest() {
        button.setHeight(100);
        button.setWidth(100);

        button.handleClick(50, 3);

        Assertions.assertEquals(1, timesRan);
    }
}
