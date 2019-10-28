package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.noahcharlton.stationalpha.science.GoalTab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalTabPaneTests {

    private final ScienceMenu scienceMenu = new ScienceMenu();
    private final SienceTabPane tabMenu = new SienceTabPane(scienceMenu);

    @Test
    void selectedDefaultsToTechTest() {
        Assertions.assertEquals(GoalTab.TECH, tabMenu.getSelected());
    }

    @Test
    void tabMenuFollowsWidthTest() {
        scienceMenu.setWidth(125);

        tabMenu.updateSize();

        Assertions.assertEquals(125, tabMenu.getWidth());
    }

    @Test
    void tabMenuFollowsXTest() {
        scienceMenu.setX(643);
        tabMenu.updatePosition();

        Assertions.assertEquals(643, tabMenu.getX());
    }
}
