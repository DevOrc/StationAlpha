package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlWriter;
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
    void saveTileBasicTest() {
        Tile tile = world.getTileAt(1, 5).get();
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(35f);

        saveGame.saveTile(tile);

        String expected = stringWriter.toString();
        Assertions.assertEquals("<Tile x=\"1\" y=\"5\" oxygen=\"35.0\"/>\n", expected);
    }
}
