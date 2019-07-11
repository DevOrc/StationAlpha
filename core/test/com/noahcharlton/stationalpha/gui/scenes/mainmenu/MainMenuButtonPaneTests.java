package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainMenuButtonPaneTests {

    private final MainMenuButtonPane buttonPane = new MainMenuButtonPane();

    private final MainMenuSubMenu settingsMenu = buttonPane.getSettingsMenu();
    private final MainMenuSubMenu aboutMenu = buttonPane.getAboutMenu();

    @Test
    void openMenuSetsVisibleTest() {
        buttonPane.clickOnMenu(settingsMenu);

        Assertions.assertTrue(settingsMenu.isVisible());
    }

    @Test
    void openMenuClosesMenuIfAlreadyOpenTest() {
        aboutMenu.setVisible(true);
        buttonPane.clickOnMenu(aboutMenu);

        Assertions.assertFalse(aboutMenu.isVisible());
    }

    @Test
    void openMenuClosesOtherMenusTest() {
        aboutMenu.setVisible(true);
        buttonPane.clickOnMenu(settingsMenu);

        Assertions.assertFalse(aboutMenu.isVisible());
    }
}