package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.Item;

import java.util.HashMap;

public class Inventory {

    private final HashMap<Item, Integer> items = new HashMap<>();

    public Inventory() {
        for(Item item: Item.values()){
            items.put(item, 0);
        }
    }

    public int getAmountForItem(Item item){
        return items.get(item);
    }

    public void setAmountForItem(Item item, int amount){
        if(amount < 0)
            amount = 0;

        items.put(item, amount);
    }

    public void changeAmountForItem(Item item, int amount){
        setAmountForItem(item, getAmountForItem(item) + amount);
    }

    /**
     * Used for testing to remove the requirements for building
     */
    public void fillAllItems() {
        for(Item item : Item.values()){
            setAmountForItem(item, 1000);
        }
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }
}
