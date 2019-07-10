package com.noahcharlton.stationalpha;

import com.badlogic.gdx.utils.GdxRuntimeException;
import org.junit.jupiter.api.Assertions;
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
}
