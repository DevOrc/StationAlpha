package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InventoryLoaderTests {

    @Test
    public void loadBasicTest(){
        Inventory inventory = new Inventory();
        XmlReader.Element element = LoadTestUtils.asChild("<ItemEntry item=\"STEEL\" amount=\"23\"/>");

        InventoryLoader.load(element, inventory);

        Assertions.assertEquals(23, inventory.getAmountForItem(Item.STEEL));
    }

    @Test
    public void loadEntryBasicTest(){
        Inventory inventory = new Inventory();
        XmlReader.Element element = LoadTestUtils.asElement("<ItemEntry item=\"WOOD\" amount=\"45\"/>");

        InventoryLoader.parseItemEntry(element, inventory);

        Assertions.assertEquals(45, inventory.getAmountForItem(Item.WOOD));
    }

    @Test
    public void loadInvalidItemFailsTest(){
        Inventory inventory = new Inventory();
        XmlReader.Element element = LoadTestUtils.asElement("<ItemEntry item=\"foo\" amount=\"45\"/>");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> InventoryLoader.parseItemEntry(element, inventory));
    }

    @Test
    public void loadInvalidAmountFailsTest(){
        Inventory inventory = new Inventory();
        XmlReader.Element element = LoadTestUtils.asElement("<ItemEntry item=\"STEEL\" amount=\"f\"/>");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> InventoryLoader.parseItemEntry(element, inventory));
    }
}
