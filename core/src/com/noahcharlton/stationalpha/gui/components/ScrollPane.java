package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class ScrollPane extends Pane {

    private int scrollX = 0;
    private int scrollY = 0;

    @Override
    protected void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.B)){
            scrollY++;
        }else if(Gdx.input.isKeyPressed(Input.Keys.V)){
            scrollY--;
        }
    }

    @Override
    protected void drawSubGuis(SpriteBatch batch) {
        Matrix4 regularProjection = batch.getProjectionMatrix().cpy();
        Matrix4 newProjection = regularProjection.cpy();
        batch.end();
        newProjection.translate(scrollX, scrollY, 0);

        batch.setProjectionMatrix(newProjection);
        Gdx.graphics.getGL20().glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.graphics.getGL20().glScissor(getX(), getY(), getWidth(), getHeight());

        batch.begin();
        super.drawSubGuis(batch);
        batch.end();
        Gdx.graphics.getGL20().glDisable(GL20.GL_SCISSOR_TEST);

        batch.setProjectionMatrix(regularProjection);
        batch.begin();
    }

    @Override
    protected void updateSize() {}

    @Override
    protected void updatePosition() {}
}
