package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.BuildFloor;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.components.ComponentGroup;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.layout.HStretchLayout;
import com.noahcharlton.stationalpha.world.Floor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuildBar extends ComponentGroup {

    private static final Logger logger = LogManager.getLogger(BuildBar.class);

    private final HStretchLayout layoutManager;

    private final MenuButton woodFloorButton;
    private final MenuButton metalFloorButton;
    private final MenuButton wallButton;
    private final MenuButton doorButton;
    private final MenuButton quitButton;
    private final MenuButton compressorButton;

    public BuildBar() {
        woodFloorButton = new MenuButton("Wood", this::onWoodButtonClick);
        metalFloorButton = new MenuButton("Metal", this::onMetalButtonClick);
        wallButton = new MenuButton("Wall", this::onWallButtonClick);
        doorButton = new MenuButton("Door", this::onDoorButtonClick);
        compressorButton = new MenuButton("Compressor", this::onCompressorButtonClick);
        quitButton = new MenuButton("Quit", this::onQuitButtonClick);
        layoutManager = new HStretchLayout();
        layoutManager.setHGap(8);
        layoutManager.setPadding(5);

        setLayoutManager(layoutManager);
        addAllGui(woodFloorButton, metalFloorButton, wallButton, doorButton, compressorButton, quitButton);
        setDrawBorder(true, true, false, false);
    }

    @Override
    protected void updatePosition() {
        this.setX(0);
        this.setY(0);
    }

    @Override
    protected void updateSize() {
        this.setHeight(65);
        this.setWidth(Gdx.graphics.getWidth());
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

    private void onCompressorButtonClick() {
        BuildBlock blockAction = new BuildBlock(Blocks.getCompressor());

        InputHandler.getInstance().setBuildAction(blockAction);
    }
}
