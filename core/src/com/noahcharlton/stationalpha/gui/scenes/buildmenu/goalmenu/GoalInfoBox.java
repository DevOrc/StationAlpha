package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.goal.Goal;
import com.noahcharlton.stationalpha.gui.components.Pane;

import java.util.Optional;

public class GoalInfoBox extends Pane {

    static final int HEIGHT = 150;

    private final GoalMenu goalMenu;
    private Optional<Goal> selectedGoal;

    public GoalInfoBox(GoalMenu goalMenu) {
        this.goalMenu = goalMenu;

        setSelectedGoal(null);
        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        selectedGoal.ifPresent(goal -> {
            setFontData(.75f, Color.WHITE);
            drawCenteredText(b, goal.getName(), getHeight() - 10);

            setFontData(.55f, Color.WHITE);
            drawCenteredText(b, goal.getDesc(), getHeight() - 45);
        });
    }

    @Override
    protected void updateSize() {
        setWidth(goalMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(goalMenu.getX());
        setY(goalMenu.getY());
    }

    public void setSelectedGoal(Goal selectedGoal) {
        this.selectedGoal = Optional.ofNullable(selectedGoal);
        this.setVisible(selectedGoal != null);
    }

    public Optional<Goal> getSelectedGoal() {
        return selectedGoal;
    }
}
