package com.noahcharlton.stationalpha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.GameRenderer;
import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.GuiContainer;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StationAlpha extends ApplicationAdapter {

	private static final Logger logger = LogManager.getLogger(StationAlpha.class);
	private static StationAlpha instance;
	private GameRenderer gameRenderer;

	private GuiContainer guiContainer;
	private Optional<World> world = Optional.empty();

	public StationAlpha() {
		if(instance != null)
			throw new GdxRuntimeException("Cannot create instance more than once!");

		instance = this;
	}

	@Override
	public void create () {
		gameRenderer = new GameRenderer();
		guiContainer = new GuiContainer();

		InputHandler.init();
		Blocks.init();
		Item.init();

		world = Optional.of(new World(true));

		logger.info("Asset Count: " + AssetManager.getInstance().getNumberOfAssets());
	}

	@Override
	public void render () {
		while(!AssetManager.getInstance().isDone()){
			return;
		}

		gameRenderer.render();
		guiContainer.render();
	}

	@Override
	public void resize(int width, int height) {
		gameRenderer.resize(width, height);
	}

	@Override
	public void dispose () {
		AssetManager.getInstance().dispose();
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
}
