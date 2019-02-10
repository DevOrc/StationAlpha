package com.noahcharlton.stationalpha.worker.pathfinding;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathTests {

    private final WorldGraph worldGraph = new WorldGraph(new World());

    @Test
    void differentWorldsPathConstructorFailsTest() {
        Tile tileOne = new Tile(0, 0, new World());
        Tile tileTwo = new Tile(0, 0, new World());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           new Path(worldGraph, tileOne, tileTwo);
        });
    }

    @Test
    void simpleHeuristicEstimateBasicTest() {
        Tile src = new Tile(0, 0, new World());
        Tile dest = new Tile(3, 4, new World());

        Assertions.assertEquals(5, new SimpleHeuristic().estimate(src, dest));
    }
}
