package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.engine.CameraInputHandler;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

public class InputHandler implements SimpleInputProcessor {

    private static final InputHandler instance = new InputHandler();
    private static final Logger logger = LogManager.getLogger(InputHandler.class);

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer(this);
    private final BuildManager buildManager = new BuildManager();

    private Optional<Selectable> currentlySelected = Optional.empty();

    InputHandler() {
    }

    public static void init() {
        Objects.requireNonNull(getInstance(), "Instance cannot be null!");
        Gdx.input.setInputProcessor(getInstance().getInputMultiplexer());

        logger.debug("Loaded Input Handler!");
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int graphicsY = Gdx.graphics.getHeight() - screenY;
        boolean onGui = StationAlpha.getInstance().getGuiContainer().handleClick(screenX, graphicsY, false);

        if(onGui)
            return false;

        if(World.getInstance().isPresent()) {
            buildManager.handleGameDrag(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int graphicsY = Gdx.graphics.getHeight() - screenY;
        boolean onGui = StationAlpha.getInstance().getGuiContainer().handleClick(screenX, graphicsY, true);

        logger.info("Click Event[x = {}, y = {}, button = {}, onGui = {}]", screenX, graphicsY, button, onGui);

        if(World.getInstance().isPresent() && !onGui) {
            buildManager.handleGameClick(screenX, screenY, button);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(DebugKeys.DEBUG_KEY)) {
            switch(keycode) {
                case DebugKeys.PATHFIND:
                    setBuildAction(new WorkerPathfindAction(World.getInstance().get()));
                    break;
                default:
                    break;
            }
        }

        buildManager.getAction().ifPresent(action -> action.onKeyPressed(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        StationAlpha instance = StationAlpha.getInstance();

        switch(character) {
            case ' ':
                if(instance.getTicksPerUpdate() == 0) {
                    instance.setTicksPerUpdate(1);
                } else {
                    instance.setTicksPerUpdate(0);
                }
                break;
            case '1':
                instance.setTicksPerUpdate(1);
                break;
            case '2':
                instance.setTicksPerUpdate(2);
                break;
            case '3':
                instance.setTicksPerUpdate(3);
                break;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        boolean onGui = StationAlpha.getInstance().getGuiContainer().handleClick(mouseX, mouseY, false);

        if(onGui)
            return false;

        OrthographicCamera camera = StationAlpha.getInstance().getGameRenderer().getCamera();

        CameraInputHandler.onScroll(camera, amount);

        return false;
    }

    public void setBuildAction(BuildAction action) {
        logger.debug("New Build Action: " + action);

        buildManager.setAction(action);
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }

    public void setCurrentlySelected(Selectable currentlySelected) {
        this.currentlySelected = Optional.ofNullable(currentlySelected);
    }

    public Optional<Selectable> getCurrentlySelected() {
        return currentlySelected;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static InputHandler getInstance() {
        return instance;
    }
}
