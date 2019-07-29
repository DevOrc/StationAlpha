package com.noahcharlton.stationalpha;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.gui.scenes.message.Message;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class StationAlphaTests extends LibGdxTest{

    private StationAlpha gameInstance = new StationAlpha(false);

    @Test
    void worldEmptyByDefaultTest() {
        Assertions.assertEquals(Optional.empty(), gameInstance.getWorld());
    }

    @Test
    void onStartWorldCreated() {
        gameInstance.startGame();

        Assertions.assertTrue(gameInstance.getWorld().isPresent());
    }

    @Test
    void onStartGameStateChangesToInGame() {
        gameInstance.startGame();

        Assertions.assertEquals(StationAlpha.GameState.IN_GAME, gameInstance.getCurrentState());
    }

    @Test
    void cannotCreateInstanceMoreThanOnceTest() {
        Assertions.assertThrows(GdxRuntimeException.class, () -> new StationAlpha(true));
    }

    @Test
    void gotoMainMenuEmptyWorldTestTest() {
        gameInstance.startGame();

        Assumptions.assumeTrue(gameInstance.getWorld().isPresent());
        gameInstance.gotoMainMenu();

        Assertions.assertFalse(gameInstance.getWorld().isPresent());
    }

    @Test
    void gotoMainMenuSetsStateTest() {
        Assumptions.assumeTrue(gameInstance.getCurrentState() == StationAlpha.GameState.LOADING);
        gameInstance.gotoMainMenu();

        Assertions.assertEquals(StationAlpha.GameState.MAIN_MENU, gameInstance.getCurrentState());
    }

    @Test
    void startGameClearsQueueTest() {
        MessageQueue.getInstance().add("", "");
        MessageQueue.getInstance().add("", "");
        MessageQueue.getInstance().add("", "");
        MessageQueue.getInstance().add("", "");

        gameInstance.startGame();

        Assertions.assertEquals(1, MessageQueue.getInstance().getMessages().size());
    }

    @Test
    void hintMessageAddedOnStart() {
        gameInstance.startGame();

        Message m = MessageQueue.getInstance().getMessages().getFirst();

        Assertions.assertEquals(HelpInfo.START_INFO, m.getDescription());
    }
}
