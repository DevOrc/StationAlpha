package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuButton extends Pane{

    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    private static final Logger logger = LogManager.getLogger(MenuButton.class);

    private final Runnable onClick;

    private String text;

    public MenuButton(String text, Runnable onClick) {
        this.onClick = onClick;

        setText(text);
        setBackgroundColor(new Color(0x2a2a2aff));
        setDrawBorder(true, true, true, true);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onClick() {
        logger.debug("Button Clicked - Name: " + text);
        onClick.run();
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, Color.WHITE);
        drawCenteredText(b, text, getHeight() - (getHeight() / 3));
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
    protected void updatePosition() {

    }

    @Override
    protected void updateSize() {

    }
}
