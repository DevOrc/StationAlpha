package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;

public class InventoryLoader {

    static void load(XmlReader.Element elements, Inventory inventory) {
        for(XmlReader.Element element : elements.getChildrenByName("ItemEntry")){
            parseItemEntry(element, inventory);
        }
    }

    static void parseItemEntry(XmlReader.Element element, Inventory inventory) {
        Item item = Item.valueOf(element.getAttribute("item"));
        int amount = element.getIntAttribute("amount");

        inventory.setAmountForItem(item, amount);
    }
}
