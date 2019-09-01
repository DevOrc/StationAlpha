package com.noahcharlton.stationalpha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.GameRenderer;
import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.PlayerActions;
import com.noahcharlton.stationalpha.gui.GuiContainer;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipes;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadGameException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StationAlpha extends ApplicationAdapter {

    public enum GameState {LOADING, MAIN_MENU, IN_GAME}

    public static final String VERSION = "0.2.0-prelease";
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    private static final Logger logger = LogManager.getLogger(StationAlpha.class);
    private static StationAlpha instance;
    private GameRenderer gameRenderer;

    private GameState currentState = GameState.LOADING;
    private GuiContainer guiContainer;
    private Optional<World> world = Optional.empty();

    private int ticksPerUpdate = 1;
    private double tickLength = 1 / (60.0 * ticksPerUpdate);
    private double tickTime = 0;

    public StationAlpha(boolean updateInstance) {
        if(!updateInstance)
            return;

        if(instance != null)
            throw new GdxRuntimeException("Cannot create instance more than once!");

        instance = this;
    }

    public StationAlpha() {
        this(true);
    }

    @Override
    public void create() {
        gameRenderer = new GameRenderer();

        InputHandler.init();
        Blocks.init();
        Item.init();
        ManufacturingRecipes.init();
        PlayerActions.init();

        guiContainer = new GuiContainer();

        logger.info("Asset Count: " + AssetManager.getInstance().getNumberOfAssets());
    }

    @Override
    public void render() {
        switch(currentState) {
            case IN_GAME:
                gameRenderer.render();
                updateInGame();
                break;
            case LOADING:
                if(AssetManager.getInstance().isDone())
                    currentState = GameState.MAIN_MENU;
                break;
        }

        guiContainer.render();
    }

    public void loadGame(int saveNumber) {
        JobQueue.getInstance().clear();
        currentState = GameState.IN_GAME;
        world = Optional.of(new World(false));
        setTicksPerUpdate(0);

        loadGameSafe(saveNumber);
    }

    private void loadGameSafe(int saveNumber) {
        try{
            world.get().load(saveNumber);
        }catch(RuntimeException e){
            gotoMainMenu();

            throw new LoadGameException(e);
        }
    }

    public void startGame() {
        JobQueue.getInstance().clear();
        currentState = GameState.IN_GAME;
        world = Optional.of(new World(true));

        MessageQueue.getInstance().getMessages().clear();
        MessageQueue.getInstance().add("Welcome!", HelpInfo.get("start_message"));
        MessageQueue.getInstance().add("Controls", HelpInfo.get("controls_message"));
    }

    public void gotoMainMenu() {
        world = Optional.empty();
        currentState = GameState.MAIN_MENU;
    }

    private void updateInGame() {
        tickTime += Gdx.graphics.getDeltaTime();

        while(tickTime > tickLength){
            tickTime -= tickLength;

            world.ifPresent(World::update);
        }
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        AssetManager.getInstance().dispose();
    }

    public void setTicksPerUpdate(int tps) {
        this.tickTime = 0;
        this.ticksPerUpdate = tps;
        this.tickLength = 1 / (60.0 * ticksPerUpdate);
    }

    public int getTicksPerUpdate() {
        return ticksPerUpdate;
    }

    public Optional<World> getWorld() {
        return world;
    }

    public GuiContainer getGuiContainer() {
        return guiContainer;
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    public static StationAlpha getInstance() {
        return instance;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
