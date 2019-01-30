package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class DebugBox extends Pane {

    private static final int SIZE = 250;

    public DebugBox() {
        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.5f, Color.WHITE);
        drawCenteredText(b, "Debug Info: ", getHeight() - 10);
        drawCenteredText(b, "FPS: " + Gdx.graphics.getFramesPerSecond(), getHeight() - 40);
        drawCenteredText(b, "Build: " + InputHandler.getInstance().getBuildManager().getAction()
                .map(BuildAction::getName).orElse("None"), getHeight() - 70);
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
