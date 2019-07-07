package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlockContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final BlockContainer blockContainer = new BlockContainer(tile, Blocks.getWall());

    @Test
    void combinedDebugInfo() {
        String[] expected = new String[]{"Floor: None", "Oxygen: 0.0", "Zebra", "Lion"};
        String[] actual = blockContainer.combineDebugInfo("Zebra", "Lion");

        Assertions.assertArrayEquals(expected, actual);
    }
}
