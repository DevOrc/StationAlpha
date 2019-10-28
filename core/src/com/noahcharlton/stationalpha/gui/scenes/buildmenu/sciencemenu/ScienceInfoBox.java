package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.science.Goal;
import com.noahcharlton.stationalpha.gui.components.Pane;

import java.util.Optional;

public class ScienceInfoBox extends Pane {

    static final int HEIGHT = 200;

    private final ScienceMenu scienceMenu;
    private Optional<Goal> selectedGoal;

    public ScienceInfoBox(ScienceMenu scienceMenu) {
        this.scienceMenu = scienceMenu;

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
        setWidth(scienceMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(scienceMenu.getX());
        setY(scienceMenu.getY());
    }

    public void setSelectedGoal(Goal selectedGoal) {
        this.selectedGoal = Optional.ofNullable(selectedGoal);
        this.setVisible(selectedGoal != null);
    }

    public Optional<Goal> getSelectedGoal() {
        return selectedGoal;
    }
}
