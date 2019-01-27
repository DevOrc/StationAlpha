package com.noahcharlton.stationalpha.world;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorldTests {

    private final World world = new World(false);

    @Test
    void getTileAtBasicTest() {
        Assertions.assertEquals(new Tile(0, 0), world.getTileAt(0, 0).get());
    }

    @Test
    void getTileOutsideBorrow() {
        Assertions.assertFalse(world.getTileAt(1000, 1000).isPresent());
    }
}
