package com.noahcharlton.stationalpha.block.sapling;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
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

    @Test
    void tickSubtractsWithOxygenTest() {
        container.getTile().setFloor(Floor.DIRT);
        int startTick = container.getTick();

        container.getTile().changeOxygenLevel(25f);
        container.onUpdate();

        Assertions.assertTrue( container.getTick() < startTick);
    }

    @Test
    void noOxygenKillsTreeTest() {
        container.getTile().setFloor(Floor.DIRT);
        Assumptions.assumeTrue(container.getTile().getOxygenLevel() < 10);

        container.onUpdate();

        Assertions.assertSame(Blocks.getDeadPlant(), container.getTile().getBlock().get());
    }

    @Test
    void noDirtKillsTreeTest() {
        container.getTile().setFloor(Floor.METAL);
        container.getTile().changeOxygenLevel(100f);

        container.onUpdate();

        Assertions.assertSame(Blocks.getDeadPlant(), container.getTile().getBlock().get());
    }

    @Test
    void onSaveBasicTest() {
        StringWriter stringWriter = new StringWriter();
        container.setTick(234);

        container.onSave(new QuietXmlWriter(stringWriter));

        String expected = "<Data tick=\"234\" start=\"" + container.getStartingAmount() + "\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void onLoadTickTest() {
        String xml = "<Data tick=\"23\" start=\"3673\"/>\n";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(23, container.getTick());
    }

    @Test
    void onLoadStartingAmountTest() {
        String xml = "<Data tick=\"74\" start=\"5367\"/>\n";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(5367, container.getStartingAmount());
    }
}
