package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.components.layout.HStretchLayout;
import com.noahcharlton.stationalpha.gui.components.layout.LayoutManager;

public abstract class ComponentGroup extends Pane{

    private LayoutManager layoutManager;

    public ComponentGroup() {
        this.layoutManager = new HStretchLayout();
    }

    @Override
    public void drawForeground(SpriteBatch b) {

    }

    @Override
    public void drawBackground(SpriteBatch b) {
        super.drawBackground(b);

        layoutManager.layout(this, getSubGuis());
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
}
