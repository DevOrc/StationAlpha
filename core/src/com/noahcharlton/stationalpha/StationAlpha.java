package com.noahcharlton.stationalpha;

import com.badlogic.gdx.ApplicationAdapter;
import com.noahcharlton.stationalpha.engine.GameRenderer;

public class StationAlpha extends ApplicationAdapter {

	private GameRenderer gameRenderer;

	@Override
	public void create () {
		gameRenderer = new GameRenderer();
	}

	@Override
	public void render () {
		gameRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		gameRenderer.resize(width, height);
	}

	@Override
	public void dispose () {

	}
}
