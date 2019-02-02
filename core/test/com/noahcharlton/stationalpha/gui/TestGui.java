package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestGui extends GuiComponent{

    private int clickCount;

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
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