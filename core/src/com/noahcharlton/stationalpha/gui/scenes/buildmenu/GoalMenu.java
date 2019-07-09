package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.goal.Goal;
import com.noahcharlton.stationalpha.world.World;

import java.util.Collections;

public class GoalMenu extends BuildBarMenu {

    private static final int SPACING = 15;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 400;

    private int height = HEIGHT;

    public GoalMenu() {
        super(Collections.emptyList());
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        drawGoalData(b, World.getInstance().get().getGoalManager().getCurrentGoal());
    }

    private void drawGoalData(SpriteBatch b, Goal goal) {
        int y = SPACING;

        setFontData(1f, Color.WHITE);
        y += drawCenteredText(b, goal.getName(), getHeight() - y).height;
        y += SPACING;

        setFontData(.6f, Color.WHITE);
        y += drawCenteredText(b, goal.getDescription(),getHeight() -y ).height;
        y += SPACING * 2;

        height = y;
    }

    @Override
    protected void updateSize() {
        setHeight(height);
        setWidth(WIDTH);
    }

    @Override
    public String getName() {
        return "Goals";
    }

    @Override
    protected Runnable createRunnable(Object item) {
        return null;
    }
}
