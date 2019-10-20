package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public class IconButton extends ButtonBase{

    public static final int SIZE = 40;
    private static final int ICON_OFFSET = 2 + Pane.BORDER_WIDTH;
    private final InGameIcon icon;

    public IconButton(InGameIcon icon, Runnable onClick) {
        super(onClick);

        this.icon = icon;
        this.setBorderColor(GuiComponent.ACCENT_COLOR);
        this.setDrawBorder(true, true, true, true);
        this.setBackgroundColor(Color.DARK_GRAY);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        b.draw(icon.getTexture().get(), getX() + ICON_OFFSET, getY() + ICON_OFFSET);
    }

    @Override
    protected void updatePosition() {}

    @Override
    protected void updateSize() {
        setWidth(SIZE);
        setHeight(SIZE);
    }
}
