package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.VStretchLayout;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;

import java.util.List;

public abstract class BuildMenu<T> extends ComponentGroup {

    private static final int WIDTH = 225;
    private static final int HEIGHT = 330;

    public BuildMenu(List<T> items) {
        super(new VStretchLayout());
        setDrawBorder(true, true, false,false);

        createButtons(items);
    }

    private void createButtons(List<T> items) {
        for(int i = 0; i < items.size(); i++){
            T item = items.get(i);

            MenuButton button = new MenuButton(item.toString(), createRunnable(item));
            button.setX(0);
            button.setY(getY() + i * (65) + 5);

            addGui(button);
        }
    }

    @Override
    protected void updatePosition() {
        setX(0);
        setY(BuildBar.HEIGHT);
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    protected abstract Runnable createRunnable(T item);

    public abstract String getName();

    @Override
    public String toString() {
        return String.format("[%s: %b]", getName(), isVisible());
    }

}
