package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.Optional;

public abstract class Pane extends GuiComponent {

    public static final int BORDER_WIDTH = 2;
    private Color borderColor = GuiComponent.ACCENT_COLOR;
    private Optional<Color> hoverColor = Optional.empty();
    private Color backgroundColor = Color.DARK_GRAY;

    private boolean borderNorth;
    private boolean borderSouth;
    private boolean borderEast;
    private boolean borderWest;

    public void drawBackground(SpriteBatch b){
        updatePosition();
        updateSize();

        Color color = isHovering() ? hoverColor.orElse(backgroundColor) : backgroundColor;
        ShapeUtil.drawRect(getX(), getY(), getWidth(), getHeight(), color, b);

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

    public void setDrawBorder(boolean north, boolean east, boolean south, boolean west){
        borderNorth = north;
        borderEast = east;
        borderSouth = south;
        borderWest = west;
    }

    public void drawForeground(SpriteBatch b){

    }

    protected GlyphLayout drawCenteredText(SpriteBatch b, String text, int y){
        int padding = BORDER_WIDTH * 2;

        return font.draw(b, text, getX() + padding, getY() + y, getWidth() - (padding * 2),
                Align.center, true);
    }

    protected abstract void updatePosition();

    protected abstract void updateSize();

    protected void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = Optional.ofNullable(hoverColor);
    }

    protected void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
