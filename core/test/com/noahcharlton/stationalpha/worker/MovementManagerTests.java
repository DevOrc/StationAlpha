package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.worker.pathfinding.SimpleGraphPath;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovementManagerTests {

    private final Worker worker = Worker.create(new World());
    private final WorkerMovementManager manager = worker.getAi().getMovementManager();

    @BeforeEach
    void setUp() {
        worker.setPixelX(0);
        worker.setPixelY(0);
    }

    @Test
    void workerMoveRelativeXTest() {
        worker.setPixelX(100);
        manager.moveWorkerRelative(120, 0);

        Assertions.assertEquals(worker.getPixelX(), 220);
    }

    @Test
    void workerMoveRelativeYTest() {
        worker.setPixelY(32);
        manager.moveWorkerRelative(0, 40);

        Assertions.assertEquals(worker.getPixelY(), 72);
    }

    @Test
    void getTargetTileFromPathAlreadyOnTargetTest() {
        SimpleGraphPath path  = new SimpleGraphPath();
        Tile tile = new Tile(0, 0, new World());
        path.add(tile);

        Assertions.assertSame(tile, manager.getTargetTileFromPath(path));
    }

    @Test
    void getTargetTileFromPathAlreadyOnNextTest() {
        SimpleGraphPath path  = new SimpleGraphPath();
        Tile tile = new Tile(1, 0, new World());

        path.add(new Tile(0, 0, new World()));
        path.add(tile);

        Assertions.assertSame(tile, manager.getTargetTileFromPath(path));
    }

    @Test
    void getTargetTileFromPathBasicTest() {
        SimpleGraphPath path  = new SimpleGraphPath();
        Tile tile = new Tile(1, 0, new World());
        path.add(tile);

        Assertions.assertSame(tile, manager.getTargetTileFromPath(path));
    }

    @Test
    void moveToTileOriginBasicXTest() {
        worker.setPixelX(1);
        worker.setPixelY(0);

        manager.moveToTileOrigin();

        Assertions.assertEquals(0, worker.getPixelX());
    }

    @Test
    void moveToTileOriginBasicYTest() {
        worker.setPixelX(0);
        worker.setPixelY(1);

        manager.moveToTileOrigin();

        Assertions.assertEquals(0, worker.getPixelX());
    }

    @Test
    void moveToTileOriginOnOriginDoNotMoveTest() {
        manager.moveToTileOrigin();

        Assertions.assertEquals(0, worker.getPixelX());
    }
}
