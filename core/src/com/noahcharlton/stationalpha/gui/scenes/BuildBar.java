package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuildBar extends Pane {

    private static final Logger logger = LogManager.getLogger(BuildBar.class);

    private final MenuButton iceButton;
    private final MenuButton wallButton;
    private final MenuButton doorButton;
    private final MenuButton quitButton;

    public BuildBar() {
        iceButton = new MenuButton("Ice", this::onIceButtonClick);
        wallButton = new MenuButton("Wall", this::onWallButtonClick);
        doorButton = new MenuButton("Door", this::onDoorButtonClick);
        quitButton = new MenuButton("Quit", this::onQuitButtonClick);

        iceButton.setY(10);
        wallButton.setY(10);
        quitButton.setY(10);
        doorButton.setY(10);

        addAllGui(iceButton, wallButton, doorButton, quitButton);
        addGui(iceButton);
        setDrawBorder(true, true, false, false);
    }

    @Override
    protected void updatePosition() {
        this.setX(0);
        this.setY(0);
    }

    @Override
    protected void updateSize() {
        this.setHeight(MenuButton.HEIGHT + 20);
        this.setWidth(MenuButton.WIDTH * 3 + 50);

        layoutButtons();
    }

    private void layoutButtons() {
        quitButton.setX(10);
        wallButton.setX(20 + MenuButton.WIDTH);
        iceButton.setX(30 + (MenuButton.WIDTH * 2));
        doorButton.setX(40 + (MenuButton.WIDTH * 3));
    }

    void onQuitButtonClick() {
        logger.info("Exit button clicked!");
        Gdx.app.exit();
    }

    void onWallButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getWall());

        InputHandler.getInstance().setBuildAction(blockAction);
    }

    void onIceButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getIce());

        InputHandler.getInstance().setBuildAction(blockAction);
    }

    private void onDoorButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getDoor());

        InputHandler.getInstance().setBuildAction(blockAction);
    }
}
