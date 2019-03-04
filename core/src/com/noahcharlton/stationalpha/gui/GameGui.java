package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.DebugBox;
import com.noahcharlton.stationalpha.gui.scenes.ItemList;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;
    private final ItemList itemList;

    public GameGui() {
        debugBox = new DebugBox();
        buildBar = new BuildBar();
        itemList = new ItemList();

        this.addAllGui(debugBox, buildBar, itemList);
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
