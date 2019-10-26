package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.ScrollPane;

public class TestPane extends ScrollPane {

    public TestPane() {
        setX(400);
        setY(200);
        setWidth(250);
        setHeight(250);

        for(int i = 0; i < 10; i ++){
            MenuButton button = new MenuButton(Integer.toString(i), () -> {});
            button.setX(getX() + 15);
            button.setY(getY() + getHeight() - 60 - (i * 60));
            addGui(button);
        }
    }
}
