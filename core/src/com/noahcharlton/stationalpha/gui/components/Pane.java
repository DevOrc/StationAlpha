package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public abstract class Pane extends GuiComponent {

    private static final int BORDER_WIDTH = 2;
    private Color borderColor = Color.FIREBRICK;
    private Color backgroundColor = Color.DARK_GRAY;

    private boolean borderNorth;
    private boolean borderSouth;
    private boolean borderEast;
    private boolean borderWest;

    public void drawBackground(SpriteBatch b){
        updatePosition();
        updateSize();

        ShapeUtil.drawRect(getX(), getY(), getWidth(), getHeight(), backgroundColor, b);

        drawBorder(b);
    }

    private void drawBorder(SpriteBatch b) {
        if(borderNorth) {
            ShapeUtil.drawRect(getX(), getY() + getHeight() - BORDER_WIDTH,
                    getWidth(), BORDER_WIDTH, borderColor, b);
        }

        if(borderSouth) {
            ShapeUtil.drawRect(getX(), getY(),
                    getWidth(), BORDER_WIDTH, borderColor, b);
        }

        if(borderEast) {
            ShapeUtil.drawRect(getX() + getWidth() - 2, getY(),
                    BORDER_WIDTH, getHeight(), borderColor, b);
        }

        if(borderWest) {
            ShapeUtil.drawRect(getX(), getY(),
                    BORDER_WIDTH, getHeight(), borderColor, b);
        }
    }

    @Override
    public boolean handleClick(int clickX, int clickY) {
        return super.handleClick(clickX, clickY);
    }

    public void setDrawBorder(boolean north, boolean east, boolean south, boolean west){
        borderNorth = north;
        borderEast = east;
        borderSouth = south;
        borderWest = west;
    }

    public void drawForeground(SpriteBatch b){

    }

    protected GlyphLayout drawCenteredText(SpriteBatch b, String text, int y){
        return font.draw(b, text, getX(), getY() + y, getWidth(), Align.center, true);
    }

    protected abstract void updatePosition();

    protected abstract void updateSize();

    protected void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
