package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.DebugBox;
import com.noahcharlton.stationalpha.gui.scenes.ItemList;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BlockMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildMenu;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.FloorMenu;

import java.util.Arrays;
import java.util.List;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;
    private final ItemList itemList;

    private final BuildMenu blockMenu = new BlockMenu();
    private final BuildMenu floorMenu = new FloorMenu();

    public GameGui() {
        List<BuildMenu> menus = Arrays.asList(blockMenu, floorMenu);

        debugBox = new DebugBox();
        buildBar = new BuildBar(menus);
        itemList = new ItemList();

        this.addAllGui(debugBox, buildBar, itemList);
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
