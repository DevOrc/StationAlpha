package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalInfoBoxTests {

    private final ScienceMenu scienceMenu = new ScienceMenu();
    private final ScienceInfoBox infoBox = new ScienceInfoBox(scienceMenu);

    @Test
    void emptyGoalByDefault() {
        Assertions.assertFalse(infoBox.getSelectedGoal().isPresent());
    }

    @Test
    void infoBoxFollowsGoalMenuXTest() {
        scienceMenu.setX(2512);

        infoBox.updatePosition();

        Assertions.assertEquals(2512, infoBox.getX());
    }

    @Test
    void infoBoxFollowsGoalMenuYTest() {
        scienceMenu.setY(125);

        infoBox.updatePosition();

        Assertions.assertEquals(125, infoBox.getY());
    }

    @Test
    void infoBoxFollowsGoalMenuWidthTest() {
        scienceMenu.setY(125);

        infoBox.updatePosition();

        Assertions.assertEquals(125, infoBox.getY());
    }
}
