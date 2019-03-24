package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.item.Item;

public class TestPlant extends Plant{

    public TestPlant() {
        super(4, 128);
    }

    @Override
    public Item getProduct() {
        return Item.TEST_ITEM;
    }

    @Override
    public String getPlantName() {
        return "test";
    }
}
