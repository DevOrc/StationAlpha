package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.goal.Goal;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;

import java.util.Collections;

public class GoalMenu extends BuildBarMenu<Void> {

    private static final int WINDOW_OFFSET = 50;
    private static final int BOX_WIDTH = 250;
    private static final int BOX_HEIGHT = 50;

    private final GoalTabPane tabPane = new GoalTabPane(this);
    private final GoalInfoBox infoBox = new GoalInfoBox(this);

    public GoalMenu() {
        super(Collections.emptyList());

        addAllGui(tabPane, infoBox);
        setDrawBorder(true, true, true, true);
    }

    @Override
    protected Runnable createRunnable(Void item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        for(Goal goal : tabPane.getSelected().getGoals()) {
            renderGoal(goal, b);
        }
    }

    private void renderGoal(Goal goal, SpriteBatch b) {
        int x = goal.getX() + getX();
        int y = goal.getY() + getY() + GoalInfoBox.HEIGHT;

        Color borderColor = goal.isCompleted() ? Color.GREEN : Color.FIREBRICK;
        ShapeUtil.drawRect(x, y, BOX_WIDTH, BOX_HEIGHT, borderColor, b);
        ShapeUtil.drawRect(x + 2, y + 2, BOX_WIDTH - 4, BOX_HEIGHT - 4, Color.BLACK, b);

        setFontData(.6f, goal.allRequirementsCompleted() ? Color.WHITE: Color.GRAY);
        int fontPadding = 5;
        font.draw(b, goal.getName(), x + fontPadding, y + 30,
                BOX_WIDTH - (fontPadding * 2), Align.center, false);

        drawRequirements(goal, b);
    }

    private void drawRequirements(Goal goal, SpriteBatch b) {
        int x2 = goal.getX() + getX();
        int y2 = goal.getY() + getY() + GoalInfoBox.HEIGHT +  (BOX_HEIGHT / 2);

        for(Goal requirement: goal.getRequirements()){
            int x1 = requirement.getX() + getX() + BOX_WIDTH;
            int y1 = requirement.getY() +  getY() + GoalInfoBox.HEIGHT + (BOX_HEIGHT / 2);

            ShapeUtil.drawLine(x1, y1, x2, y2, Color.GOLDENROD, b);
        }
    }

    @Override
    protected void onClick() {
        int clickX = Gdx.input.getX() - getX();
        int clickY = Gdx.graphics.getHeight() - Gdx.input.getY() - getY() - GoalInfoBox.HEIGHT;

        for(Goal goal : tabPane.getSelected().getGoals()) {
            if(clickX > goal.getX() && clickY > goal.getY() && clickX < goal.getX() + BOX_WIDTH &&
                    clickY < goal.getY() + BOX_HEIGHT){
                infoBox.setSelectedGoal(goal);
                return;
            }
        }

        infoBox.setSelectedGoal(null);
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
