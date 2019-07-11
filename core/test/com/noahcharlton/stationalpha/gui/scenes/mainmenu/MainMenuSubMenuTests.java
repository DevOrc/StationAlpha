package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainMenuSubMenuTests {

    private static class TestSubMenu extends MainMenuSubMenu{

        public TestSubMenu(MainMenuButtonPane buttonPane) {
            super(buttonPane);
        }
    }

    private final MainMenuButtonPane buttonPane = new MainMenuButtonPane();
    private final TestSubMenu subMenu = new TestSubMenu(buttonPane);

    @Test
    void menuInvisibleOnStart() {
        Assertions.assertFalse(subMenu.isVisible());
    }

    @Test
    void menuIsSameYAsButtonPane() {
        buttonPane.setY(253);
        subMenu.updatePosition();

        Assertions.assertEquals(253, subMenu.getY());
    }

    @Test
    void menuIsSameHeightAsButtonPane() {
        buttonPane.setHeight(65);
        subMenu.updateSize();

        Assertions.assertEquals(65, subMenu.getHeight());
    }
}