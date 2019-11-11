package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResearchItemInfoBoxTests {

    private final ScienceMenu scienceMenu = new ScienceMenu();
    private final ResearchItemInfoBox infoBox = new ResearchItemInfoBox(scienceMenu);

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
