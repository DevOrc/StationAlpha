package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WorldLoaderTests {

    @Test
    void getTileFromElementBasicTest() {
        World world = new World();
        XmlReader.Element xml = LoadTestUtils.asElement("<Tile x=\"2\" y=\"3\"/>");

        Optional<Tile> actual = WorldLoader.getTileFromElement(xml, world);

        Assertions.assertEquals(world.getTileAt(2, 3), actual);
    }

    @Test
    void getTileFromElementOutsideWorldTest() {
        World world = new World();
        XmlReader.Element xml = LoadTestUtils.asElement("<Tile x=\"-5\" y=\"3\"/>");

        Optional<Tile> tile = WorldLoader.getTileFromElement(xml, world);

        Assertions.assertFalse(tile.isPresent());
    }

    @Test
    void getTileFromElementMissingXFailsTest() {
        World world = new World();
        XmlReader.Element xml = LoadTestUtils.asElement("<Tile y=\"3\"/>");

        Assertions.assertThrows(GdxRuntimeException.class, () -> WorldLoader.getTileFromElement(xml, world));
    }

    @Test
    void getTileFromElementMissingYFailsTest() {
        World world = new World();
        XmlReader.Element xml = LoadTestUtils.asElement("<Tile x=\"9\"/>");

        Assertions.assertThrows(GdxRuntimeException.class, () -> WorldLoader.getTileFromElement(xml, world));
    }

    @Test
    void loadFloorBasicTest() {
        Tile tile = new Tile(0, 0, new World());
        XmlReader.Element xml = LoadTestUtils.asChild("<Floor>METAL</Floor>");

        WorldLoader.loadTileFloor(tile, xml);

        Assertions.assertEquals(Floor.METAL, tile.getFloor().get());
    }

    @Test
    void loadFloorNoFloorTest() {
        Tile tile = new Tile(0, 0, new World());
        XmlReader.Element xml = LoadTestUtils.asChild("");

        WorldLoader.loadTileFloor(tile, xml);

        Assertions.assertFalse(tile.getFloor().isPresent());
    }

    @Test
    void loadFloorInvalidFloorFailsTest() {
        Tile tile = new Tile(0, 0, new World());
        XmlReader.Element xml = LoadTestUtils.asChild("<Floor>FooBar</Floor>");

        Assertions.assertThrows(IllegalArgumentException.class, () -> WorldLoader.loadTileFloor(tile, xml));
    }

    @Test
    void loadContainerCopyBasicTest() {
        World world = new World();
        Tile rootTile = world.getTileAt(0, 0).get();
        rootTile.setBlock(Blocks.getWall());
        Tile copyTile = world.getTileAt(5, 5).get();

        XmlReader.Element xml = LoadTestUtils.asElement("<Container rootX=\"0\" rootY=\"0\"/>");
        WorldLoader.loadContainerCopy(copyTile, xml);

        Assertions.assertSame(rootTile.getContainer().get(), copyTile.getContainer().get());
    }

    @Test
    void loadContainerRootBlockTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        XmlReader.Element xml = LoadTestUtils.asElement("<Container Block=\"wall\" Rotation=\"NORTH\"/>");
        WorldLoader.loadContainerRoot(tile, xml);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void loadContainerRootRotationTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        XmlReader.Element xml = LoadTestUtils.asElement("<Container Block=\"wall\" Rotation=\"SOUTH\"/>");
        WorldLoader.loadContainerRoot(tile, xml);

        Assertions.assertEquals(BlockRotation.SOUTH, tile.getContainer().get().getRotation());
    }

    @Test
    void loadContainerRootInvalidBlockIDFailsTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        XmlReader.Element xml = LoadTestUtils.asElement("<Container Block=\"fooBar\" Rotation=\"EAST\"/>");

        Assertions.assertThrows(GdxRuntimeException.class, () -> WorldLoader.loadContainerRoot(tile, xml));
    }

    @Test
    void loadOxygenBasicTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setFloor(Floor.METAL);

        XmlReader.Element xml = LoadTestUtils.asElement("<Tile oxygen=\"41\"/>");
        WorldLoader.loadTileOxygen(tile, xml);

        Assertions.assertEquals(41f, tile.getOxygenLevel());
    }

    @Test
    void loadPowerBasicTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        XmlReader.Element xml = LoadTestUtils.asElement("<Tile manualConduit=\"true\" power=\"11\"/>\n");
        WorldLoader.loadTilePower(tile, xml);

        Assertions.assertEquals(11f, tile.getPower());
    }

    @Test
    void loadConduitBasicTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        XmlReader.Element xml = LoadTestUtils.asElement("<Tile manualConduit=\"true\" power=\"11\"/>\n");
        WorldLoader.loadTilePower(tile, xml);

        Assertions.assertTrue(tile.hasConduit());
    }

    @Test
    void loadPowerMachineConduitTest() {
        World world = new World();
        Tile tile = world.getTileAt(1, 2).get();
        String xmlString = "<Tile x=\"1\" y=\"2\" oxygen=\"2.0\" manualConduit=\"false\" power=\"25\">\n" +
                "<Container Block=\"solar_panel\" Rotation=\"NORTH\"/>\n</Tile>";

        XmlReader.Element xml = LoadTestUtils.asChild(xmlString);
        WorldLoader.load(xml, world);

        Assertions.assertEquals(25f, tile.getPower());
    }
}
