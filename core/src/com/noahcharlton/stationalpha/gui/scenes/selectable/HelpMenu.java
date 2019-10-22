package com.noahcharlton.stationalpha.gui.scenes.selectable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class HelpMenu extends Pane {

    private static final int WIDTH = 750;
    private static final int HEIGHT = 500;

    private final CloseHelpMenuButton closeButton = new CloseHelpMenuButton(this);

    public HelpMenu() {
        setVisible(false);

        setBackgroundColor(Color.DARK_GRAY);
        setDrawBorder(true, true, true, true);

        addAllGui(closeButton);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        InputHandler.getInstance().getCurrentlySelected()
                .ifPresent(s -> s.getHelpInfo().ifPresent(helpInfo -> render(b, s.getTitle(), helpInfo)));
    }

    private void render(SpriteBatch b, String title, String helpInfo) {
        setFontData(1f, Color.WHITE);
        font.draw(b, "Help:  " + title, getX() + 20, getY() + getHeight() - 25);

        setFontData(.75f, Color.WHITE);
        font.draw(b, helpInfo, getX() + 20, getY() + getHeight() - 75, WIDTH - 40,
                Align.left, true);
    }

    @Override
    protected void updatePosition() {
        setX((Gdx.graphics.getWidth() / 2) - (WIDTH / 2));
        setY((Gdx.graphics.getHeight() / 2) - (HEIGHT / 2));
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }
}

class CloseHelpMenuButton extends Pane {

    private static final int SIZE = 24;
    private final HelpMenu menu;

    public CloseHelpMenuButton(HelpMenu menu) {
        this.menu = menu;

        setDrawBorder(true, true, true, true);
    }

    @Override
    protected void onClick() {
        menu.setVisible(false);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, isHovering() ? Color.RED : Color.FIREBRICK);
        drawCenteredText(b, "X", SIZE * 9 / 10);
    }

    @Override
    protected void updateSize() {
        setWidth(SIZE);
        setHeight(SIZE);
    }

    @Override
    protected void updatePosition() {
        setX(menu.getX() + menu.getWidth() - SIZE);
        setY(menu.getY() + menu.getHeight() - SIZE);
    }
}
