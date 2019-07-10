package com.noahcharlton.stationalpha.gui;

import com.noahcharlton.stationalpha.StationAlpha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GuiContainerTests {

    private static final GuiContainer guiContainer = new GuiContainer();

    @ParameterizedTest
    @MethodSource("sceneVisibilityProvider")
    void sceneVisibilityTest(StationAlpha.GameState gameState, GuiComponent guiComponent, boolean visible) {
        guiContainer.updateScenes(gameState);

        Assertions.assertEquals(visible, guiComponent.isVisible());
    }

    static Stream<Arguments> sceneVisibilityProvider() {
        return Stream.of(
                //Game Gui
                Arguments.of(StationAlpha.GameState.MAIN_MENU, guiContainer.getGameGui(), false),
                Arguments.of(StationAlpha.GameState.IN_GAME, guiContainer.getGameGui(), true),
                Arguments.of(StationAlpha.GameState.LOADING, guiContainer.getGameGui(), false),

                //Main Menu
                Arguments.of(StationAlpha.GameState.MAIN_MENU, guiContainer.getMainMenu(), true),
                Arguments.of(StationAlpha.GameState.IN_GAME, guiContainer.getMainMenu(), false),
                Arguments.of(StationAlpha.GameState.LOADING, guiContainer.getGameGui(), false),

                //Loading Screen
                Arguments.of(StationAlpha.GameState.MAIN_MENU, guiContainer.getLoadingScreen(), false),
                Arguments.of(StationAlpha.GameState.IN_GAME, guiContainer.getLoadingScreen(), false),
                Arguments.of(StationAlpha.GameState.LOADING, guiContainer.getLoadingScreen(), true)
        );
    }
}
