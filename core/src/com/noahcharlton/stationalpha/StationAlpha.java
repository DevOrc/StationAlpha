package com.noahcharlton.stationalpha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.GameRenderer;
import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.GuiContainer;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipes;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StationAlpha extends ApplicationAdapter {

	public enum GameState{MAIN_MENU, IN_GAME}

	private static final Logger logger = LogManager.getLogger(StationAlpha.class);
	private static StationAlpha instance;
	private GameRenderer gameRenderer;

	private GameState currentState = GameState.MAIN_MENU;
	private GuiContainer guiContainer;
	private Optional<World> world = Optional.empty();
	private int ticksPerUpdate = 1;

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
	public void create () {
		gameRenderer = new GameRenderer();

		InputHandler.init();
		Blocks.init();
		Item.init();
		ManufacturingRecipes.init();

		guiContainer = new GuiContainer();

		logger.info("Asset Count: " + AssetManager.getInstance().getNumberOfAssets());
	}

	@Override
	public void render () {
		while(!AssetManager.getInstance().isDone()){
			return;
		}

		gameRenderer.render();
		guiContainer.render();

		update();
	}

	private void update() {
		switch(currentState){
			case IN_GAME:
				updateInGame();
				break;
		}
	}

	public void startGame(){
		currentState = GameState.IN_GAME;
		world = Optional.of(new World(true));
	}

	private void updateInGame() {
		for(int i = 0; i < ticksPerUpdate; i++){
			world.ifPresent(World::update);
		}
	}

	@Override
	public void resize(int width, int height) {
		gameRenderer.resize(width, height);
	}

	@Override
	public void dispose () {
		AssetManager.getInstance().dispose();
	}

	public void setTicksPerUpdate(int ticksPerUpdate) {
		this.ticksPerUpdate = ticksPerUpdate;
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
