package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.BuildFloor;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.world.Floor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuildBar extends Pane {

    private static final Logger logger = LogManager.getLogger(BuildBar.class);

    private final MenuButton woodFloorButton;
    private final MenuButton metalFloorButton;
    private final MenuButton wallButton;
    private final MenuButton doorButton;
    private final MenuButton quitButton;

    public BuildBar() {
        woodFloorButton = new MenuButton("Wood", this::onWoodButtonClick);
        metalFloorButton = new MenuButton("Metal", this::onMetalButtonClick);
        wallButton = new MenuButton("Wall", this::onWallButtonClick);
        doorButton = new MenuButton("Door", this::onDoorButtonClick);
        quitButton = new MenuButton("Quit", this::onQuitButtonClick);

        woodFloorButton.setY(10);
        metalFloorButton.setY(10);
        wallButton.setY(10);
        quitButton.setY(10);
        doorButton.setY(10);

        addAllGui(woodFloorButton, metalFloorButton, wallButton, doorButton, quitButton);
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
        this.setWidth(MenuButton.WIDTH * 4 + 50);

        layoutButtons();
    }

    private void layoutButtons() {
        quitButton.setX(10);
        wallButton.setX(20 + MenuButton.WIDTH);
        doorButton.setX(30 + (MenuButton.WIDTH * 2));
        woodFloorButton.setX(40 + (MenuButton.WIDTH * 3));
        metalFloorButton.setX(50 + (MenuButton.WIDTH * 4));
    }

    void onQuitButtonClick() {
        logger.info("Exit button clicked!");
        Gdx.app.exit();
    }

    void onWallButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getWall());

        InputHandler.getInstance().setBuildAction(blockAction);
    }

    void onWoodButtonClick() {
        BuildFloor action = new BuildFloor(Floor.WOOD);

        InputHandler.getInstance().setBuildAction(action);
    }

    void onMetalButtonClick() {
        BuildFloor action = new BuildFloor(Floor.METAL);

        InputHandler.getInstance().setBuildAction(action);
    }

    private void onDoorButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getDoor());

        InputHandler.getInstance().setBuildAction(blockAction);
    }
}
