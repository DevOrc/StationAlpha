package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;

import java.util.Map;

public class InventorySaver {

    private final Inventory inventory;

    public InventorySaver(World world) {
        this.inventory = world.getInventory();
    }

    public void save(QuietXmlWriter writer) {
        QuietXmlWriter inventoryWriter = writer.element("Inventory");

        for(Map.Entry<Item, Integer> entry : inventory.getItems().entrySet()){
            writeInventoryEntry(inventoryWriter, entry.getKey(), entry.getValue());
        }

        inventoryWriter.pop();
    }

    void writeInventoryEntry(QuietXmlWriter inventoryWriter, Item item, int amount) {
        inventoryWriter.element("ItemEntry")
                .attribute("item", item.name())
                .attribute("amount", amount)
                .pop();
    }
}
