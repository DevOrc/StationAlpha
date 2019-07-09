package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TreeSaplingContainerTests {

    private final World world = new World();
    private final TreeSaplingContainer container = new TreeSaplingContainer(world.getTileAt(1,1).get(),
            Blocks.getTreeBlock(), BlockRotation.NORTH);

    @Test
    void getAllTilesAdjacentWithDiagonalsBasicTest() {
        List<Tile> adjacent = container.getTilesAdjacentWithDiagonals();

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                if(x == 1 && y == 1)
                    continue;

                Assertions.assertTrue(adjacent.contains(world.getTileAt(x, y).get()));
            }
        }
    }

    @Test
    void getBottomLeftCornerTileBasicTest() {
        Tile bottomLeft = container.getBottomLeftCornerTile();

        Assertions.assertEquals(new Tile(0, 0, world), bottomLeft);
    }

    @Test
    void allAdjacentTilesArePresentAllPresentTest() {
        List<Tile> tiles = Arrays.asList(null, null, null, null, null, null, null, null);

        Assertions.assertTrue(container.allAdjacentTilesPresent(tiles));
    }

    @Test
    void allAdjacentTilesArePresentOneMissingTest() {
        List<Tile> tiles = Arrays.asList(null, null, null, null, null, null, null);

        Assertions.assertFalse(container.allAdjacentTilesPresent(tiles));
    }

    @Test
    void allTilesOpenBasicTest(){
        List<Tile> tiles = Arrays.asList(
                world.getTileAt(0, 0).get(),
                world.getTileAt(0, 1).get());

        Assertions.assertTrue(container.allTilesOpen(tiles));
    }

    @Test
    void allTilesOpenOneClosedReturnsFalseTest(){
        List<Tile> tiles = Arrays.asList(
                world.getTileAt(0, 0).get(),
                world.getTileAt(0, 1).get());

        world.getTileAt(0, 0).get().setBlock(Blocks.getIce());

        Assertions.assertFalse(container.allTilesOpen(tiles));
    }

    @Test
    void growTreeBasicTest() {
        Tile tile = container.getTile();

        container.createTree();

        Assertions.assertNotEquals(tile.getContainer().get(), container);
    }

    @Test
    void tickAndStartingTickMatchAfterConstructor() {
        Assertions.assertEquals(container.getTick(), container.getStartingAmount());
    }

    @Test
    void debugInfoZeroPercentTest() {
        String expected = "Progress: 0%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void debugInfo50PercentTest() {
        container.setTick(container.getStartingAmount() / 2);

        String expected = "Progress: 50%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void debugInfo100PercentTest() {
        container.setTick(0);

        String expected = "Progress: 100%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void enoughSpaceDebugInfoTest() {
        String expected = "Space: Good";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void notEnoughSpaceDebugInfoTest() {
        world.getTileAt(0 ,0).get().setBlock(Blocks.getWall());

        String expected = "Space: Low";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }
}
