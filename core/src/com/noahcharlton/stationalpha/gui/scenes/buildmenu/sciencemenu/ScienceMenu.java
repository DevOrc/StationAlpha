package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildMenu;
import com.noahcharlton.stationalpha.world.World;

public class ScienceMenu extends Pane implements BuildMenu {

    private static final int WINDOW_OFFSET = 50;
    private static final int BOX_WIDTH = 250;
    private static final int BOX_HEIGHT = 50;

    private final ResearchItemInfoBox infoBox = new ResearchItemInfoBox(this);

    public ScienceMenu() {
        addAllGui(infoBox);
        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        String text = "Science: " + World.getInstance().get().getScienceManager().getSciencePoints();
        setFontData(.65f, Color.WHITE);
        font.draw(b, text, getX() + 10, getY() + getHeight() - 20);
    }

    @Override
    public GuiComponent getComponent() {
        return this;
    }

    @Override
    public String getName() {
        return "Science";
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.SCIENCE;
    }

    @Override
    protected void updatePosition() {
        if(Gdx.graphics == null)
            return;

        setX((Gdx.graphics.getWidth() / 2) - (getWidth() / 2));
        setY((Gdx.graphics.getHeight() / 2) - (getHeight() / 2) + (BuildBar.HEIGHT / 2));
    }

    @Override
    protected void updateSize() {
        if(Gdx.graphics == null)
            return;

        setWidth(Gdx.graphics.getWidth() - (WINDOW_OFFSET * 2));

        int height = Gdx.graphics.getHeight() - BuildBar.HEIGHT - (WINDOW_OFFSET * 2);
        setHeight(height);
    }
}
