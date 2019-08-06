package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class InventorySaverTests {

    private final World world = new World();
    private final StringWriter stringWriter = new StringWriter();
    private final QuietXmlWriter xmlWriter = new QuietXmlWriter(new XmlWriter(stringWriter));
    private final InventorySaver saveGame = new InventorySaver(world);

    @Test
    void writeInventoryEntryTest() {
        saveGame.writeInventoryEntry(xmlWriter, Item.SPACE_ROCK, 23);

        String expected = "<ItemEntry item=\"SPACE_ROCK\" amount=\"23\"/>\n";
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void saveHasEveryItemTest() {
        saveGame.save(xmlWriter);

        String output = stringWriter.toString();
        XmlReader.Element element = LoadTestUtils.asElement(output);

        Assertions.assertEquals(Item.values().length, element.getChildrenByName("ItemEntry").size);
    }
}
