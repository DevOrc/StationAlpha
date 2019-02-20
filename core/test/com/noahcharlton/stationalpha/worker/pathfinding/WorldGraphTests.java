package com.noahcharlton.stationalpha.worker.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorldGraphTests {

    private final World world = new World(false);
    private final WorldGraph graph = new WorldGraph(world);

    @Test
    void getIndexYOneTest() {
        Tile tile = new Tile(25, 0, world);

        Assertions.assertEquals(25, graph.getIndex(tile));
    }

    @Test
    void getIndexUpperYLevelTest() {
        Tile tile = new Tile(70, 2, world);

        Assertions.assertEquals(370, graph.getIndex(tile));
    }

    @Test
    void getConnectionOriginTwoConnectionsTest() {
        Tile origin = new Tile(0, 0, world);

        Assertions.assertEquals(2, graph.getConnections(origin).size);
    }

    @Test
    void getConnectionWallBlockTest() {
        Tile origin = new Tile(0, 0, world);

        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());

        Array<Connection<Tile>> connections = graph.getConnections(origin);

        Assertions.assertEquals(0, connections.size);
    }

    @Test
    void getConnectionCenterTileFourConnectionsTest() {
        Tile origin = new Tile(15, 13, world);

        Assertions.assertEquals(4, graph.getConnections(origin).size);
    }

    @Test
    void doesTileBlockPathsNoBlockTest() {
        Tile tile = new Tile(0, 0, world);

        Assertions.assertFalse(graph.doesTileBlockPaths(tile));
    }

    @Test
    void doesTileBlockPathsWallTest() {
        Tile tile = new Tile(0, 0, world);
        tile.setBlock(Blocks.getWall());

        Assertions.assertTrue(graph.doesTileBlockPaths(tile));
    }

    @Test
    void doesTileBlockPathsDoorTest() {
        Tile tile = new Tile(0, 0, world);
        tile.setBlock(Blocks.getDoor());

        Assertions.assertFalse(graph.doesTileBlockPaths(tile));
    }
}
