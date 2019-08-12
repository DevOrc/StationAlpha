package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;

import java.util.Collections;

public class GoalMenu extends BuildBarMenu<Void> {

    private static final int WINDOW_OFFSET = 50;

    public GoalMenu() {
        super(Collections.emptyList());

        setDrawBorder(true, true, true, true);
    }

    @Override
    protected Runnable createRunnable(Void item) {
        return null;
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
}
