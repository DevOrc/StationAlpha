package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlWriter;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class WorldSaverTests {

    protected final World world = new World();
    protected final StringWriter stringWriter = new StringWriter();
    protected final WorldSaver saveGame = new WorldSaver(world, new QuietXmlWriter(new XmlWriter(stringWriter)));

    @Test
    void saveTileNoBlockNoFloorTest() {
        Tile tile = world.getTileAt(1, 5).get();
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(35f);

        saveGame.saveTile(tile).pop();

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Tile x=\"1\" y=\"5\" oxygen=\"35.0\"/>\n", expected);
    }

    @Test
    void saveBlockNotRootTileTest() {
        Tile rootTile = world.getTileAt(5, 5).get();
        Tile otherTile = world.getTileAt(5, 6).get();
        BlockContainer blockContainer = new BlockContainer(rootTile, Blocks.getWall());

        saveGame.saveBlock(otherTile, blockContainer, new QuietXmlWriter(new XmlWriter(stringWriter)));

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Container rootX=\"5\" rootY=\"5\"/>\n", expected);
    }

    @Test
    void saveBlockRootTileTest() {
        Tile tile = world.getTileAt(5, 5).get();
        BlockContainer blockContainer = new BlockContainer(tile, Blocks.getWall(), BlockRotation.EAST);

        saveGame.saveBlock(tile, blockContainer, new QuietXmlWriter(new XmlWriter(stringWriter)));

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Container Block=\"wall\" Rotation=\"EAST\"/>\n", expected);
    }

    @Test
    void saveFloorTest() {
        saveGame.saveFloor(Floor.WOOD, new QuietXmlWriter(new XmlWriter(stringWriter)));

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Floor>WOOD</Floor>\n", expected);
    }
}
