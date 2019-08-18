package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WallBlockTests {

    private World world = new World();

    @BeforeAll
    static void initBlocks() {
        Blocks.init();
    }

    @Test
    void shouldConnectNorthBasicTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());
        world.getTileAt(1, 2).get().setBlock(Blocks.getWall());

        Assertions.assertTrue(WallBlock.shouldConnectNorth(tile, world));
    }

    @Test
    void shouldConnectNorthFalseTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());

        Assertions.assertFalse(WallBlock.shouldConnectNorth(tile, world));
    }

    @Test
    void shouldConnectEastBasicTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());

        Assertions.assertTrue(WallBlock.shouldConnectEast(tile, world));
    }

    @Test
    void shouldConnectEastFalseTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());

        Assertions.assertFalse(WallBlock.shouldConnectEast(tile, world));
    }

    @Test
    void shouldConnectWestBasicTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());
        world.getTileAt(2, 1).get().setBlock(Blocks.getWall());

        Assertions.assertTrue(WallBlock.shouldConnectWest(tile, world));
    }

    @Test
    void shouldConnectWestFalseTest() {
        Tile tile = world.getTileAt(2, 1).get();
        tile.setBlock(Blocks.getWall());

        Assertions.assertFalse(WallBlock.shouldConnectWest(tile, world));
    }

    @Test
    void shouldConnectSouthBasicTest() {
        Tile tile = world.getTileAt(1, 1).get();
        tile.setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertTrue(WallBlock.shouldConnectSouth(tile, world));
    }

    @Test
    void shouldConnectSouthFalseTest() {
        Tile tile = world.getTileAt(1, 0).get();
        tile.setBlock(Blocks.getWall());

        Assertions.assertFalse(WallBlock.shouldConnectSouth(tile, world));
    }

    @Test
    void isWallBlockSimpleTest() {
        world.getTileAt(5, 5).get().setBlock(Blocks.getWall());

        Assertions.assertTrue(WallBlock.isWallBlock(world, 5, 5));
    }

    @Test
    void isWallBlockFalseTest() {
        Assertions.assertFalse(WallBlock.isWallBlock(world, 65, 5));
    }

    @Test
    void helpInfoBasicTest() {
        Assertions.assertTrue(Blocks.getWall().getHelpInfo().isPresent());
    }
}
