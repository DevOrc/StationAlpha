package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.worker.WorkerRenderer;
import com.noahcharlton.stationalpha.world.World;

public class GameRenderer {

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final SpriteBatch spriteBatch;

    public GameRenderer() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);
        spriteBatch = new SpriteBatch();

        initRenderers();
    }

    private void initRenderers() {
        WorkerRenderer.init();
    }

    public void render(){
        clearScreen();
        updateCamera();

        spriteBatch.begin();
        renderWorldHaze();
        World.getInstance().ifPresent(world -> world.render(spriteBatch));
        InputHandler.getInstance().getBuildManager().getAction().ifPresent(action -> action.render(spriteBatch));
        spriteBatch.end();
    }

    private void renderWorldHaze() {
        int thickness = 8;
        int worldWidth = World.WORLD_PIXEL_SIZE + thickness;

        if(InputHandler.getInstance().getBuildManager().getAction().isPresent()){
            ShapeUtil.drawRect(-thickness, -thickness, thickness, worldWidth, Color.BLUE, spriteBatch);
            ShapeUtil.drawRect(-thickness , -thickness, worldWidth, thickness, Color.BLUE, spriteBatch);
            ShapeUtil.drawRect(worldWidth - thickness, -thickness, thickness, worldWidth, Color.BLUE, spriteBatch);
            ShapeUtil.drawRect(-thickness, worldWidth - thickness, worldWidth, thickness, Color.BLUE, spriteBatch);
        }
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

    public OrthographicCamera getCamera() {
        return camera;
    }

    public ScreenViewport getViewport() {
        return viewport;
    }
}
