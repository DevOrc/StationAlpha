package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameRenderer {

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final SpriteBatch spriteBatch;

    public GameRenderer() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);
        spriteBatch = new SpriteBatch();
    }

    public void render(){
        clearScreen();
        updateCamera();

        spriteBatch.begin();
        ShapeUtil.drawRect(0, 0, 100, 100, Color.WHITE, spriteBatch);
        spriteBatch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public void updateCamera(){
        CameraInputHandler.update(camera);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
    }
}
