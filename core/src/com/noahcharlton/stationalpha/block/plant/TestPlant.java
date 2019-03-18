package com.noahcharlton.stationalpha.block.plant;

public class TestPlant extends Plant{

    public TestPlant() {
        super(4, 128);
    }

    @Override
    public String getPlantName() {
        return "test";
    }
}
