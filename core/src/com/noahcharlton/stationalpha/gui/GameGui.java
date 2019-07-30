package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.scenes.*;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.*;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu.BlockMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu.WorkerMenu;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageMenu;
import com.noahcharlton.stationalpha.gui.scenes.selectable.SelectableBox;

import java.util.Arrays;
import java.util.List;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;
    private final ItemList itemList;
    private final SpeedButton speedButton;
    private final MessageMenu messageMenu;
    private final SelectableBox selectableBox;

    private final BuildBarMenu blockMenu = new BlockMenu();
    private final BuildBarMenu floorMenu = new FloorMenu();
    private final BuildBarMenu goalMenu = new GoalMenu();
    private final BuildBarMenu manufactureMenu = new ManufacturingMenu();
    private final BuildBarMenu workerMenu = new WorkerMenu();
    private final BuildBarMenu actionsMenu = new ActionsMenu();
    private final BuildBarMenu quitMenu = new QuitMenu();

    public GameGui() {
        List<BuildBarMenu> menus = Arrays.asList(blockMenu, floorMenu, actionsMenu, manufactureMenu,
                workerMenu, goalMenu, quitMenu);

        debugBox = new DebugBox();
        buildBar = new BuildBar(menus);
        itemList = new ItemList();
        speedButton = new SpeedButton();
        messageMenu = new MessageMenu();
        selectableBox = new SelectableBox();

        this.addAllGui(debugBox, buildBar, itemList, speedButton, selectableBox, messageMenu);
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
        selectableBox.setVisible(InputHandler.getInstance().getCurrentlySelected().isPresent());
        debugBox.setVisible(DebugKeys.isDebugPressed(DebugKeys.DEBUG_BOX));
    }

    SelectableBox getSelectableBox() {
        return selectableBox;
    }
}
