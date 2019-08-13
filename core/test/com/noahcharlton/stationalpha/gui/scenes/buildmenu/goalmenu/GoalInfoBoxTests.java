package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalInfoBoxTests {

    private final GoalMenu goalMenu = new GoalMenu();
    private final GoalInfoBox infoBox = new GoalInfoBox(goalMenu);

    @Test
    void emptyGoalByDefault() {
        Assertions.assertFalse(infoBox.getSelectedGoal().isPresent());
    }

    @Test
    void infoBoxFollowsGoalMenuXTest() {
        goalMenu.setX(2512);

        infoBox.updatePosition();

        Assertions.assertEquals(2512, infoBox.getX());
    }

    @Test
    void infoBoxFollowsGoalMenuYTest() {
        goalMenu.setY(125);

        infoBox.updatePosition();

        Assertions.assertEquals(125, infoBox.getY());
    }

    @Test
    void infoBoxFollowsGoalMenuWidthTest() {
        goalMenu.setY(125);

        infoBox.updatePosition();

        Assertions.assertEquals(125, infoBox.getY());
    }
}
