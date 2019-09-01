package com.noahcharlton.stationalpha;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.gui.scenes.message.Message;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.worker.job.TestJob;
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
    void onLoadGameStateChangeToInGame() {
        gameInstance.loadGame(0);

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

        Assertions.assertEquals(2, MessageQueue.getInstance().getMessages().size());
    }

    @Test
    void hintMessageAddedOnStart() {
        gameInstance.startGame();

        Message m = MessageQueue.getInstance().getMessages().getFirst();

        Assertions.assertEquals(HelpInfo.get("start_message"), m.getDescription());
    }

    @Test
    void controlMessageOnStart() {
        gameInstance.startGame();

        MessageQueue.getInstance().getMessages().poll();
        Message m = MessageQueue.getInstance().getMessages().poll();

        Assertions.assertEquals(HelpInfo.get("controls_message"), m.getDescription());
    }

    @Test
    void jobQueueClearedOnWorldStartTest() {
        JobQueue.getInstance().addJob(new TestJob());

        gameInstance.startGame();

        Assertions.assertEquals(0, JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).size());
    }

    @Test
    void jobQueueClearedOnWorldLoadedTest() {
        JobQueue.getInstance().addJob(new TestJob());

        gameInstance.loadGame(11);

        Assertions.assertEquals(0, JobQueue.getInstance().getJobQueue(WorkerRole.GENERAL).size());
    }
}
