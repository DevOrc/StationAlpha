package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class DebugBar extends Pane {

    private static final int SIZE = 250;

    public DebugBar() {
        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.5f, Color.WHITE);
        drawCenteredText(b, "Debug Info: ", getHeight() - 10);
        drawCenteredText(b, "FPS: " + Gdx.graphics.getFramesPerSecond(), getHeight() - 60);
    }

    @Override
    protected void updatePosition() {
        this.setX((Gdx.graphics.getWidth() / 2) - (SIZE / 2));
        this.setY((Gdx.graphics.getHeight() / 2) - (SIZE / 2));
    }

    @Override
    protected void onClick() {
        System.out.println("test");
    }

    @Override
    protected void updateSize() {
        setWidth(SIZE);
        setHeight(SIZE);
    }
}
