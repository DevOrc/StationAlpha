package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class GuiComponent {

    protected static final BitmapFont font;

    static {
        if(Gdx.app != null)
            font = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
                    Gdx.files.internal("ui/font.png"), false);
        else
            font = null;
    }

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible = true;

    private ArrayList<GuiComponent> subGuis;

    public GuiComponent() {
        subGuis = new ArrayList<>();
    }


    protected void addGui(GuiComponent gui){
        subGuis.add(gui);
    }

    protected void addAllGui(GuiComponent... comps){
        for(GuiComponent comp : comps){
            addGui(comp);
        }

    }

    void render(SpriteBatch batch){
        if(!visible)
            return;

        update();
        drawBackground(batch);

        for(GuiComponent gui : subGuis){
            gui.render(batch);
        }

        drawForeground(batch);
    }

    protected void update(){}

    public boolean handleClick(int clickX, int clickY) {
        if(!visible)
            return false;

        boolean onGui = isPointOnGui(clickX, clickY);

        if(onGui)
            onClick();

        return handleClickOnSubGui(clickX, clickY) || onGui;
    }

    protected void onClick(){}

    boolean isPointOnGui(int clickX, int clickY){
        return clickX > x && clickY > y && clickX < x  + width && clickY < y + height;
    }

    private boolean handleClickOnSubGui(int x, int y) {
        boolean onGui = false;

        for(GuiComponent gui : subGuis){
            if(gui.isVisible()) {
                if(gui.handleClick(x, y)){
                    onGui = true;
                }
            }
        }

        return onGui;
    }

    protected void setFontData(float size, Color color){
        font.setColor(color);
        font.getData().setScale(size);
    }

    protected abstract void drawBackground(SpriteBatch batch);

    protected abstract void drawForeground(SpriteBatch batch);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible(){
        return visible;
    }

    protected ArrayList<GuiComponent> getSubGuis() {
        return subGuis;
    }
}
