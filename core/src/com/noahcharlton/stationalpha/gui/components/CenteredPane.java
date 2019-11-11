package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;

public class CenteredPane extends Pane {

    @Override
    protected void updateSize() {}

    @Override
    protected void updatePosition() {
        setX((Gdx.graphics.getWidth() / 2)  - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() / 2)  - (getHeight() / 2));
    }
}
