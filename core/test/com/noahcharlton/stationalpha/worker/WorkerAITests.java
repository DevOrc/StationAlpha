package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerAITests {

    private final TestWorker worker = new TestWorker();
    private final WorkerAI ai = worker.getAi();

    @Test
    void onTargetTileBasicTest() {
        ai.setTargetTile(new Tile(1, 1, new World()));
        worker.setPixelX(50);
        worker.setPixelY(50);

        Assertions.assertTrue(ai.onTargetTile());
    }

    @Test
    void onTargetTileFalseTest() {
        ai.setTargetTile(new Tile(3, 1, new World()));
        worker.setPixelX(50);
        worker.setPixelY(50);

        Assertions.assertFalse(ai.onTargetTile());
    }

    @Test
    void onTargetTileNullTrueAlwaysTest() {
        ai.setTargetTile(null);

        Assertions.assertTrue(ai.onTargetTile());
    }
}
