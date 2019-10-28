package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.DebugBox;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.*;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu.BlockMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu.ScienceMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu.WorkerMenu;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageMenu;
import com.noahcharlton.stationalpha.gui.scenes.selectable.SelectableBox;

import java.util.Arrays;
import java.util.List;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;
    private final SpeedButton speedButton;
    private final MessageMenu messageMenu;
    private final SelectableBox selectableBox;

    private final BuildMenu blockMenu = new BlockMenu();
    private final BuildMenu floorMenu = new FloorMenu();
    private final BuildMenu goalMenu = new ScienceMenu();
    private final BuildMenu manufactureMenu = new ManufacturingMenu();
    private final BuildMenu workerMenu = new WorkerMenu();
    private final BuildMenu actionsMenu = new ActionsMenu();
    private final BuildMenu inventoryMenu = new InventoryMenu();
    private final BuildMenu quitMenu = new QuitMenu();

    public GameGui() {
        List<BuildMenu> menus = Arrays.asList(blockMenu, floorMenu, actionsMenu, manufactureMenu,
                workerMenu, goalMenu, inventoryMenu, quitMenu);

        debugBox = new DebugBox();
        buildBar = new BuildBar(menus);
        speedButton = new SpeedButton();
        messageMenu = new MessageMenu();
        selectableBox = new SelectableBox();

        this.addAllGui(debugBox, buildBar, speedButton, selectableBox, messageMenu);
        menus.stream().map(BuildMenu::getComponent).forEach(comp -> {
            this.addGui(comp);
            comp.setVisible(false);
        });
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
