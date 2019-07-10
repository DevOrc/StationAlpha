package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class LoadingScreen extends Pane {

    static final int BAR_WIDTH = 450;
    static final int BAR_HEIGHT = 80;
    static final int BORDER = 4;

    public LoadingScreen() {
        setDrawBorder(true, true, true, true);

        setBackgroundColor(Color.BLACK);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        int barX = (Gdx.graphics.getWidth() / 2) - (BAR_WIDTH / 2);
        int barY = Gdx.graphics.getHeight() / 5;
        int innerBarWidth = calcInnerBar(AssetManager.getInstance());

        ShapeUtil.drawRect(barX - BORDER, barY - BORDER, BAR_WIDTH + (BORDER * 2),
                BAR_HEIGHT + (BORDER * 2), Color.WHITE, b);

        ShapeUtil.drawRect(barX, barY, innerBarWidth, BAR_HEIGHT, GuiComponent.ACCENT_COLOR, b);
    }

    int calcInnerBar(AssetManager assets) {
        double percent = (double) assets.getNumberOfCompletedTasks() / assets.getNumberOfAssets();

        return (int) (percent * BAR_WIDTH);
    }

    @Override
    protected void updatePosition() {
        setX(0);
        setY(0);
    }

    @Override
    protected void updateSize() {
        setWidth(Gdx.graphics.getWidth());
        setHeight(Gdx.graphics.getHeight());
    }
}
