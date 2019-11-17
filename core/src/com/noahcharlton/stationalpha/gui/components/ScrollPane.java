package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.SimpleInputProcessor;
import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.awt.*;
import java.util.Objects;

public class ScrollPane extends Pane implements SimpleInputProcessor {

    public static final int SCROLL_BAR_HEIGHT = 80;
    public static final int SCROLL_BAR_WIDTH = 20;
    private static final int SCROLL_SCALE = 35;

    private GuiComponent component;
    private int scrollY = 0;
    private int scrollX = 0;

    public ScrollPane(GuiComponent component) {
        this.component = Objects.requireNonNull(component);
        InputHandler.getInstance().getInputMultiplexer().addProcessor(this);

        setDrawBorder(true, true, true, true);

        clampToContent();
    }

    public void clampToContent() {
        setX(component.getX() - BORDER_WIDTH);
        setY(component.getY() - BORDER_WIDTH);
        setWidth(component.getWidth() + (BORDER_WIDTH * 2));
        setHeight(component.getHeight() + (BORDER_WIDTH * 2));
    }

    @Override
    protected boolean handleClickOnSubGui(int x, int y, boolean actualClick) {
        if(component.isVisible()) {
            return component.handleClick(x, y, actualClick);
        }

        return false;
    }

    @Override
    protected void updatePosition() {
    }

    @Override
    protected void updateSize() {
    }

    @Override
    public boolean scrolled(int amount) {
        if(isHovering() && isVisible()) {
            int scrollYNew = scrollY + (amount * SCROLL_SCALE);

            int translation = scrollY - scrollYNew;
            scrollY(-translation);
        }

        return false;
    }

    private void scrollY(int translation) {
        int newPos = Math.max(0, Math.min(component.getHeight(), scrollY + translation));
        int relative = newPos - scrollY;

        component.setY(component.getY() + relative);
        scrollY += relative;
    }

    private void scrollX(int translation) {
        int newPos = Math.max(0, Math.min(component.getWidth(), scrollX + translation));
        int relative = newPos - scrollX;

        component.setX(component.getX() + relative);
        scrollX += relative;
    }


    @Override
    protected void update() {
        if(isBarPressed(getScrollBarDimensions())) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            int mouseRelativeToTop = getY() + getHeight() - mouseY - (SCROLL_BAR_HEIGHT / 3);
            double percentScroll = (double) mouseRelativeToTop / (getHeight() - SCROLL_BAR_HEIGHT - BORDER_WIDTH * 2);
            int newScroll = (int) (component.getHeight() * percentScroll);
            int relativeScroll = newScroll - scrollY;
            scrollY(relativeScroll);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!isHovering() || !isVisible())
            return false;

        switch(keycode) {
            case Input.Keys.PAGE_DOWN:
                scrollY(getHeight());
                break;
            case Input.Keys.PAGE_UP:
                scrollY(-getHeight());
                break;
            case Input.Keys.RIGHT:
                scrollX(20);
                break;
            case Input.Keys.LEFT:
                scrollX(-20);
                break;
            case Input.Keys.UP:
                scrollY(20);
                break;
            case Input.Keys.DOWN:
                scrollY(-20);
                break;
        }

        return false;
    }

    @Override
    protected void renderSubGuis(SpriteBatch batch) {
        batch.end();
        Gdx.graphics.getGL20().glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.graphics.getGL20().glScissor(getX() + BORDER_WIDTH, getY() + BORDER_WIDTH,
                getWidth() - (BORDER_WIDTH * 2), getHeight() - (BORDER_WIDTH * 2));
        batch.begin();

        component.render(batch);

        batch.end();
        Gdx.graphics.getGL20().glDisable(GL20.GL_SCISSOR_TEST);
        batch.begin();
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        Rectangle dimensions = getScrollBarDimensions();
        Color color = isBarPressed(dimensions) ? Color.GRAY : Color.WHITE;
        int x = (int) dimensions.getX();
        int y = (int) dimensions.getY();
        int width = (int) dimensions.getWidth();
        int height = (int) dimensions.getHeight();

        ShapeUtil.drawRect(x, y, width, height, color, b);
    }


    private boolean isBarPressed(Rectangle dimension) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        boolean mouseOver = dimension.contains(new Point(mouseX, mouseY));
        boolean mouseDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        return mouseDown && mouseOver && isVisible();
    }


    private Rectangle getScrollBarDimensions() {
        int padding = 5;
        double percentScrolled = (double) scrollY / component.getHeight();
        int interiorHeight = (getHeight() - ((BORDER_WIDTH + padding) * 2) - SCROLL_BAR_HEIGHT);

        int barYInterior = (int) (percentScrolled * interiorHeight);
        int barY = (getY() + getHeight()) - barYInterior - BORDER_WIDTH - SCROLL_BAR_HEIGHT - padding;

        int barX = getX() + getWidth() - BORDER_WIDTH - SCROLL_BAR_WIDTH - padding;

        return new Rectangle(barX, barY, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
    }

    @Override
    public void addGui(GuiComponent gui) {
        throw new UnsupportedOperationException();
    }

    public int getScrollY() {
        return scrollY;
    }

    public int getScrollX() {
        return scrollX;
    }
}
