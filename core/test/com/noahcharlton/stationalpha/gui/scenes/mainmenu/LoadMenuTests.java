package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoadMenuTests {

    private final MainMenuButtonPane buttonPane = new MainMenuButtonPane();
    private final LoadMenu loadMenu = new LoadMenu(buttonPane);
    private final LoadErrorMenu errorMenu = new LoadErrorMenu(loadMenu);

    @Test
    void errorMenuSameYTest() {
        loadMenu.setY(25);

        errorMenu.updatePosition();

        Assertions.assertEquals(25, errorMenu.getY());
    }

    @Test
    void errorMenuXTest() {
        loadMenu.setX(331);
        loadMenu.setWidth(15);

        errorMenu.updatePosition();


        Assertions.assertEquals(346, errorMenu.getX());
    }

    @Test
    void errorMenuFollowsHeightTest() {
        loadMenu.setHeight(98);

        errorMenu.updatePosition();

        Assertions.assertEquals(98, loadMenu.getHeight());
    }

    @Test
    void nullReasonReplacedTest() {
        errorMenu.setReason(null);

        Assertions.assertNotNull(errorMenu.getReason());
    }
}
