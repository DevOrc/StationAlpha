package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlWriter;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.TilePowerTests;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class WorldSaverTests {

    private final World world = new World();
    private final StringWriter stringWriter = new StringWriter();
    private final QuietXmlWriter xmlWriter = new QuietXmlWriter(new XmlWriter(stringWriter));
    private final WorldSaver saveGame = new WorldSaver(world);

    @Test
    void saveTileNoBlockNoFloorTest() {
        Tile tile = world.getTileAt(1, 5).get();
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(35f);
        tile.setConduit(true);

        saveGame.saveTile(tile, xmlWriter).pop();

        String expected = "<Tile x=\"1\" y=\"5\" oxygen=\"35.0\" manualConduit=\"true\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void saveTileNonManualConduitTest() {
        Tile tile = world.getTileAt(6, 9).get();
        tile.setBlock(Blocks.getWall(), new TilePowerTests.TestPowerContainer(tile));

        Assumptions.assumeTrue(tile.hasConduit());
        saveGame.saveTile(tile, xmlWriter).pop();

        String expected = "<Tile x=\"6\" y=\"9\" oxygen=\"0.0\" manualConduit=\"false\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }


    @Test
    void saveBlockNotRootTileTest() {
        Tile rootTile = world.getTileAt(5, 5).get();
        Tile otherTile = world.getTileAt(5, 6).get();
        BlockContainer blockContainer = new BlockContainer(rootTile, Blocks.getWall());

        saveGame.saveBlock(otherTile, blockContainer, xmlWriter);

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Container rootX=\"5\" rootY=\"5\"/>\n", expected);
    }

    @Test
    void saveBlockRootTileTest() {
        Tile tile = world.getTileAt(5, 5).get();
        BlockContainer blockContainer = new BlockContainer(tile, Blocks.getWall(), BlockRotation.EAST);

        saveGame.saveBlock(tile, blockContainer, xmlWriter);

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Container Block=\"wall\" Rotation=\"EAST\"/>\n", expected);
    }

    @Test
    void saveBlockRootTileContainerSaveTest() {
        Tile tile = world.getTileAt(5, 5).get();
        SaveTestContainer blockContainer = new SaveTestContainer(tile);

        saveGame.saveBlock(tile, blockContainer, xmlWriter);

        Assertions.assertTrue(blockContainer.isSaved());
    }

    @Test
    void saveFloorTest() {
        saveGame.saveFloor(Floor.WOOD, xmlWriter);

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Floor>WOOD</Floor>\n", expected);
    }
}
class SaveTestContainer extends BlockContainer{

    private boolean isSaved;

    public SaveTestContainer(Tile tile) {
        super(tile, Blocks.getWall());
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        isSaved = true;
    }

    public boolean isSaved() {
        return isSaved;
    }
}
