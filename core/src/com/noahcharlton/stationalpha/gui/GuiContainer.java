package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.gui.scenes.mainmenu.MainMenu;

import java.util.ArrayList;

public class GuiContainer {

    private final ArrayList<GuiComponent> guis;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ScreenViewport viewport = new ScreenViewport(camera);
    private final SpriteBatch guiBatch;

    {
        if(Gdx.graphics != null)
            guiBatch = new SpriteBatch();
        else
            guiBatch = null;
    }

    private final GameGui gameGui;
    private final MainMenu mainMenu;

    public GuiContainer() {
        guis = new ArrayList<>();

        this.gameGui = new GameGui();
        this.mainMenu = new MainMenu();

        guis.add(gameGui);
        guis.add(mainMenu);
    }

    public void render(){
        updateScenes(StationAlpha.getInstance().getCurrentState());

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

    void updateScenes(StationAlpha.GameState gameState) {
        gameGui.setVisible(false);
        mainMenu.setVisible(false);

        switch(gameState){
            case IN_GAME:
                gameGui.setVisible(true);
                break;
            case MAIN_MENU:
                mainMenu.setVisible(true);
                break;
        }
    }

    public boolean handleClick(int clickX, int clickY, boolean actualClick){
        boolean onGui = false;

        for(GuiComponent gui : guis){
            if(gui.isVisible()) {
                if(gui.handleClick(clickX, clickY, actualClick)){
                    onGui = true;
                }
            }
        }

        return onGui;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public GameGui getGameGui() {
        return gameGui;
    }
}
