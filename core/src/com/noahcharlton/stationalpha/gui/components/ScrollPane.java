package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.SimpleInputProcessor;

public class ScrollPane extends Pane implements SimpleInputProcessor {

    public static final int SCROLL_BAR_SIZE = 10;
    private static final int SCROLL_SCALE = 35;
    private int scrollY = 0;

    public ScrollPane() {
        InputHandler.getInstance().getInputMultiplexer().addProcessor(this);
    }

    @Override
    protected void updatePosition() {}

    @Override
    protected void updateSize() { }

    @Override
    public boolean scrolled(int amount) {
        if(isHovering()){
            scrollY += amount * SCROLL_SCALE;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!isHovering())
            return false;

        switch(keycode){
            case Input.Keys.PAGE_DOWN:
                scrollY += getHeight();
                break;
            case Input.Keys.PAGE_UP:
                scrollY -= getHeight();
                break;
        }

        return false;
    }

    @Override
    protected void renderSubGuis(SpriteBatch batch) {
        Matrix4 regularProjection = batch.getProjectionMatrix().cpy();
        Matrix4 newProjection = regularProjection.cpy();
        batch.end();

        newProjection.translate(0, scrollY, 0);
        renderSubGuis(batch, newProjection);

        batch.setProjectionMatrix(regularProjection);
        batch.begin();
    }

    private void renderSubGuis(SpriteBatch batch, Matrix4 newProjection) {
        batch.setProjectionMatrix(newProjection);
        Gdx.graphics.getGL20().glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.graphics.getGL20().glScissor(getX(), getY(), getWidth(), getHeight());

        batch.begin();
        super.renderSubGuis(batch);
        batch.end();
        Gdx.graphics.getGL20().glDisable(GL20.GL_SCISSOR_TEST);
    }
}
