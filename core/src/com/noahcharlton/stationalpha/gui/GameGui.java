package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.DebugBox;

public class GameGui extends GuiComponent {

    private final DebugBox debugBox;
    private final BuildBar buildBar;

    public GameGui() {
        debugBox = new DebugBox();
        buildBar = new BuildBar();

        this.addAllGui(debugBox, buildBar);
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {

    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }

    @Override
    protected void update() {
        debugBox.setVisible(Gdx.input.isKeyPressed(Input.Keys.NUM_2));
    }
}
