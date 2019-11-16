package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuButton extends ButtonBase{

    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    protected static final Color backgroundColor = new Color(0x2a2a2aff);
    protected static final Color hoverColor = new Color(0x252525ff);

    private static final Logger logger = LogManager.getLogger(MenuButton.class);

    private Color textColor = Color.WHITE;
    private String text;

    public MenuButton(String text, Runnable onClick) {
        super(onClick);

        setText(text);
        setBackgroundColor(backgroundColor);
        setHoverColor(hoverColor);
        setDrawBorder(true, true, true, true);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, textColor);
        drawCenteredText(b, text, getHeight() - (getHeight() / 4));
    }

    @Override
    protected void updatePosition() {

    }

    @Override
    protected void updateSize() {

    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
}
