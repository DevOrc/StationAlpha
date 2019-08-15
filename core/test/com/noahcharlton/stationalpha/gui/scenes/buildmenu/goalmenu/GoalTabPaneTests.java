package com.noahcharlton.stationalpha.gui.scenes.buildmenu.goalmenu;

import com.noahcharlton.stationalpha.goal.GoalTab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalTabPaneTests {

    private final GoalMenu goalMenu = new GoalMenu();
    private final GoalTabPane tabMenu = new GoalTabPane(goalMenu);

    @Test
    void selectedDefaultsToTechTest() {
        Assertions.assertEquals(GoalTab.TECH, tabMenu.getSelected());
    }

    @Test
    void tabMenuFollowsWidthTest() {
        goalMenu.setWidth(125);

        tabMenu.updateSize();

        Assertions.assertEquals(125, tabMenu.getWidth());
    }

    @Test
    void tabMenuFollowsXTest() {
        goalMenu.setX(643);
        tabMenu.updatePosition();

        Assertions.assertEquals(643, tabMenu.getX());
    }
}
