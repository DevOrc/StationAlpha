package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class GuiContainer {

    private final ArrayList<GuiComponent> guis;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ScreenViewport viewport = new ScreenViewport(camera);
    private final SpriteBatch guiBatch = new SpriteBatch();

    public GuiContainer() {
        guis = new ArrayList<>();

        guis.add(new GameGui());
    }

    public void render(){
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Matrix4 matrix = viewport.getCamera().projection;
        matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiBatch.setProjectionMatrix(matrix);

        guiBatch.begin();
        for(GuiComponent gui : guis){
            gui.render(guiBatch);
        }
        guiBatch.end();
    }

    public boolean handleClick(int clickX, int clickY){
        boolean onGui = false;

        for(GuiComponent gui : guis){
            if(gui.isVisible()) {
                if(gui.handleClick(clickX, clickY)){
                    onGui = true;
                }
            }
        }

        return onGui;
    }
}
