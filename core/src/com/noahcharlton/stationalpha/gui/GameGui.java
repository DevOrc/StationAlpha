package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.DebugBox;
import com.noahcharlton.stationalpha.gui.scenes.ItemList;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.*;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu.WorkerMenu;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageMenu;

import java.util.Arrays;
import java.util.List;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;
    private final ItemList itemList;
    private final SpeedButton speedButton;
    private final MessageMenu messageMenu;

    private final BuildBarMenu blockMenu = new BlockMenu();
    private final BuildBarMenu floorMenu = new FloorMenu();
    private final BuildBarMenu goalMenu = new GoalMenu();
    private final BuildBarMenu manufactureMenu = new ManufacturingMenu();
    private final BuildBarMenu workerMenu = new WorkerMenu();
    private final BuildBarMenu actionsMenu = new ActionsMenu();

    public GameGui() {
        List<BuildBarMenu> menus = Arrays.asList(blockMenu, floorMenu, workerMenu, goalMenu,
                manufactureMenu, actionsMenu);

        debugBox = new DebugBox();
        buildBar = new BuildBar(menus);
        itemList = new ItemList();
        speedButton = new SpeedButton();
        messageMenu = new MessageMenu();

        this.addAllGui(debugBox, buildBar, itemList, speedButton, messageMenu);
        menus.forEach(this::addGui);
        menus.forEach(m -> m.setVisible(false));
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {

    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }

    @Override
    protected void update() {
        debugBox.setVisible(DebugKeys.isDebugPressed(DebugKeys.DEBUG_BOX));
    }
}
