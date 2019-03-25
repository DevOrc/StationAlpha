package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.goal.Goal;
import com.noahcharlton.stationalpha.world.World;

import java.util.Collections;

public class GoalMenu extends BuildBarMenu {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 250;

    public GoalMenu() {
        super(Collections.emptyList());
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        World.getInstance().get().getGoalManager().getCurrentGoal().ifPresent(goal -> drawGoalData(b, goal));
    }

    private void drawGoalData(SpriteBatch b, Goal goal) {
        setFontData(1f, Color.WHITE);
        drawCenteredText(b, goal.getName(), HEIGHT * 9 / 10);

        setFontData(.6f, Color.WHITE);
        drawCenteredText(b, goal.getDescription(), HEIGHT * 6 / 10);
    }

    @Override
    protected void updateSize() {
        setHeight(HEIGHT);
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
