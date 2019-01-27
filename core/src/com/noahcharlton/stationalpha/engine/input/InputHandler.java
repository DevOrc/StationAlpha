package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class InputHandler implements SimpleInputProcessor {

    private static final InputHandler instance = new InputHandler();

    private static final Logger logger = LogManager.getLogger(InputHandler.class);

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer(this);
    private final BuildManager buildManager = new BuildManager();

    public static void init(){
        Objects.requireNonNull(getInstance(), "Instance cannot be null!");
        Gdx.input.setInputProcessor(getInstance().getInputMultiplexer());

        logger.debug("Loaded Input Handler!");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int graphicsY = Gdx.graphics.getHeight() - screenY;

        logger.info("Click Event[x = {}, y = {}, button = {}]", screenX, graphicsY, button);

        if(World.getInstance().isPresent()) {
            buildManager.handleGameClick(screenX, screenY, button);
        }

        return false;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static InputHandler getInstance() {
        return instance;
    }
}
