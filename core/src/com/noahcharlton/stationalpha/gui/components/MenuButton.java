package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuButton extends ButtonBase{

    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    private static final Logger logger = LogManager.getLogger(MenuButton.class);

    private String text;

    public MenuButton(String text, Runnable onClick) {
        super(onClick);

        setText(text);
        setBackgroundColor(new Color(0x2a2a2aff));
        setHoverColor(new Color(0x252525ff));
        setDrawBorder(true, true, true, true);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void update() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if(isPointOnGui(mouseX, mouseY)){

        }else{

        }
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, Color.WHITE);
        drawCenteredText(b, text, getHeight() - (getHeight() / 3));
    }

    @Override
    protected void updatePosition() {

    }

    @Override
    protected void updateSize() {

    }
}
