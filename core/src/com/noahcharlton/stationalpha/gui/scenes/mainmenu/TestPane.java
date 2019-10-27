package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.components.ScrollPane;

public class TestPane extends Pane {

    public TestPane() {
        setX(400);
        setY(100);
        setWidth(250);
        setHeight(400);

        for(int i = 0; i < 10; i ++){
            int finalI = i;
            MenuButton button = new MenuButton(Integer.toString(i), () -> {
                System.out.println(finalI);
            });
            addGui(button);
        }
    }

    public ScrollPane wrapInScrollPane(){
        ScrollPane pane = new ScrollPane(this);

        pane.clampToContent();
        pane.setHeight(250);
        pane.setY(500 - 250);

        return pane;
    }

    @Override
    protected void updateSize() {}

    @Override
    protected void updatePosition() {
        for(int i = 0; i < 10; i ++){
            getSubGuis().get(i).setX(getX() + 15);
            getSubGuis().get(i).setY(getY() + getHeight() - 60 - (i * 60));
        }
    }
}
