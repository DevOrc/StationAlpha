package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.VStretchLayout;

import java.util.List;

public abstract class BuildBarListMenu<T> extends ComponentGroup implements BuildMenu{

    protected static final int WIDTH = 225;
    protected static final int HEIGHT = 330;

    public BuildBarListMenu(List<T> items) {
        super(new VStretchLayout());
        setDrawBorder(true, true, false,false);
        updatePosition();

        createButtons(items);
    }

    protected void createButtons(List<T> items) {
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
        setY(0);
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    protected abstract Runnable createRunnable(T item);

    public abstract String getName();

    public abstract InGameIcon getIcon();

    @Override
    public GuiComponent getComponent() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("[%s: %b]", getName(), isVisible());
    }

}
