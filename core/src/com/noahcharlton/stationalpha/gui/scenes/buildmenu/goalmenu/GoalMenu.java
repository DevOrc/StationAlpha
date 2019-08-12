package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;
import com.noahcharlton.stationalpha.world.World;

import java.util.Collections;

public class GoalMenu extends BuildBarMenu<Void> {

    private static final int WINDOW_OFFSET = 50;

    private final GoalTabPane tabPane = new GoalTabPane(this);

    public GoalMenu() {
        super(Collections.emptyList());

        addGui(tabPane);
        setDrawBorder(true, true, true, true);
    }

    @Override
    protected Runnable createRunnable(Void item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, GuiComponent.ACCENT_COLOR);
        font.draw(b, tabPane.getSelected().getDisplayName(), getX() + 20, getY() + 20);
    }

    @Override
    public String getName() {
        return "Goals";
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

    private World getWorld(){
        return World.getInstance().orElseThrow(() -> new GdxRuntimeException("World not available!"));
    }
}
