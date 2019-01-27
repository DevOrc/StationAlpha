package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGui extends GuiComponent{

    private int clickCount;

    @Override
    protected void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    protected void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    protected void setX(int x) {
        super.setX(x);
    }

    @Override
    protected void setY(int y) {
        super.setY(y);
    }

    @Override
    protected void onClick() {
        clickCount++;
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {

    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }

    public int getClickCount() {
        return clickCount;
    }
}