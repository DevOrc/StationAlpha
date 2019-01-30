package com.noahcharlton.stationalpha.block.door;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoorBlockTests {

    @Test
    void doorContainerSetOrientationNullFailsTest() {
        Tile tile = new Tile(0, 0, new World());
        tile.setBlock(Blocks.getDoor());

        DoorContainer doorContainer = (DoorContainer) Block.getContainerFromTile(tile);

        Assertions.assertThrows(NullPointerException.class, () -> doorContainer.setOrientation(null));
    }

    @Test
    void basicWallOrientationEastWestTest() {
        World world = new World();

        world.getTileAt(0, 0).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getDoor());
        world.getTileAt(2, 0).get().setBlock(Blocks.getWall());

        DoorContainer doorContainer = (DoorContainer) Block.getContainerFromTile(world.getTileAt(1, 0).get());

        Assertions.assertEquals(DoorContainer.Orientation.EAST_WEST, doorContainer.getOrientation());
    }

    @Test
    void eastWallOnlyDefaultOrientationTest() {
        World world = new World();

        world.getTileAt(1, 0).get().setBlock(Blocks.getDoor());
        world.getTileAt(2, 0).get().setBlock(Blocks.getWall());

        DoorContainer doorContainer = (DoorContainer) Block.getContainerFromTile(world.getTileAt(1, 0).get());

        Assertions.assertEquals(DoorContainer.Orientation.NORTH_SOUTH, doorContainer.getOrientation());
    }

    @Test
    void westWallOnlyDefaultOrientationTest() {
        World world = new World();

        world.getTileAt(1, 0).get().setBlock(Blocks.getDoor());
        world.getTileAt(0, 0).get().setBlock(Blocks.getWall());

        DoorContainer doorContainer = (DoorContainer) Block.getContainerFromTile(world.getTileAt(1, 0).get());

        Assertions.assertEquals(DoorContainer.Orientation.NORTH_SOUTH, doorContainer.getOrientation());
    }

    @Test
    void defaultWallOrientationTest() {
        World world = new World();

        world.getTileAt(1, 0).get().setBlock(Blocks.getDoor());

        DoorContainer doorContainer = (DoorContainer) Block.getContainerFromTile(world.getTileAt(1, 0).get());

        Assertions.assertEquals(DoorContainer.Orientation.NORTH_SOUTH, doorContainer.getOrientation());
    }
}
