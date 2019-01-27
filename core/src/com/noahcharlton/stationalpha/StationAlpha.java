package com.noahcharlton.stationalpha;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.GameRenderer;
import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class StationAlpha extends ApplicationAdapter {

	private static final Logger logger = LogManager.getLogger(StationAlpha.class);
	private static StationAlpha instance;
	private GameRenderer gameRenderer;

	private Optional<World> world = Optional.empty();
	private long lastFPSTime;

	public StationAlpha() {
		if(instance != null)
			throw new GdxRuntimeException("Cannot create instance more than once!");

		instance = this;
	}

	@Override
	public void create () {
		gameRenderer = new GameRenderer();

		Blocks.init();
		world = Optional.of(new World());

		logger.info("Asset Count: " + AssetManager.getInstance().getNumberOfAssets());
	}

	@Override
	public void render () {
		while(!AssetManager.getInstance().isDone()){
			return;
		}

		gameRenderer.render();

		updateFPS();
	}

	private void updateFPS() {
		if(lastFPSTime + 1000 < System.currentTimeMillis()){
			lastFPSTime = System.currentTimeMillis();

			logger.debug("FPS: " + Gdx.graphics.getFramesPerSecond());
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

	public Optional<World> getWorld() {
		return world;
	}

	public static StationAlpha getInstance() {
		return instance;
	}
}
