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

    public static final int SCROLL_VBAR_HEIGHT = 80;
    public static final int SCROLL_VBAR_WIDTH = 20;
    public static final int SCROLL_HBAR_HEIGHT = 20;
    public static final int SCROLL_HBAR_WIDTH = 200;

    private static final int MOUSE_SCROLL_SCALE = 35;
    private static final int KEY_SCROLL_SCALE = 10;

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

    private int getMaximumScrollX(){
        if(component.getWidth() < getWidth()){
            return 0;
        }

        return component.getWidth() - getWidth();
    }

    private int getMaximumScrollY(){
        if(component.getHeight() < getHeight()){
            return 0;
        }

        return component.getHeight() - getHeight();
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
            int scrollYNew = scrollY + (amount * MOUSE_SCROLL_SCALE);

            int translation = scrollY - scrollYNew;
            scrollY(-translation);
        }

        return false;
    }

    private void scrollY(int translation) {
        int newPos = Math.max(0, Math.min(getMaximumScrollY(), scrollY + translation));
        int relative = newPos - scrollY;

        component.setY(component.getY() + relative);
        scrollY += relative;
    }

    private void scrollX(int translation) {
        int newPos = Math.max(0, Math.min(getMaximumScrollX(), scrollX + translation));
        int relative = newPos - scrollX;

        component.setX(component.getX() + relative);
        scrollX += relative;
    }


    @Override
    protected void update() {
        updateVScrollBarMouse();
        updateHScrollBarMouse();

        updateKeyMovement();
    }

    private void updateHScrollBarMouse() {
        if(isBarPressed(getHScrollBarDimensions())) {
            int mouseX = Gdx.input.getX() - (SCROLL_HBAR_WIDTH);
            double percentScroll = (double) mouseX / (getWidth() - SCROLL_HBAR_WIDTH - BORDER_WIDTH * 2);
            int newScroll = (int) (getMaximumScrollX() * percentScroll);
            int relativeScroll = newScroll - scrollX;
            scrollX(relativeScroll);
        }
    }

    private void updateVScrollBarMouse() {
        if(isBarPressed(getVScrollBarDimensions())) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            int mouseRelativeToTop = getY() + getHeight() - mouseY - (SCROLL_VBAR_HEIGHT / 3);
            double percentScroll = (double) mouseRelativeToTop / (getHeight() - SCROLL_VBAR_HEIGHT - BORDER_WIDTH * 2);
            int newScroll = (int) (getMaximumScrollY() * percentScroll);
            int relativeScroll = newScroll - scrollY;
            scrollY(relativeScroll);
        }
    }

    private void updateKeyMovement() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            scrollX(-KEY_SCROLL_SCALE);
        }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            scrollX(KEY_SCROLL_SCALE);
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            scrollY(-KEY_SCROLL_SCALE);
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            scrollY(KEY_SCROLL_SCALE);
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
        if(getMaximumScrollY() != 0)
            renderScrollBar(b, getVScrollBarDimensions());

        if(getMaximumScrollX() != 0)
            renderScrollBar(b, getHScrollBarDimensions());
    }

    private void renderScrollBar(SpriteBatch b, Rectangle dimensions) {
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

    private Rectangle getHScrollBarDimensions() {
        int padding = 5;
        double percentScrolled = (double) scrollX / getMaximumScrollX();

        int interiorWidth = getWidth() - ((BORDER_WIDTH + padding) * 2) - SCROLL_HBAR_WIDTH;

        int barXInterior = (int) (percentScrolled * interiorWidth);
        int barX = getX() + barXInterior + padding;

        int barY = getY() + padding;

        return new Rectangle(barX, barY, SCROLL_HBAR_WIDTH, SCROLL_HBAR_HEIGHT);
    }

    private Rectangle getVScrollBarDimensions() {
        int padding = 5;
        double percentScrolled = (double) scrollY / getMaximumScrollY();
        int interiorHeight = (getHeight() - ((BORDER_WIDTH + padding) * 2) - SCROLL_VBAR_HEIGHT);

        int barYInterior = (int) (percentScrolled * interiorHeight);
        int barY = (getY() + getHeight()) - barYInterior - BORDER_WIDTH - SCROLL_VBAR_HEIGHT - padding;

        int barX = getX() + getWidth() - BORDER_WIDTH - SCROLL_VBAR_WIDTH - padding;

        return new Rectangle(barX, barY, SCROLL_VBAR_WIDTH, SCROLL_VBAR_HEIGHT);
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
