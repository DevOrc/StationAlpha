package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;

public class PotatoPlant extends Plant{

    public PotatoPlant() {
        super(4, 1200);
    }

    @Override
    public Item getProduct() {
        return Item.POTATO;
    }

    @Override
    public String getPlantName() {
        return "potato";
    }
}
