package com.noahcharlton.stationalpha.gui.scenes.selectable;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.gui.components.MenuButton;

public class SelectableHelpButton extends MenuButton {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 35;

    private final SelectableBox selectableBox;

    public SelectableHelpButton(SelectableBox box, Runnable onClick) {
        super("Help", onClick);

        setDrawBorder(true, true, false, true);

        this.selectableBox = box;
    }

    @Override
    protected void updatePosition() {
        setX(Gdx.graphics.getWidth() - WIDTH);
        setY(selectableBox.getY() + selectableBox.getHeight());
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }
}
