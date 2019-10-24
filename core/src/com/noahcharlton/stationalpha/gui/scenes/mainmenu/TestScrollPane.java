package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.ScrollPane;

public class TestScrollPane extends ScrollPane {

    public TestScrollPane() {
        setX(400);
        setY(200);
        setWidth(250);
        setHeight(250);

        MenuButton button = new MenuButton("Hi", () -> {});
        button.setX(getX() + 15);
        button.setY(getY() + 150);
        addGui(button);
    }
}
