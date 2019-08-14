package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;

public class PotatoPlant extends Plant{

    public PotatoPlant() {
        super(4, 3000);
    }

    @Override
    public Item getProduct() {
        return Item.POTATO;
    }

    @Override
    public String getPlantName() {
        return "potato";
    }

    @Override
    public int getAmountPerHarvest() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return "Potato Plant";
    }
}
