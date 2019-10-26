package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public final class EmptyComponent extends GuiComponent {

    @Override
    protected void drawBackground(SpriteBatch batch) {

    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }

    @Override
    public void addGui(GuiComponent gui) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAllGui(GuiComponent... comps) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }
}
