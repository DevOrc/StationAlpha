package com.noahcharlton.stationalpha.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.scenes.DebugBar;

public class GameGui extends GuiComponent {

    private final DebugBar bar;

    public GameGui() {
        bar = new DebugBar();

        this.addGui(bar);
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {

    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }

    @Override
    protected void update() {
        bar.setVisible(Gdx.input.isKeyPressed(Input.Keys.NUM_2));
    }
}
